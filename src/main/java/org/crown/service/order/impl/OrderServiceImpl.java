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
import org.crown.mapper.order.OrderMapper;
import org.crown.model.coupon.entity.Coupon;
import org.crown.model.coupon.entity.CouponCustomer;
import org.crown.model.customer.entity.Customer;
import org.crown.model.order.dto.OrderLogisticsDTO;
import org.crown.model.order.dto.OrderUploadDTO;
import org.crown.model.order.entity.Order;
import org.crown.model.order.entity.OrderLogistics;
import org.crown.service.coupon.ICouponCustomerService;
import org.crown.service.coupon.ICouponService;
import org.crown.service.customer.ICustomerService;
import org.crown.service.order.IOrderLogisticsService;
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
    private IOrderLogisticsService orderLogisticsService;
    @Autowired
    private ICouponService couponService;
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private ICouponCustomerService couponCustomerService;

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
            Order order = baseMapper.queryOrderByOrderNum(orderNum);
            order.setStatus(3);
            order.setConsignTime(LocalDateTime.now());
            updateById(order);
            baseMapper.updateLogisticsByOrderId(order.getId(), logisticsNumber, logisticsCompany);
            count++;
        }

        return count;
    }

    /**
     * @param orderLogisticsDTO 订单快递地址
     */
    @Transactional(readOnly = false)
    @Override
    public void updateLogistics( OrderLogisticsDTO orderLogisticsDTO) {
        baseMapper.updateLogisticsByOrderId(orderLogisticsDTO.getId(), orderLogisticsDTO.getLogisticsNumber(), orderLogisticsDTO.getLogisticsCompany());
        Order order = getById(orderLogisticsDTO.getId());
        order.setStatus(3);
        order.setConsignTime(LocalDateTime.now());
        updateById(order);
    }

    /**
     * 删除订单
     *
     * @param id
     */
    @Transactional(readOnly = false)
    @Override
    public void deleteOrder(Integer id) {
        Order order = getById(id);

        if (Objects.nonNull(order.getCouponId())) {
            CouponCustomer couponCustomer = new CouponCustomer();
            Customer customer = customerService.query().eq(Customer::getId, order.getCustomerId()).entity(e -> e);
            couponCustomer.setCouponId(order.getCouponId());
            couponCustomer.setOpenId(customer.getOpenId());
            couponCustomerService.save(couponCustomer);
            Coupon coupon = couponService.getById(order.getCouponId());
            coupon.setUsed(coupon.getUsed() - 1);
            couponService.updateById(coupon);
        }
        removeById(id);
        OrderLogistics entity = orderLogisticsService.query().eq(OrderLogistics::getOrderId, id).entity(e -> e);
        orderLogisticsService.removeById(entity);
    }
}