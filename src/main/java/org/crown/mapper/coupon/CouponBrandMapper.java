package org.crown.mapper.coupon;

import org.apache.ibatis.annotations.Mapper;
import org.crown.framework.mapper.BaseMapper;
import org.crown.model.coupon.entity.CouponBrand;

/**
 * <p>
 * 优惠券和品牌关联表 Mapper 接口
 * </p>
 *
 * @author ykMa
 */
@Mapper
public interface CouponBrandMapper extends BaseMapper<CouponBrand> {

}
