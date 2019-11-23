package org.crown.mapper.label;

import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;
import org.crown.model.label.entity.Label;
import org.crown.framework.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 标签表 Mapper 接口
 * </p>
 *
 * @author ykMa
 */
@Mapper
public interface LabelMapper extends BaseMapper<Label> {

    /**
     * 查询用户下的所有标签
     * @param id
     * @return
     */
    List<Integer> queryLabelIdsByCustomerId(@Param("id") Integer id);

    /**
     * 删除中间表
     * @param id
     */
    void deleteLabelCustomerByLabelId( @Param("id") Integer id);

    /**
     * 根据标签类型id删除标签
     *
     * @param id
     */
    void deleteByBrandId(@Param("id") Long id);
}
