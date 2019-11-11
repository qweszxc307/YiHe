package org.crown.mapper.order;

import org.apache.ibatis.annotations.Mapper;
import org.crown.framework.mapper.BaseMapper;
import org.crown.model.customer.entity.Customer;
import org.crown.model.order.entity.Order;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ykMa
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

        }
