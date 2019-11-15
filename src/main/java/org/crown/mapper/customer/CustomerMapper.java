package org.crown.mapper.customer;

import org.apache.ibatis.annotations.Mapper;
import org.crown.framework.mapper.BaseMapper;
import org.crown.model.customer.entity.Customer;
import org.crown.model.customer.entity.CustomerMember;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ykMa
 */
@Mapper
public interface CustomerMapper extends BaseMapper<Customer> {
    /**
     * 根据等级查询会员
     *
     * @param level 用户特权等级
     * @return 特权表id
     */
    Integer getMemberIdByLevel(Integer level);

    /**
     * 修改中间表
     * @param id  用户id
     * @param mId 会员等级id
     * @return 执行结果
     */
    Integer updateCustomerMember(Integer id, Integer mId);

    /**
     * 根据 用户id修改等级
     * @param cId   用户id
     * @param level 会员等级
     */
    Integer updateCustomerLevelById(Integer cId, Integer level);

    /**
     * 查询等级之间所有的客户id
     * @param upgrade     等级
     * @param nextUpgrade 下一级别
     * @return 客户id集合
     */

    List<Integer> queryIdsByUpgrade(Double upgrade, Double nextUpgrade);

    List<Integer> queryCIdsByMId(Integer mId);

    Double querySumByCId(Integer cId);

    /**
     * 查询所有用户
     * @param level  等级
     * @param memberNum 会员号
     * @return null
     */
    List<Customer> queryAllCustomer(Integer level, String memberNum);

    /**
     * 根据 客户id查询等级
     * @param cId  客户id
     * @return level 等级
     */
    String queryLevelBycId(Integer cId);
}
