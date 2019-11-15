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

import org.crown.common.annotations.Resources;
import org.crown.enums.AuthTypeEnum;
import org.crown.framework.controller.SuperController;
import org.crown.framework.responses.ApiResponses;
import org.crown.model.order.dto.OrderDTO;
import org.crown.model.order.entity.Order;
import org.crown.model.order.parm.OrderPARM;
import org.crown.service.order.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ykMa
 */
@Api(tags = {"Order"}, description = "订单相关接口")
@RestController
@RequestMapping(value = "/order", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class OrderRestController extends SuperController {
    private Integer delete = 0;
    @Autowired
    private IOrderService orderService;


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

        return success(orderService.query()
                .eq(Objects.nonNull(status), Order::getStatus, status)
                .eq(StringUtils.isNotEmpty(orderNum), Order::getOrderNum, orderNum)
                .ge(StringUtils.isNotEmpty(beforeTime), Order::getCreateTime, beforeTime)
                .le(StringUtils.isNotEmpty(afterTime), Order::getCreateTime, afterTime)
                .page(this.<Order>getPage())
                .convert(e -> e.convert(OrderDTO.class)));
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
        orderService.getById(id).setStatus(delete);
        return success(HttpStatus.NO_CONTENT);
    }


    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation("修改订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单ID", required = true, paramType = "path")
    })
    @PutMapping("/{id}")
    public ApiResponses<Void> update(@PathVariable("id") Integer id, @RequestBody @Validated(OrderPARM.Update.class) OrderPARM orderPARM) {
        Order order = orderPARM.convert(Order.class);
        order.setId(id);
        orderService.updateById(order);
        return success();
    }


    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation("生成订单")
    @PostMapping
    public ApiResponses<Void> create() {
        //TODO
        /*
    addressId：收货人地址信息的id，需要去用户中心查询收货人地址
    carts：购物车中的商品数据，可以有多个对象
    num：购物车中指定商品的购买数量
    skuId：购物车中的某商品的id
    paymentType：付款方式：1 在线支付，2 货到付款
        数据类型：{ addressId：xxx ，carts：[skuId：xx，num：xx]，paymentType：xx}
        * 先定义  orderDTO类 接受前端传输的数据，  包含字段   addressId，paymentType， List<cartDTO>
        再定义   CartDTO 保存 skuId，num，
        1.减库存
        2.生成订单号
        3.遍历集合查询商品，获取商品信息 ，存入订单详情表
        * */
        return null;

    }

}






















