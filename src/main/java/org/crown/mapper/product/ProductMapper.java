package org.crown.mapper.product;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.crown.framework.mapper.BaseMapper;
import org.crown.model.product.dto.ProductDTO;
import org.crown.model.product.entity.Product;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 产品表 Mapper 接口
 * </p>
 *
 * @author whZhang
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {
    /**
     * 获取品牌列表
     * @return
     */
    List<ProductDTO> selectProductPage(IPage<ProductDTO> page);
    /**
     * 获取产品信息(下拉框)
     * @return
     */
    List<Map<String,String>> selectProducts();
}
