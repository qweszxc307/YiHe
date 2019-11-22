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
package org.crown.controller.coupon;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.crown.common.annotations.Resources;
import org.crown.enums.AuthTypeEnum;
import org.crown.framework.responses.ApiResponses;
import org.crown.model.brand.dto.BrandDTO;
import org.crown.model.coupon.dto.CouponDTO;
import org.crown.model.coupon.entity.Coupon;
import org.crown.model.coupon.parm.CouponPARM;
import org.crown.service.brand.impl.BrandServiceImpl;
import org.crown.service.coupon.ICouponService;
import org.crown.service.coupon.impl.CouponServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;

import io.swagger.annotations.Api;
import org.crown.framework.controller.SuperController;

/**
 * <p>
 * 优惠券模块
 * </p>
 *
 * @author ykMa
 */
@Api(tags = {"Coupon"}, description = "相关接口")
@RestController
@RequestMapping(value = "/coupon", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class CouponRestController extends SuperController {
    @Autowired
    private ICouponService couponService;
    @Autowired
    private BrandServiceImpl brandService;

    @Resources(auth = AuthTypeEnum.OPEN)
    @ApiOperation("查询所有优惠券")
    @GetMapping
    public ApiResponses<IPage<CouponDTO>> page() {
        return success(couponService.query().page(this.getPage()).convert(e -> {
            CouponDTO convert = e.convert(CouponDTO.class);
            convert.setBrand(brandService.getBrandById(e.getBrandId()));
            return convert;
        }));
    }

    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation(value = "修改优惠券")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "产品ID", required = true, paramType = "path")
    })
    @PutMapping("/{id}")
    public ApiResponses<Void> update(@PathVariable("id") Integer id, @RequestBody @Validated(CouponPARM.Update.class) CouponPARM couponPARM) {
        couponService.updateById(couponPARM.convert(Coupon.class));
        return success();
    }



    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation(value = "添加优惠券")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "产品ID", required = true, paramType = "path")
    })
    @PostMapping
    public ApiResponses<Void> create(@PathVariable("id") Integer id, @RequestBody @Validated(CouponPARM.Update.class) CouponPARM couponPARM) {
        couponService.updateById(couponPARM.convert(Coupon.class));
        return success();
    }




    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation(value = "删除优惠券")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "产品ID", required = true, paramType = "path")
    })
    @DeleteMapping("/{id}")
    public ApiResponses<Void> delete(@PathVariable("id") Integer id) {
        couponService.removeById(id);
        return success();
    }





    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation(value = "修改优惠状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "产品ID", required = true, paramType = "path")
    })
    @PutMapping("/{id}/status")
    public ApiResponses<Void> status(@PathVariable("id") Integer id, @RequestBody @Validated(CouponPARM.Update.class) CouponPARM couponPARM) {
        couponService.updateStatus(id, couponPARM.getStatus());
        return success();
    }

}
