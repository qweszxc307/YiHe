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
package org.crown.service.brand.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.crown.common.utils.QiNiuUtils;
import org.crown.enums.StatusEnum;
import org.crown.framework.enums.ErrorCodeEnum;
import org.crown.framework.service.impl.BaseServiceImpl;
import org.crown.framework.utils.ApiAssert;
import org.crown.mapper.brand.BrandMapper;
import org.crown.model.brand.dto.BrandDTO;
import org.crown.model.brand.entity.Brand;
import org.crown.model.brand.entity.BrandImage;
import org.crown.model.brand.parm.BrandPARM;
import org.crown.model.image.entity.Image;
import org.crown.service.brand.IBrandImageService;
import org.crown.service.brand.IBrandService;
import org.crown.service.image.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.crown.common.utils.ApiUtils.currentUid;

/**
 * <p>
 * 品牌表 服务实现类
 * </p>
 *
 * @author whZhang
 */
@Service
public class BrandServiceImpl extends BaseServiceImpl<BrandMapper, Brand>implements IBrandService {
    @Autowired
    IBrandService brandService;
    @Autowired
    IBrandImageService brandImageService;
    @Autowired
    IImageService imageService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createBrand(BrandPARM brandPARM) {
        int count = brandService.query()
                .eq(Brand::getName, brandPARM.getName())
                .count();
        ApiAssert.isTrue(ErrorCodeEnum.BRAND_ALREADY_EXISTS, count == 0);
        Brand brand = brandPARM.convert(Brand.class);
        //默认启用
        brand.setStatus(StatusEnum.NORMAL);
        brandService.save(brand);
        BrandImage brandImage = new BrandImage();
        brandImage.setBId(brand.getId());
        brandImage.setImgId(Integer.parseInt(brandPARM.getImageId()));
        brandImageService.save(brandImage);
    }

    @Override
    public IPage<BrandDTO> selectBrandPage(IPage<BrandDTO> page) {
        return page.setRecords(baseMapper.getBrandPage(page));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Integer brandId, StatusEnum status) {
        Brand brand = baseMapper.selectById(brandId);
        brand.setStatus(status);
        updateById(brand);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBrandById(Integer brandId) {
        brandService.removeById(brandId);
        BrandImage brandImage = brandImageService.query().eq(BrandImage::getBId, brandId).getOne();
        imageService.delete().eq(Image::getId, brandImage.getImgId()).execute();
        brandImageService.delete().eq(BrandImage::getBId, brandId).execute();
    }

    @Override
    public BrandDTO getBrandById(Integer brandId) {
        Brand brand = brandService.getById(brandId);
        BrandDTO brandDTO = brand.convert(BrandDTO.class);
        BrandImage brandImage = brandImageService.query().eq(BrandImage::getBId,brandDTO.getId()).getOne();
        Image image = imageService.query().eq(Image::getId,brandImage.getImgId()).getOne();
        brandDTO.setImgUrl(image.getImgUrl());
        brandDTO.setImageId(image.getId());
        return brandDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBrand(Integer brandId, BrandPARM brandPARM) {
        BrandImage brandImage = brandImageService.query().eq(BrandImage::getBId, brandId).getOne();
        brandImage.setImgId(Integer.parseInt(brandPARM.getImageId()));
        brandImageService.updateById(brandImage);
        Brand brand = brandService.query().eq(Brand::getId, brandImage.getBId()).getOne();
        brand.setName(brandPARM.getName());
        brand.setOrderNum(Integer.parseInt(brandPARM.getOrderNum()));
        brandService.saveOrUpdate(brand);
    }
}
