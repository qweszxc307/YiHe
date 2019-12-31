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
package org.crown.controller.member;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.crown.common.annotations.Resources;
import org.crown.enums.AuthTypeEnum;
import org.crown.enums.ImagesEnum;
import org.crown.framework.responses.ApiResponses;
import org.crown.model.member.dto.MemberDTO;
import org.crown.model.member.dto.MemberImgDTO;
import org.crown.model.member.entity.Member;
import org.crown.model.member.parm.MemberPARM;
import org.crown.service.member.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import io.swagger.annotations.Api;
import org.crown.framework.controller.SuperController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 会员等级表 前端控制器
 * </p>
 *
 * @author ykMa
 */
@Api(tags = {"Member"}, description = "会员等级表相关接口")
@RestController
@RequestMapping(value = "/member", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class MemberRestController extends SuperController {
    @Autowired
    private IMemberService memberService;

    @Resources(auth = AuthTypeEnum.OPEN)
    @ApiOperation("查询所有会员等级")
    @GetMapping
    public ApiResponses<IPage<MemberDTO>> page() {
        return success(memberService.query().page(this.<Member>getPage()).convert(e -> e.convert(MemberDTO.class)));
    }
    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation("查询单个会员等级")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "会员等级id", required = true, paramType = "path")
    })
    @GetMapping("/{id}")
    public ApiResponses<MemberDTO> get(@PathVariable("id") Integer id) {
        return success(memberService.getById(id).convert(MemberDTO.class));
    }

    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation(value = "新增会员等级")
    @PostMapping
    public ApiResponses<Void> create(@RequestBody @Validated(MemberPARM.Create.class) MemberPARM memberPARM) {
        Member member = memberPARM.convert(Member.class);
        memberService.save(member);
        memberService.updateCustomerLevel(member);
        return success(HttpStatus.CREATED);
    }
    /**
     * 修改
     * 删除
     */
    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation(value = "修改会员等级")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "会员等级ID", required = true, paramType = "path")
    })
    @PutMapping("/{id}")
    public ApiResponses<Void> update(@PathVariable("id") Integer id,@RequestBody @Validated(MemberPARM.Update.class) MemberPARM memberPARM) {
        memberService.updateMember(id,memberPARM);
        return success();
    }


    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation(value = "删除客户等级")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "会员等级Id", required = true, paramType = "path")
    })
    @DeleteMapping("/{id}")
    public ApiResponses<Void> delete(@PathVariable("id") Integer id) {
        if (id != 1) {
            memberService.deleteMember(id);
        }
        return success();
    }

    @Resources(auth = AuthTypeEnum.OPEN)
    @ApiOperation("上传单个图片")
    @PostMapping(value = "/upload")
    public ApiResponses<MemberImgDTO> create(HttpServletResponse httpServletResponse, MultipartFile file) {
        return memberService.uploadImg(httpServletResponse,file, ImagesEnum.MEMBER_PRIVILEGE_IMAGE);
    }

}
