/*
 * Copyright (c) 2018-2022 Caratacus, (caratacus@qq.com).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.crown.controller.product;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.crown.common.annotations.Resources;
import org.crown.common.utils.TypeUtils;
import org.crown.enums.AuthTypeEnum;
import org.crown.enums.ImagesEnum;
import org.crown.framework.controller.SuperController;
import org.crown.framework.responses.ApiResponses;
import org.crown.model.image.dto.ImageDTO;
import org.crown.model.product.dto.ProductDTO;
import org.crown.model.product.dto.ProductImgDTO;
import org.crown.model.product.dto.ProductPriceDTO;
import org.crown.model.product.entity.Product;
import org.crown.model.product.entity.ProductCarriage;
import org.crown.model.product.entity.ProductImage;
import org.crown.model.product.entity.ProductPrice;
import org.crown.model.product.parm.ProductPARM;
import org.crown.service.image.IImageService;
import org.crown.service.product.IProductCarriageService;
import org.crown.service.product.IProductImageService;
import org.crown.service.product.IProductPriceService;
import org.crown.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 产品表 前端控制器
 * </p>
 *
 * @author whZhang
 */
@Api(tags = {"Product"}, description = "产品表相关接口")
@RestController
@RequestMapping(value = "/product", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class ProductRestController extends SuperController {
    @Autowired
    private IProductService productService;
    @Autowired
    private IImageService imageService;
    @Autowired
    private IProductCarriageService productCarriageService;
    @Autowired
    private IProductPriceService productPriceService;
    @Autowired
    private IProductImageService productImageService;

    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation("查询所有产品")
    @GetMapping
    public ApiResponses<IPage<ProductDTO>> page() {
        return success(
                productService.selectProductPage(this.<ProductDTO>getPage())
                        .convert(e -> e.convert(ProductDTO.class))
        );
    }

    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation("查询单个产品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "产品ID", required = true, paramType = "path")
    })
    @GetMapping("/{id}")
    public ApiResponses<ProductDTO> get(@PathVariable("id") Integer id) {
            Product product = productService.getById(id);
            ProductDTO productDTO = product.convert(ProductDTO.class);
            /*获取产品运费策略模板*/
            ProductCarriage productCarriage = productCarriageService.query().eq(ProductCarriage::getPid,id).getOne();
            /*获取产品价格*/
            List<ProductPrice> productPriceList =  productPriceService.query().eq(ProductPrice::getPid,id).list();
            List<ProductPriceDTO> productPriceDTOList = new ArrayList<>();
            for(ProductPrice productPrice : productPriceList){
                ProductPriceDTO productPriceDTO = productPrice.convert(ProductPriceDTO.class);
                productPriceDTOList.add(productPriceDTO);
            }
            /*获取产品相关图片*/
            List<ProductImgDTO> productImgDTOList = productImageService.getProductImagesById(id);
            productDTO.setProductImgList(productImgDTOList);
            productDTO.setCarriageId(productCarriage.getCid());
            productDTO.setProductPriceList(productPriceDTOList);
            return success(productDTO);
    }

    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation("添加产品")
    @PostMapping
    public ApiResponses<Void> create(@RequestBody @Validated(ProductPARM.Create.class) ProductPARM productPARM) {
            productService.createProduct(productPARM);
            return success(HttpStatus.CREATED);
    }

    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation(value = "删除产品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "产品ID", required = true, paramType = "path")
    })

    @DeleteMapping("/{id}")
    public ApiResponses<Void> delete(@PathVariable("id") Integer id) {
            productService.deletProductById(id);
            return success(HttpStatus.NO_CONTENT);
    }

    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation(value = "修改产品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "产品ID", required = true, paramType = "path")
    })
    @PutMapping("/{id}")
    public ApiResponses<Void> update(@PathVariable("id") Integer id,@RequestBody @Validated  ProductPARM productPARM) {
            productService.updateProductById(id,productPARM);
            return success();
    }

    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation("设置产品状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "产品ID", required = true, paramType = "path")
    })
    @PutMapping("/{id}/status")
    public ApiResponses<Void> updateStatus(@PathVariable("id") Integer id, @RequestBody @Validated(ProductPARM.Status.class) ProductPARM productPARM) {
        productService.updateStatus(id, productPARM.getStatus());
        return success();
    }

    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation("添加产品详情图片")
    @PostMapping(value = "/upload")
    public ApiResponses<ImageDTO> upload(HttpServletResponse httpServletResponse, MultipartFile file) {
        return imageService.uploadImg(httpServletResponse,file,ImagesEnum.PRODUCT_DETAIL_IMAGE);
    }

    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation("添加产品图片")
    @PostMapping(value = "/uploads")
    public ApiResponses<ImageDTO> uploads(HttpServletResponse httpServletResponse, MultipartFile file) {
        return imageService.uploadImg(httpServletResponse,file, ImagesEnum.PRODUCT_IMAGE);
    }
}
