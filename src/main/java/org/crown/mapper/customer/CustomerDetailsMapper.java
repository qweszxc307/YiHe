package org.crown.mapper.customer;

import org.apache.ibatis.annotations.Mapper;

import org.crown.model.customer.entity.CustomerDetails;
import org.crown.framework.mapper.BaseMapper;

/**
 * <p>
 * 客户详情表 Mapper 接口
 * </p>
 *
 * @author whZhang
 */
@Mapper
public interface CustomerDetailsMapper extends BaseMapper<CustomerDetails> {

        Integer updateLevelBycId(Integer id, Integer level);

        CustomerDetails queryByCid(Integer id);
}
