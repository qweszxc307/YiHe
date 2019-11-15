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
package org.crown.service.brand;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.crown.enums.StatusEnum;
import org.crown.framework.responses.ApiResponses;
import org.crown.framework.service.BaseService;
import org.crown.model.brand.dto.BrandDTO;
import org.crown.model.brand.dto.BrandImgDTO;
import org.crown.model.brand.entity.Brand;
import org.crown.model.brand.parm.BrandPARM;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 品牌表 服务类
 * </p>
 *
 * @author whZhang
 */
public interface IBrandService extends BaseService<Brand> {
        /**
         * 添加品牌
         *
         * @param brandPARM
         */
        void createBrand(BrandPARM brandPARM);
        /**
         * 品牌列表
         *
         * @param page
         */
        IPage<BrandDTO> selectBrandPage(IPage<BrandDTO> page);

        /**
         * 修改菜单状态
         *
         * @param brandId
         * @param status
         */
        void updateStatus(Integer brandId, StatusEnum status);

        /**
         * 删除品牌
         *
         * @param brandId
         */
        void deleteBrandById(Integer brandId);
        /**
         * 上传品牌图片
         *
         *@param httpServletResponse
         * @param file
         */
        ApiResponses<BrandImgDTO> uploadBrandImg(HttpServletResponse httpServletResponse,MultipartFile file);
        /**
         * 获取品牌详情
         *
         * @param brandId
         */
        BrandDTO getBrandById(Integer brandId);
        /**
         * 获取品牌详情
         *
         * @param brandId
         * @param brandPARM
         */
        void updateBrand(Integer brandId,BrandPARM brandPARM);
}
