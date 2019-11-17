package org.crown.mapper.product;

import org.apache.ibatis.annotations.Mapper;

import org.crown.model.product.entity.ProductCarriage;
import org.crown.framework.mapper.BaseMapper;

/**
 * <p>
 * 产品运费策略关系表 Mapper 接口
 * </p>
 *
 * @author whZhang
 */
@Mapper
public interface ProductCarriageMapper extends BaseMapper<ProductCarriage> {

}
