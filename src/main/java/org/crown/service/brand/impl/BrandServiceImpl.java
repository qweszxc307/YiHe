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
import org.crown.enums.StatusEnum;
import org.crown.framework.service.impl.BaseServiceImpl;
import org.crown.mapper.brand.BrandMapper;
import org.crown.model.brand.dto.BrandDTO;
import org.crown.model.brand.entity.Brand;
import org.crown.service.brand.IBrandService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 品牌表 服务实现类
 * </p>
 *
 * @author whZhang
 */
@Service
public class BrandServiceImpl extends BaseServiceImpl<BrandMapper, Brand>implements IBrandService {


    @Override
    public IPage<BrandDTO> selectBrandPage(IPage<BrandDTO> page) {
        return page.setRecords(baseMapper.getBrandPage(page));
    }

    @Override
    @Transactional
    public void updateStatus(Integer brandId, StatusEnum status) {
        Brand brand = baseMapper.selectById(brandId);
        brand.setStatus(status);
        updateById(brand);
    }
}
