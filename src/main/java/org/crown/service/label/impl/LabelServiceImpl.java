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

import lombok.extern.slf4j.Slf4j;
import org.crown.model.label.entity.Label;
import org.crown.mapper.label.LabelMapper;
import org.crown.service.label.ILabelService;
import org.crown.framework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author ykMa
 */
@Slf4j
@Service
public class LabelServiceImpl extends BaseServiceImpl<LabelMapper, Label> implements ILabelService {


    @Transactional(readOnly = false)
    @Override
    public void removeLabelById(Integer id) {
        try {
            removeById(id);
            baseMapper.deleteLabelCustomerByLabelId(id);
        } catch (Exception e) {
            log.error("删除中间表失败，原因是：" + e);
        }
    }
}
