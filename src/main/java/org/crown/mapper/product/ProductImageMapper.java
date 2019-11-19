package org.crown.mapper.product;

import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;
import org.crown.model.product.dto.ProductImgDTO;
import org.crown.model.product.entity.ProductImage;
import org.crown.framework.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 产品_图片关系表 Mapper 接口
 * </p>
 *
 * @author whZhang
 */
@Mapper
public interface ProductImageMapper extends BaseMapper<ProductImage> {
        /**
             * @param pid
             * @return List<ProductImgDTO>
             */
         List<ProductImgDTO> getProductImagesById(@Param("pId") Integer pid);
}
