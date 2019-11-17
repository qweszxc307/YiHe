package org.crown.mapper.product;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;
import org.crown.model.product.dto.AreaDTO;
import org.crown.model.product.dto.CarriageConfigDTO;
import org.crown.model.product.dto.CarriageConfigPriceDTO;
import org.crown.model.product.entity.CarriageConfig;
import org.crown.framework.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 运费策略配置表 Mapper 接口
 * </p>
 *
 * @author whZhang
 */
@Mapper
public interface CarriageConfigMapper extends BaseMapper<CarriageConfig> {
        /**
         * 获取品牌列表
         * @param page
         * @param cid
         * @return
         */
        List<CarriageConfigDTO> selectCarriageConfigPage(IPage<CarriageConfigDTO> page, @Param("id") Integer cid);
        /**
         * 获取区域信息
         * @return
         */
        List<AreaDTO> getAreas();
}
