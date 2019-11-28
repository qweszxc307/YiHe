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
package org.crown.controller.ordle;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.swagger.annotations.*;

import lombok.extern.slf4j.Slf4j;
import org.crown.common.annotations.Resources;
import org.crown.common.utils.POIUtils;
import org.crown.enums.AuthTypeEnum;
import org.crown.framework.controller.SuperController;
import org.crown.framework.responses.ApiResponses;
import org.crown.model.customer.entity.Customer;
import org.crown.model.order.dto.OrderDTO;
import org.crown.model.order.dto.OrderUploadDTO;
import org.crown.model.order.entity.Order;
import org.crown.model.order.entity.OrderLogistics;
import org.crown.model.order.parm.OrderPARM;
import org.crown.service.customer.ICustomerService;
import org.crown.service.order.IOrderLogisticsService;
import org.crown.service.order.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ykMa
 */
@Slf4j
@Api(tags = {"Order"}, description = "订单相关接口")
@RestController
@RequestMapping(value = "/order", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class OrderRestController extends SuperController {
    private final Integer delete = 0;
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IOrderLogisticsService orderLogisticsService;


    @Resources(auth = AuthTypeEnum.OPEN)
    @ApiOperation("查询所有订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "订单状态", paramType = "query"),
            @ApiImplicitParam(name = "orderNum", value = "订单号", paramType = "query"),
            @ApiImplicitParam(name = "beforeTime", value = "开始时间", paramType = "query"),
            @ApiImplicitParam(name = "afterTime", value = "结束时间", paramType = "query")
    })
    @GetMapping
    public ApiResponses<IPage<OrderDTO>> page(@RequestParam(value = "status", required = false) Integer status,
                                              @RequestParam(value = "orderNum", required = false) String orderNum,
                                              @RequestParam(value = "beforeTime", required = false) String beforeTime,
                                              @RequestParam(value = "afterTime", required = false) String afterTime) {
        IPage<OrderDTO> convert = orderService.query()
                .eq(Objects.nonNull(status), Order::getStatus, status)
                .eq(StringUtils.isNotEmpty(orderNum), Order::getOrderNum, orderNum)
                .ge(StringUtils.isNotEmpty(beforeTime), Order::getCreateTime, beforeTime)
                .le(StringUtils.isNotEmpty(afterTime), Order::getCreateTime, afterTime)
                .page(this.<Order>getPage())
                .convert(e -> {
                    OrderDTO order = e.convert(OrderDTO.class);
                    Customer entity = customerService.query().eq(Customer::getId, e.getCustomerId()).entity(d -> d);
                    order.setCustomerNum(entity.getMemberNum());
                    order.setOrderLogistics(orderLogisticsService.query().eq(OrderLogistics::getOrderId, e.getId()).entity(o -> o));
                    return order;
                });
        return success(convert);
    }


    /**
     * 退款之后也需要删除订单   删除订单之后要查询用户消费把消费回退到之前的状态
     * @param id
     * @return
     */
    @Resources(auth = AuthTypeEnum.OPEN)
    @ApiOperation(value = "删除订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单ID", required = true, paramType = "path")
    })
    @DeleteMapping("/{id}")
    public ApiResponses<Void> delete(@PathVariable("id") Integer id) {
        Order order = orderService.getById(id);
        order.setStatus(delete);
        orderService.updateById(order);
        return success(HttpStatus.NO_CONTENT);
    }


    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation("修改订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单ID", required = true, paramType = "path")
    })
    @PutMapping("/{id}/address")
    public ApiResponses<Void> update(@PathVariable("id") Integer id, @RequestBody @Validated(OrderPARM.Update.class) OrderPARM orderPARM) {
        Order order = orderPARM.convert(Order.class);
        order.setId(id);
        orderService.updateById(order);
        return success();
    }

    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation("订单改价")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单ID", required = true, paramType = "path"),
            @ApiImplicitParam(name = "totalFee", value = "修改的价格", required = true, paramType = "path")
    })
    @PutMapping("/{id}")
    public ApiResponses<Void> updateTotalFee(@PathVariable("id") Integer id, @RequestBody BigDecimal totalFee) {
        try {
            orderService.updateTotalFeeByOrderId(id, totalFee);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success();
    }




    @Resources(auth = AuthTypeEnum.OPEN)
    @ApiOperation("订单批量发货")
    @PostMapping("/upload")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "logisticsCompany", value = "快递公司名称", required = true, paramType = "path"),
    })
    public ApiResponses<Integer> upload(MultipartFile file, @RequestParam(value = "logisticsCompany") String logisticsCompany) {
        Integer count = 0;
        try {
            List<String[]> list = POIUtils.readExcel(file);
            List<OrderUploadDTO> upload = new ArrayList<>();
            for (String[] strings : list) {
                OrderUploadDTO orderUploadDTO = new OrderUploadDTO(strings[0], strings[1]);
                upload.add(orderUploadDTO);
            }
            count = orderService.upload(upload, logisticsCompany);
        } catch (IOException e) {
            log.error("批量发货出现异常:", e);
        }
        return success(count);
    }
}






















