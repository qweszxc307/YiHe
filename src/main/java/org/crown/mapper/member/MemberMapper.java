package org.crown.mapper.member;

import org.apache.ibatis.annotations.Mapper;

import org.crown.model.member.entity.Member;
import org.crown.framework.mapper.BaseMapper;
import org.crown.model.member.entity.MemberSum;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 会员等级表 Mapper 接口
 * </p>
 *
 * @author ykMa
 */
@Mapper
public interface MemberMapper extends BaseMapper<Member> {

    /**
     * 根据会员等级查询出所有当前等级下的客户id
     * @param id
     */
    List<Integer> queryCustomerIdByMemberId(Integer id);

    /**
     * 查询所有等级
     * @return 所有等级
     */
    List<Member> queryAllMember();

    /**
     * 根据等级查询等级列表id
     * @param level 等级
     * @return mId
     */
    Integer queryMemberIdByLevel(Integer level);

    /**
     * 查询下一级别 等级id和升级条件
     * @param upgrade
     * @return
     */
    MemberSum queryNextUpgradeByUpgrade(BigDecimal upgrade);

    /**
     * 查询上一级别的等级id 和升级条件
     * @param upgrade
     * @return
     */
    MemberSum queryFrontUpgradeByUpgrade(BigDecimal upgrade);
    /**
     * 根据等级名称查询 等级实体类
     * @param memberName
     * @return
     */
    Member queryMemberByMemberName(String memberName);


}
