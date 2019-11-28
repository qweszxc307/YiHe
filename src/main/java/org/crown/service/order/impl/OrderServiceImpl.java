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
package org.crown.service.order.impl;

import lombok.extern.slf4j.Slf4j;
import org.crown.framework.service.impl.BaseServiceImpl;
import org.crown.mapper.customer.CustomerMapper;
import org.crown.mapper.order.OrderMapper;
import org.crown.model.order.dto.OrderUploadDTO;
import org.crown.model.order.entity.Order;
import org.crown.service.order.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ykMa
 */
@Slf4j
@Service
public class OrderServiceImpl extends BaseServiceImpl<OrderMapper, Order> implements IOrderService {
    @Autowired
    private CustomerMapper customerMapper;


    @Transactional(readOnly = false)
    @Override
    public void updateTotalFeeByOrderId(Integer id, BigDecimal totalFee) {

        if (Objects.nonNull(id) && Objects.nonNull(totalFee)) {
            if (baseMapper.updateTotalFeeByOrderId(id, totalFee) == 0) {
                log.error("修改订单失败！传入参数：{ id:" + id + " },{ totalFee:" + totalFee + " }");
                throw new RuntimeException("修改订单失败：传入参数有误：{ id:" + id + " },{ totalFee:" + totalFee + " }");
            }
        } else {
            log.error("修改订单失败！传入参数：{ id:" + id + " },{ totalFee:" + totalFee + " }");
            throw new RuntimeException("修改订单失败：传入参数有误：{ id:" + id + " },{ totalFee:" + totalFee + " }");
        }
    }

    @Transactional(readOnly = false)
    @Override
    public Integer upload(List<OrderUploadDTO> upload ,String logisticsCompany) {
        int count = 0;
        for (OrderUploadDTO orderUploadDTO : upload) {
            String orderNum = orderUploadDTO.getOrderNum();//订单号
            String logisticsNumber = orderUploadDTO.getLogisticsNumber();//物流单号
            /**
             * 根据订单号查询订单，根据订单id找到物流表   设置物流表的物流单号和物流公司名称   订单状态改成 已发货
             */
            Order order = baseMapper.queryOrderByOrderNum(orderNum);
            order.setStatus(3);
            order.setConsignTime(LocalDateTime.now());
            updateById(order);
            baseMapper.updateLogisticsByOrderId(order.getId(), logisticsNumber, logisticsCompany);
            count++;
        }

        return count;
    }
}