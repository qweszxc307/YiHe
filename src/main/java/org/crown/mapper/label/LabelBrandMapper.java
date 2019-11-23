package org.crown.mapper.label;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.crown.framework.mapper.BaseMapper;
import org.crown.model.label.dto.LabelBrandDTO;
import org.crown.model.label.entity.LabelBrand;

import java.util.List;

/**
 * <p>
 * 品牌标签表
 * Mapper 接口
 * </p>
 *
 * @author ykMa
 */
@Mapper
public interface LabelBrandMapper extends BaseMapper<LabelBrand> {
    /**
     * 根据标签类型id删除用户标签中间表
     * @param id
     */
    void deleteLabelCustomerByBrandId(@Param("id") Long id);
}
