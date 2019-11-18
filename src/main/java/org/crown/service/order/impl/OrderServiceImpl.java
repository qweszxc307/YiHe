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

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.crown.common.mybatisplus.LambdaQueryWrapperChain;
import org.crown.framework.service.impl.BaseServiceImpl;
import org.crown.mapper.order.OrderMapper;
import org.crown.model.order.dto.OrderDAO;
import org.crown.model.order.dto.OrderDTO;
import org.crown.model.order.entity.Order;
import org.crown.model.order.parm.OrderPARM;
import org.crown.service.order.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ykMa
 */
@Service
        public class OrderServiceImpl extends BaseServiceImpl<OrderMapper, Order>implements IOrderService {


        @Override
        public List<OrderDTO> setOrderDTO(List<OrderDTO> records) {
                for (OrderDTO orderDTO : records) {
                        OrderDAO orderDAO = baseMapper.queryLogisticsByOrderId(orderDTO.getId());
                        if (Objects.nonNull(orderDAO)) {
                                //收货地址
                                orderDTO.setAddress(orderDAO.getProvince() + " " + orderDAO.getCity() + " " + orderDAO.getCity() + " " + orderDAO.getStreet());
                                //收件人
                                orderDTO.setAddressee(orderDAO.getAddressee());
                                //手机号
                                orderDTO.setPhone(orderDAO.getPhone());
                                //快递单号
                                orderDTO.setLogisticsNumber(orderDAO.getLogisticsNumber());
                        }

                }
                //地址
                return records;
        }

}
