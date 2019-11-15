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
package org.crown.service.customer.impl;

import lombok.extern.log4j.Log4j2;
import org.crown.framework.service.impl.BaseServiceImpl;
import org.crown.mapper.customer.CustomerDetailsMapper;
import org.crown.mapper.customer.CustomerMapper;
import org.crown.mapper.member.MemberMapper;
import org.crown.model.customer.dto.CustomerDetailsDTO;
import org.crown.model.customer.entity.Customer;
import org.crown.model.customer.parm.CustomerPARM;
import org.crown.model.member.entity.Member;
import org.crown.service.customer.ICustomerService;
import org.crown.service.label.ILabelBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ykMa
 */
@Log4j2
@Service
public class CustomerServiceImpl extends BaseServiceImpl<CustomerMapper, Customer> implements ICustomerService {
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private CustomerDetailsMapper customerDetailsMapper;
    @Autowired
    private ILabelBrandService labelBrandService;
    @Autowired
    private MemberMapper memberMapper;





    /**
     * 查询客户详情
     *
     * @param id 客户详情
     * @return CustomerDetailsDTO
     */
    @Override
    public CustomerDetailsDTO queryByMid(Integer id) {
        //查询详情表信息
        CustomerDetailsDTO detailsDTO = customerDetailsMapper.queryByCid(id).convert(CustomerDetailsDTO.class);
        Customer customer = getById(id);
        detailsDTO.setLastTime(customer.getLastTime());
        detailsDTO.setOrderNum(customer.getOrderNum());
        detailsDTO.setMemberName(customerMapper.queryLevelBycId(id));
        detailsDTO.setMemberNum(customer.getMemberNum());
        detailsDTO.setBrands(labelBrandService.queryLabelBrandDTOByCustomerId(id));
        return detailsDTO;
    }

    @Override
    public String queryLevelBycId(Integer cId) {
        return customerMapper.queryLevelBycId(cId);
    }

    /**
     * 修改客户等级
     *
     * @param id
     * @param customerPARM
     */
    @Transactional(readOnly = false)
    @Override
    public void updateCustomerByMember(Integer id, CustomerPARM customerPARM) {
        Customer customer = customerMapper.selectById(id);
        String memberName = customerPARM.getMemberName();
        Member member = memberMapper.queryMemberByMemberName(memberName);
        customer.setMId(member.getId());
        customerMapper.updateById(customer);
    }


}
