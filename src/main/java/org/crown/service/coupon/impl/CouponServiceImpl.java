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
package org.crown.service.coupon.impl;

import org.crown.framework.service.impl.BaseServiceImpl;
import org.crown.mapper.coupon.CouponMapper;
import org.crown.model.coupon.entity.Coupon;
import org.crown.model.coupon.entity.CouponBrand;
import org.crown.model.coupon.parm.CouponPARM;
import org.crown.service.coupon.ICouponBrandService;
import org.crown.service.coupon.ICouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ykMa
 */
@Service
public class CouponServiceImpl extends BaseServiceImpl<CouponMapper, Coupon> implements ICouponService {
    @Autowired
    private ICouponBrandService couponBrandService;

    /**
     * 修改状态
     *
     * @param id
     * @param status
     */
    @Transactional(readOnly = false)
    public void updateStatus(Integer id, Integer status) {
        if (baseMapper.updateStatus(id, status) == 0) {
            throw new RuntimeException("修改优惠券状态失败：{优惠券输出的值是： “0” ");
        }
    }

    /**
     * 修改优惠券
     * @param couponPARM 传入的优惠券
     */
    @Transactional(readOnly = false)
    @Override
    public void updateCoupon(CouponPARM couponPARM) {
        System.out.println("couponPARM = " + couponPARM);
        updateById(couponPARM.convert(Coupon.class));
        couponBrandService.delete().eq(CouponBrand::getCouponId, couponPARM.getId()).execute();
        CouponBrand couponBrand = new CouponBrand();
        couponBrand.setCouponId(couponPARM.getId());
        couponPARM.getBrands().forEach(e->{
            couponBrand.setBrandId(e);
            couponBrandService.save(couponBrand);
        });
    }

    /**
     * @param id
     */
    @Transactional(readOnly = false)
    @Override
    public void deleteCoupon(Integer id) {
        removeById(id);
        couponBrandService.delete().eq(CouponBrand::getCouponId, id).execute();
    }

    /**
     * 新增
     * @param couponPARM
     */
    @Override
    public void createCoupon(CouponPARM couponPARM) {
        Coupon convert = couponPARM.convert(Coupon.class);
        save(convert);
        CouponBrand couponBrand = new CouponBrand();
        couponBrand.setCouponId(couponPARM.getId());
        couponPARM.getBrands().forEach(e -> {
            couponBrand.setBrandId(e);
            couponBrandService.save(couponBrand);
        });
    }
}
