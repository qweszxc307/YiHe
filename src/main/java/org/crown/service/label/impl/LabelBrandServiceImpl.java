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
package org.crown.service.label.impl;

import org.crown.framework.enums.ErrorCodeEnum;
import org.crown.framework.service.impl.BaseServiceImpl;
import org.crown.framework.utils.ApiAssert;
import org.crown.mapper.label.LabelBrandMapper;
import org.crown.model.label.dto.LabelBrandDTO;
import org.crown.model.label.entity.LabelBrand;
import org.crown.service.label.ILabelBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 品牌标签表
 服务实现类
 * </p>
 *
 * @author ykMa
 */
@Service
public class LabelBrandServiceImpl extends BaseServiceImpl<LabelBrandMapper, LabelBrand> implements ILabelBrandService {
        @Autowired
        private LabelBrandMapper labelBrandMapper;


        /**
         * 修改标签状态
         *
         * @param id
         * @param status
         */
        @Override
        @Transactional(readOnly = false)
        public void updateStatus(Integer id, Integer status) {
                LabelBrand labelBrand = getById(id);
                ApiAssert.notNull(ErrorCodeEnum.SERVICE_UNAVAILABLE, labelBrand);
                labelBrand.setStatus(status);
                updateById(labelBrand);
        }

        /**
         * 根据用户id查询标签
         * @param id
         * @return
         */
        @Override
        public List<LabelBrandDTO> queryLabelBrandDTOByCustomerId(Integer id) {
                List<Integer> labels =labelBrandMapper.queryLabelIdsByCustomerId(id);
                List<LabelBrandDTO> labelBrandDTOS = new ArrayList<>();
                for (Integer label : labels) {
                        labelBrandDTOS.add(labelBrandMapper.selectById(label).convert(LabelBrandDTO.class));
                }
                return labelBrandDTOS;
        }
}
