package org.crown.mapper.order;

import org.apache.ibatis.annotations.Mapper;
import org.crown.framework.mapper.BaseMapper;
import org.crown.model.order.entity.City;

/**
 * <p>
 * 省，市，区，联动表 Mapper 接口
 * </p>
 *
 * @author ykMa
 */
@Mapper
public interface CityMapper extends BaseMapper<City> {

        }
