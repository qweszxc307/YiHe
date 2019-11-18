package org.crown.mapper.order;

import org.apache.ibatis.annotations.Mapper;
import org.crown.framework.mapper.BaseMapper;
import org.crown.model.order.dto.OrderDAO;
import org.crown.model.order.entity.Order;

import java.time.LocalDateTime;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ykMa
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {


    OrderDAO queryLogisticsByOrderId(Integer id);
}
