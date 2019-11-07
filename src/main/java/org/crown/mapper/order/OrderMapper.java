package org.crown.mapper.order;

import org.apache.ibatis.annotations.Mapper;
import org.crown.framework.mapper.BaseMapper;
import org.crown.model.customer.entity.Customer;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ykMa
 */
@Mapper
public interface OrderMapper extends BaseMapper<Customer> {

        }
