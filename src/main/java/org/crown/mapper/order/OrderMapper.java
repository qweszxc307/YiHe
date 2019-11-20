package org.crown.mapper.order;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.crown.framework.mapper.BaseMapper;
import org.crown.model.order.dto.OrderDAO;
import org.crown.model.order.dto.OrderDetailDTO;
import org.crown.model.order.entity.Order;
import org.crown.model.order.entity.OrderDetail;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
     * 根据订单id查询物流信息
     * @param id
     * @return
     */
    OrderDAO queryLogisticsByOrderId(Integer id);

    /**
     * 根据订单id查询下单时间，
     * @param id
     * @return
     */
    OrderDetailDTO queryOrderTimeByOrderId(Integer id);

    /**
     * 根据订单id查询订单中的商品信息
     * @param id
     * @return
     */
    List<OrderDetail> queryOrderDetailByOrderId(Integer id);

    /**
     * 根据订单id查询用户id
     * @param id
     * @return
     */
    Integer queryCustomerIdByOrderId(Integer id);

    /**
     * 修改订单价格
     *
     * @param id
     * @param totalFee
     */
    Integer updateTotalFeeByOrderId(@Param("id") Integer id, @Param("totalFee") BigDecimal totalFee);

}
