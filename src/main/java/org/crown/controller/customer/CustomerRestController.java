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
import org.crown.model.customer.dto.CustomerDetailsDTO;
import org.crown.model.customer.entity.Customer;
import org.crown.model.customer.parm.CustomerPARM;
import org.crown.service.customer.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Resources(auth = AuthTypeEnum.OPEN)
    @ApiOperation("查询所有客户基本信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberNum", value = "会员号", paramType = "query")
    })
    @GetMapping
    public ApiResponses<IPage<CustomerDTO>> page(@RequestParam(value = "memberNum", required = false) String memberNum) {
            IPage<CustomerDTO> convert = customerService.query()
                    .eq(StringUtils.isNotEmpty(memberNum), Customer::getMemberNum, memberNum)
                    .page(this.<Customer>getPage())
                    .convert(e -> e.convert(CustomerDTO.class));
            convert.getRecords().forEach(e -> e.setMemberName(customerService.queryLevelBycId(e.getId())));
            return success(convert);


    }


    @Resources(auth = AuthTypeEnum.OPEN)
    @ApiOperation("查询会员详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "客户ID", required = true, paramType = "path")
    })
    @GetMapping("/{id}")
    public ApiResponses<CustomerDetailsDTO> get(@PathVariable("id") Integer id) {
        CustomerDetailsDTO customerDetailsDTO = customerService.queryByMid(id);
        System.out.println("customerDetailsDTO = " + customerDetailsDTO);
        return success(customerDetailsDTO);
    }


    @Resources(auth = AuthTypeEnum.OPEN)
    @ApiOperation(value = "添加客户")
    @PostMapping
    public ApiResponses<Void> create(@RequestBody @Validated(CustomerPARM.Create.class) CustomerPARM customerPARM) {
        /**
         * 1.获取微信信息
         * 传递到service 操作
         * 1.创建微信用户实体类接受用户信息
         * 2.创建会员号，获取微信名保存到用户表，再查询得到id
         * 3.获取微信名，地址 手机号，性别，会员等级默认为0 保存到会员详情表
         * 4.设置会员等级中间表， 查询用户id，获取用户等级id，赋值
         *
         */
        return null;
    }

    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation("修改客户等级")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path"),
    })
    @PutMapping("/{id}")
    public ApiResponses<Void> update(@PathVariable("id") Integer id, @RequestBody @Validated(CustomerPARM.Update.class) CustomerPARM customerPARM) {
        //修改详情表的id
      customerService.updateCustomerByMember(id,customerPARM);
        return success();
    }

}
