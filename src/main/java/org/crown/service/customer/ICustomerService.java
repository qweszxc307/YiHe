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
package org.crown.service.customer;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.crown.framework.service.BaseService;
import org.crown.model.customer.dto.CustomerDTO;
import org.crown.model.customer.dto.CustomerDetailsDTO;
import org.crown.model.customer.entity.Customer;
import org.crown.model.customer.parm.CustomerPARM;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ykMa
 */
public interface ICustomerService extends BaseService<Customer> {

    /**
     * 根据客户id查询客户详情
     * @param id
     * @return
     */
    CustomerDetailsDTO queryByMid(Integer id);

    /**
     * 根据id查询用户等级
     * @param cId 用户id
     * @return 客户等级
     */
    String queryLevelBycId(Integer cId);

    /**
     * 修改 会员状态
     * @param id
     * @param customerPARM
     */

    void updateCustomerByMember(Integer id, CustomerPARM customerPARM);
}
