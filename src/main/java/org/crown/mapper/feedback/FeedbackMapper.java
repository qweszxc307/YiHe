package org.crown.mapper.feedback;

import org.apache.ibatis.annotations.Mapper;

import org.crown.model.feedback.entity.Feedback;
import org.crown.framework.mapper.BaseMapper;

/**
 * <p>
 * 反馈信息表 Mapper 接口
 * </p>
 *
 * @author whZhang
 */
@Mapper
public interface FeedbackMapper extends BaseMapper<Feedback> {

        }
