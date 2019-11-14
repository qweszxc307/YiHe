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
import org.crown.enums.StatusEnum;
import org.crown.framework.enums.ErrorCodeEnum;
import org.crown.framework.service.impl.BaseServiceImpl;
import org.crown.framework.utils.ApiAssert;
import org.crown.mapper.product.ProductMapper;
import org.crown.model.brand.entity.Brand;
import org.crown.model.product.dto.ProductDTO;
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
        return  page.setRecords(baseMapper.getProductPage(page));
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
}
