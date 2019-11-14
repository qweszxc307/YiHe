package org.crown.mapper.product;

import org.apache.ibatis.annotations.Mapper;

import org.crown.model.product.entity.ProductPrice;
import org.crown.framework.mapper.BaseMapper;

/**
 * <p>
 * 产品价格配置表 Mapper 接口
 * </p>
 *
 * @author whZhang
 */
@Mapper
public interface ProductPriceMapper extends BaseMapper<ProductPrice> {

        }
