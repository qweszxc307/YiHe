package org.crown.mapper.coupon;

import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;
import org.crown.model.coupon.entity.Coupon;
import org.crown.framework.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ykMa
 */
@Mapper
public interface CouponMapper extends BaseMapper<Coupon> {
    /**
     * 修改优惠券状态
     *
     * @param id
     * @param status
     * @return
     */
    Integer updateStatus(@Param("id") Integer id, @Param("status") Integer status);

}
