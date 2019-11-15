package org.crown.mapper.product;

import org.apache.ibatis.annotations.Mapper;

import org.crown.model.product.entity.ProductImage;
import org.crown.framework.mapper.BaseMapper;

/**
 * <p>
 * 产品_图片关系表 Mapper 接口
 * </p>
 *
 * @author whZhang
 */
@Mapper
public interface ProductImageMapper extends BaseMapper<ProductImage> {

        }
