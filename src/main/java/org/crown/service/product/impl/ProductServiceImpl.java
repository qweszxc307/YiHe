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
package org.crown.service.product.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.crown.common.utils.TypeUtils;
import org.crown.enums.ImagesEnum;
import org.crown.enums.StatusEnum;
import org.crown.framework.enums.ErrorCodeEnum;
import org.crown.framework.service.impl.BaseServiceImpl;
import org.crown.framework.utils.ApiAssert;
import org.crown.mapper.product.ProductMapper;
import org.crown.model.brand.entity.Brand;
import org.crown.model.product.dto.ProductDTO;
import org.crown.model.product.dto.ProductImgDTO;
import org.crown.model.product.entity.Product;
import org.crown.model.product.entity.ProductCarriage;
import org.crown.model.product.entity.ProductImage;
import org.crown.model.product.entity.ProductPrice;
import org.crown.model.product.parm.ProductPARM;
import org.crown.service.product.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 产品表 服务实现类
 * </p>
 *
 * @author whZhang
 */
@Service
public class ProductServiceImpl extends BaseServiceImpl<ProductMapper, Product>implements IProductService {

    @Autowired
    private IProductService productService;
    @Autowired
    private IProductImageService productImageService;
    @Autowired
    private IProductPriceService productPriceService;
    @Autowired
    private IProductCarriageService productCarriageService;

    @Override
    public IPage<ProductDTO> selectProductPage(IPage<ProductDTO> page) {
        return  page.setRecords(baseMapper.selectProductPage(page));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createProduct(ProductPARM productPARM) {
        int count = productService.query()
                .eq(Product::getName, productPARM.getName())
                .count();
        ApiAssert.isTrue(ErrorCodeEnum.PRODUCT_ALREADY_EXISTS, count == 0);
        /*保存产品*/
        Product product = productPARM.convert(Product.class);
        //默认启用
        product.setStatus(StatusEnum.NORMAL);
        productService.save(product);
        /*保存产品图片*/
        List<Integer> productImgs = productPARM.getProductImgs();
        List<ProductImage> piList = new ArrayList<>();
        for(int i=0;i< productImgs.size();i++){
            ProductImage pi = new ProductImage();
            pi.setImgId(productImgs.get(i));
            pi.setPId(product.getId());
            piList.add(pi);
        }
        productImageService.saveBatch(piList);
        ProductImage productImage = new ProductImage();
        productImage.setPId(product.getId());
        productImage.setImgId(Integer.parseInt(productPARM.getDetailImgId()));
        productImageService.save(productImage);
        /*保存产品价格区间*/
        List<ProductPrice> productPriceList = new ArrayList<>();
        List<String> bNums = productPARM.getBnums();
        for(int i = 0;i<bNums.size();i++){
            ProductPrice productPrice = new ProductPrice();
            productPrice.setSNum(Integer.parseInt(bNums.get(i)));
            productPrice.setENum(Integer.parseInt(productPARM.getOnums().get(i)));
            BigDecimal bd = new BigDecimal(productPARM.getPrice().get(i));
            productPrice.setPrice(bd);
            productPrice.setPid(product.getId());
            productPriceList.add(productPrice);
        }
        /*保存产品运费策略*/
        ProductCarriage productCarriage = new ProductCarriage();
        productCarriage.setCid(Integer.parseInt(productPARM.getCarriageId()));
        productCarriage.setPid(product.getId());
        productCarriageService.save(productCarriage);
        productPriceService.saveBatch(productPriceList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Integer productId, StatusEnum status) {
        Product product = baseMapper.selectById(productId);
        product.setStatus(status);
        updateById(product);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateProductById(Integer id, ProductPARM productPARM) {
        /*产品基本信息*/
        Product product = productService.getById(id);
        product.setBrandId(productPARM.getBrandId());
        product.setName(productPARM.getName());
        product.setOrderNum(Integer.parseInt(productPARM.getOrderNum()));
        product.setStock(Integer.parseInt(productPARM.getStock()));
        productService.updateById(product);
        /*产品价格区间*/
        List<ProductPrice> productPriceList = new ArrayList<>();
        List<String> sNumList = productPARM.getBnums();
        for(int i = 0;i<sNumList.size();i++){
            ProductPrice productPrice = new ProductPrice();
            productPrice.setSNum(Integer.parseInt(sNumList.get(i)));
            productPrice.setENum(Integer.parseInt(productPARM.getOnums().get(i)));
            productPrice.setPrice(TypeUtils.castToBigDecimal(productPARM.getPrice().get(i)));
            productPrice.setPid(id);
            productPriceList.add(productPrice);
        }
        productPriceService.delete().eq(ProductPrice::getPid,id).execute();
        productPriceService.saveBatch(productPriceList);
        /*产品相关图片*/
        if(productPARM.getProductImgs().size()>0){
            List<ProductImgDTO> productImageDTOList = productImageService.getProductImagesById(id);
            /*删除旧的产品图片依赖关系*/
            for(int i = 0;i<productImageDTOList.size();i++){
                ProductImgDTO productImgDTO = productImageDTOList.get(i);
                if(productImgDTO.getType() == ImagesEnum.PRODUCT_IMAGE){
                    ProductImage productImage = productImageService.query()
                            .eq(ProductImage::getImgId,productImgDTO.getImgId())
                            .eq(ProductImage::getPId,productImgDTO.getPId())
                            .getOne();
                    productImageService.removeById(productImage);
                }
            }
            /*添加新的产品图片依赖*/
            List<ProductImage> productImages = new ArrayList<>();
            for(int i =0;i<productPARM.getProductImgs().size();i++){
                ProductImage productImage = new ProductImage();
                productImage.setImgId(productPARM.getProductImgs().get(i));
                productImage.setPId(id);
                productImages.add(productImage);
            }
            productImageService.saveBatch(productImages);
        }
        /*产品详情图片*/
        if(productPARM.getDetailImgId() != null){
            List<ProductImgDTO> productImageDTOList = productImageService.getProductImagesById(id);
            /*删除旧的产品详情图片依赖关系*/
            for(int i = 0;i<productImageDTOList.size();i++){
                ProductImgDTO productImgDTO = productImageDTOList.get(i);
                if(productImgDTO.getType() == ImagesEnum.PRODUCT_DETAIL_IMAGE){
                    ProductImage productImage = productImageService.query()
                            .eq(ProductImage::getImgId,productImgDTO.getImgId())
                            .eq(ProductImage::getPId,productImgDTO.getPId())
                            .getOne();
                    productImageService.removeById(productImage);
                }
            }
            /*添加新的产品详情图片依赖*/
            ProductImage productImage = new ProductImage();
            productImage.setPId(id);
            productImage.setImgId(Integer.parseInt(productPARM.getDetailImgId()));
            productImageService.save(productImage);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletProductById(Integer id) {
        productService.delete().eq(Product::getId,id).execute();
        productPriceService.delete().eq(ProductPrice::getPid,id).execute();
        productImageService.delete().eq(ProductImage::getPId,id).execute();
        productCarriageService.delete().eq(ProductCarriage::getPid,id).execute();
    }

    @Override
    public List<Map<String, String>> selectProducts() {
        return baseMapper.selectProducts();
    }
}
