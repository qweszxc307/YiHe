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

import org.crown.common.mybatisplus.LambdaQueryWrapperChain;
import org.crown.framework.service.impl.BaseServiceImpl;
import org.crown.mapper.order.OrderMapper;
import org.crown.model.order.entity.Order;
import org.crown.model.order.parm.OrderPARM;
import org.crown.service.order.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ykMa
 */
@Service
        public class OrderServiceImpl extends BaseServiceImpl<OrderMapper, Order> implements IOrderService {
        @Autowired
        private OrderMapper orderMapper;

        /**
         * 修改订单
         * @param id  订单id
         * @param orderPARM  修改内容
         */
        @Override
        public void updateOrder(Integer id, OrderPARM orderPARM) {
                Order order = orderPARM.convert(Order.class);
                order.setId(id);
                updateById(order);
        }
}
