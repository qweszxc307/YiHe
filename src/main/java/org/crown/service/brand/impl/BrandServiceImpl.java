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
import org.crown.framework.responses.ApiResponses;
import org.crown.framework.service.impl.BaseServiceImpl;
import org.crown.framework.utils.ApiAssert;
import org.crown.mapper.brand.BrandMapper;
import org.crown.model.brand.dto.BrandDTO;
import org.crown.model.brand.dto.BrandImgDTO;
import org.crown.model.brand.entity.Brand;
import org.crown.model.brand.entity.BrandImage;
import org.crown.model.brand.parm.BrandPARM;
import org.crown.model.image.entity.Image;
import org.crown.service.brand.IBrandImageService;
import org.crown.service.brand.IBrandService;
import org.crown.service.image.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static org.crown.framework.responses.ApiResponses.success;

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
    @Autowired
    private QiNiuUtils qiniuService;

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
    @Transactional(rollbackFor = Exception.class)
    public ApiResponses<BrandImgDTO> uploadBrandImg(HttpServletResponse response,MultipartFile file) {
        BrandImgDTO imageDTO = new BrandImgDTO();
        try {
            String originalFilename = file.getOriginalFilename();
            String fileExtend = originalFilename.substring(originalFilename.lastIndexOf("."));
            //默认不指定key的情况下，以文件内容的hash值作为文件名
            String fileKey = UUID.randomUUID().toString().replace("-", "") + fileExtend;
            String imgUrl = qiniuService.upload(file.getInputStream(),fileKey);
            if(imgUrl.equals(null)){
                return success(response,HttpStatus.BAD_REQUEST,null);
            }else{
                Image baseImg = new Image();
                baseImg.setImgUrl(imgUrl);
                imageService.save(baseImg);
                imageDTO.setImgId(baseImg.getId());
                imageDTO.setImgUrl(imgUrl);
                return success(response,HttpStatus.OK,imageDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return success(response,HttpStatus.BAD_REQUEST,null);
        }
    }

    @Override
    public BrandDTO getBrandById(Integer brandId) {
        Brand brand = brandService.getById(brandId);
        BrandDTO brandDTO = brand.convert(BrandDTO.class);
        BrandImage brandImage = brandImageService.query().eq(BrandImage::getBId,brandDTO.getId()).getOne();
        Image image = imageService.query().eq(Image::getId,brandImage.getImgId()).getOne();
        brandDTO.setImgUrl(image.getImgUrl());
        return brandDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBrand(Integer brandId, BrandPARM brandPARM) {
        if(brandPARM.getImageId().isEmpty() == false){
            BrandImage brandImage = brandImageService.query().eq(BrandImage::getBId, brandId).getOne();
            brandImage.setImgId(Integer.parseInt(brandPARM.getImageId()));
            brandImageService.updateById(brandImage);
        }
        Brand brand = brandService.getById(brandId);
        brand.setName(brandPARM.getName());
        brand.setOrderNum(Integer.parseInt(brandPARM.getOrderNum()));
        brandService.saveOrUpdate(brand);
    }
}
