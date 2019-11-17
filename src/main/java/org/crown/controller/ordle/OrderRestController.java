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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.crown.common.annotations.Resources;
import org.crown.enums.AuthTypeEnum;
import org.crown.framework.controller.SuperController;
import org.crown.framework.responses.ApiResponses;
import org.crown.model.order.dto.OrderDTO;
import org.crown.model.order.entity.Order;
import org.crown.service.order.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @Autowired
    private IOrderService orderService;


    @Resources(auth = AuthTypeEnum.OPEN)
    @ApiOperation("查询所有订单")
    @GetMapping
    public ApiResponses<IPage<OrderDTO>> page() {
        return null;
    }
}
   /* @ApiOperation("查询所有客户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "真实姓名", paramType = "query"),
            @ApiImplicitParam(name = "nickname", value = "微信名称", paramType = "query"),
            @ApiImplicitParam(name = "phone", value = "手机号", paramType = "query"),
            @ApiImplicitParam(name = "role", value = "会员等级", paramType = "query"),
            @ApiImplicitParam(name = "price_min", value = "消费筛选最小值", paramType = "query"),
            @ApiImplicitParam(name = "price_max", value = "消费筛选最大值", paramType = "query")
    })
    @GetMapping
    public ApiResponses<IPage<CustomerDTO>> page(@RequestParam(value = "name", required = false) String name,
                                                 @RequestParam(value = "nickname", required = false) String nickname,
                                                 @RequestParam(value = "phone", required = false) String phone,
                                                 @RequestParam(value = "role", required = false) String role,
                                                 @RequestParam(value = "price_min", required = false) Double price_min,
                                                 @RequestParam(value = "price_max", required = false) Double price_max) {
        return success(customerService.query().likeRight(StringUtils.isNotEmpty(name), Customer::getName, name)
                .likeRight(StringUtils.isNotEmpty(nickname), Customer::getNickname, nickname)
                .likeRight(StringUtils.isNotEmpty(phone), Customer::getPhone, phone)
                .like(StringUtils.isNotEmpty(role), Customer::getRole, role)
                .ge(Objects.nonNull(price_min), Customer::getCost, price_min)
                .le(Objects.nonNull(price_max), Customer::getCost, price_max)
                .page(this.<Customer>getPage())
                .convert(e -> e.convert(CustomerDTO.class))
        );
    }*/




