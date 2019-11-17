package org.crown.mapper.brand;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;
import org.crown.enums.MenuTypeEnum;
import org.crown.enums.StatusEnum;
import org.crown.model.brand.dto.BrandDTO;
import org.crown.model.brand.entity.Brand;
import org.crown.framework.mapper.BaseMapper;
import org.crown.model.system.dto.MenuTreeDTO;

import java.util.List;

/**
 * <p>
 * 品牌表 Mapper 接口
 * </p>
 *
 * @author whZhang
 */
@Mapper
public interface BrandMapper extends BaseMapper<Brand> {

        /**
         * 获取品牌列表
         * @return
         */
        List<BrandDTO> getBrandPage(IPage<BrandDTO> page);
}
