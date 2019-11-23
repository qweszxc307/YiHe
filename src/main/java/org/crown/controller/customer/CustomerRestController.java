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
import org.crown.model.member.entity.Member;
import org.crown.service.customer.ICustomerService;
import org.crown.service.label.ILabelBrandService;
import org.crown.service.member.IMemberService;
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
    @Autowired
    private ILabelBrandService labelBrandService;
    @Autowired
    private IMemberService memberService;

    @Resources(auth = AuthTypeEnum.OPEN)
    @ApiOperation("查询所有客户基本信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberNum", value = "会员号", paramType = "query"),
            @ApiImplicitParam(name = "members", value = "会员等级名称", paramType = "query")

    })
    @GetMapping
    public ApiResponses<IPage<CustomerDTO>> page(@RequestParam(value = "memberNum", required = false) String memberNum,
                                                 @RequestParam(value = "members", required = false) String memberName) {
        Member member = new Member();
        if (memberName != null && memberName.length() != 0) {
            member = memberService.queryOneByMemberName(memberName);
        }
        IPage<CustomerDTO> convert = customerService.query()
                .eq(StringUtils.isNotEmpty(memberNum), Customer::getMemberNum, memberNum)
                .eq(Objects.nonNull(member.getId()), Customer::getMId, member.getId())
                .page(this.<Customer>getPage())
                .convert(e -> e.convert(CustomerDTO.class));
        convert.getRecords().forEach(e -> {
            e.setMemberName(customerService.queryLevelBycId(e.getId()));
            e.setLabelBrands(labelBrandService.queryLabelDTOByCustomerId(e.getId()));
        });
        return success(convert);
    }


    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation("修改客户等级")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path"),
    })
    @PutMapping("/{id}")
    public ApiResponses<Void> update(@PathVariable("id") Integer id, @RequestBody @Validated(CustomerPARM.Update.class) CustomerPARM customerPARM) {
        //修改详情表的id
        customerService.updateCustomerByMember(id, customerPARM);
        return success();
    }

}
