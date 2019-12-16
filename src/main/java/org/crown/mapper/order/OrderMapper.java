package org.crown.mapper.order;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.crown.framework.mapper.BaseMapper;
import org.crown.model.order.entity.Order;

import java.math.BigDecimal;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ykMa
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {





    /**
     * 修改订单价格
     *
     * @param id
     * @param totalFee
     */
    Integer updateTotalFeeByOrderId(@Param("id") Integer id, @Param("totalFee") BigDecimal totalFee);

    /**
     * 根据订单号查订单
     * @param orderNum
     * @return
     */
    Order queryOrderByOrderNum(@Param("orderNum") String orderNum);

    /**
     *
     * @param id
     * @param logisticsNumber
     * @param logisticsCompany
     */
    void updateLogisticsByOrderId(@Param("id") Integer id, @Param("logisticsNumber") String logisticsNumber, @Param("logisticsCompany") String logisticsCompany);
}
