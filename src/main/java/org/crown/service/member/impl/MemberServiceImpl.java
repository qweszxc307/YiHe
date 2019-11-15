/*
 * Copyright (c) 2018-2022 Caratacus, (caratacus@qq.com).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.crown.service.member.impl;


import org.crown.mapper.customer.CustomerDetailsMapper;
import org.crown.mapper.customer.CustomerMapper;
import org.crown.model.member.entity.Member;
import org.crown.mapper.member.MemberMapper;
import org.crown.model.member.parm.MemberPARM;
import org.crown.service.member.IMemberService;
import org.crown.framework.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 会员等级表 服务实现类
 * </p>
 *
 * @author ykMa
 */
@Service
public class MemberServiceImpl extends BaseServiceImpl<MemberMapper, Member> implements IMemberService {
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private CustomerDetailsMapper customerDetailsMapper;

    /**
     * @param id         会员等级id
     * @param memberPARM 接受的修改的等级内容
     */
    @Transactional(readOnly = false)
    @Override
    public void updateMember(Integer id, MemberPARM memberPARM) {
        Member member = memberPARM.convert(Member.class);
        member.setId(id);
        Integer level = member.getLevel();
        List<Integer> cIds = memberMapper.queryCustomerIdByMemberId(id);
        if (cIds.size() != 0) {
            for (Integer cId : cIds) {
                Integer integer2 = customerMapper.updateCustomerMember(cId, id);
                Integer integer1 = customerMapper.updateCustomerLevelById(cId, level);
                Integer integer = customerDetailsMapper.updateLevelBycId(cId, level);
                if (!Objects.nonNull(integer) || !Objects.nonNull(integer1) || !Objects.nonNull(integer2)) {
                    throw new RuntimeException("修改客户等级出错");
                }
            }
        }
        memberMapper.updateById(member);
    }

    /**
     * 修改所有满足的客户修改其权限等级
     *
     * @param convert
     */
    @Transactional(readOnly = false)
    @Override
    public void updateCustomerLevel(Member convert) {
        Integer level = convert.getLevel();
        Integer mId = convert.getId();
////        Double upgrade = convert.getUpgrade();
//        Double nextUpgrade = memberMapper.queryNextUpgradeByUpgrade(upgrade);
//        List<Integer> cIds = customerMapper.queryIdsByUpgrade(upgrade, nextUpgrade);
//        for (Integer cId : cIds) {
//            customerMapper.updateCustomerMember(cId, mId);
//            customerMapper.updateCustomerLevelById(cId, level);
//            customerDetailsMapper.updateLevelBycId(cId, level);

        }

    /**
     * 根据id 删除会员等级
     * @param id 会员等级id
     */
    @Transactional(readOnly = false)
    @Override
    public void deleteMember(Integer id) {
        /**
         * 查找当前等级下所有的cid
         * 查询出客户的总消费
         * 设置等级
         */
        List<Integer> cIdList = customerMapper.queryCIdsByMId(id);
        if (cIdList != null || cIdList.size() <= 0) {
            for (Integer cId : cIdList) {
                Double sum = customerMapper.querySumByCId(cId);
            }
        }
    }
}
