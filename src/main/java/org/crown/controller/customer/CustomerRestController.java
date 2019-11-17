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
package org.crown.controller.customer;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.crown.common.annotations.Resources;
import org.crown.enums.AuthTypeEnum;
import org.crown.framework.controller.SuperController;
import org.crown.framework.responses.ApiResponses;
import org.crown.model.customer.dto.CustomerDTO;
import org.crown.model.customer.entity.Customer;
import org.crown.model.customer.parm.CustomerPARM;
import org.crown.service.customer.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ykMa
 */
@Api(tags = {"Customer"}, description = "客户相关接口")
@RestController
@RequestMapping(value = "/customer", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class CustomerRestController extends SuperController {
    @Autowired
    private ICustomerService customerService;



    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation("查询所有客户")
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
    }





    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation("查询单个客户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "客户ID", required = true, paramType = "path")
    })
    @GetMapping("/{id}")
    public ApiResponses<CustomerDTO> get(@PathVariable("id") Integer id) {
        Customer customer = customerService.getById(id);
        CustomerDTO customerDTO = customer.convert(CustomerDTO.class);
        return success(customerDTO);
    }



    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation(value = "删除客户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "客户ID", required = true, paramType = "path")
    })
    @DeleteMapping("/{id}")
    public ApiResponses<Void> delete(@PathVariable("id") Integer id) {
        customerService.removeById(id);
        return success(HttpStatus.NO_CONTENT);
    }


    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation(value = "添加客户")
    @PostMapping
    public ApiResponses<Void> create(@RequestBody @Validated(CustomerPARM.Create.class) CustomerPARM customerPARM) {
        customerService.save(customerPARM.convert(Customer.class));
        return success(HttpStatus.CREATED);
    }



    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation("修改客户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path")
    })
    @PutMapping("/{id}")
    public ApiResponses<Void> update(@PathVariable("id") Integer id, @RequestBody @Validated(CustomerPARM.Update.class) CustomerPARM customerPARM) {
        Customer customer = customerPARM.convert(Customer.class);
        customer.setId(id);
        customerService.updateById(customer);
        return success();
    }
}
