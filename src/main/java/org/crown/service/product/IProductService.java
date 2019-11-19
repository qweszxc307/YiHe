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
package org.crown.service.product;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.models.auth.In;
import org.crown.enums.StatusEnum;
import org.crown.model.product.dto.ProductDTO;
import org.crown.model.product.entity.Product;
import org.crown.framework.service.BaseService;
import org.crown.model.product.parm.ProductPARM;

import java.util.List;

/**
 * <p>
 * 产品表 服务类
 * </p>
 *
 * @author whZhang
 */
public interface IProductService extends BaseService<Product> {
    /**
     * 产品列表
     *
     * @param page
     */
    IPage<ProductDTO> selectProductPage(IPage<ProductDTO> page);

    /**
     * 添加产品
     *
     * @param productPARM
     */
    void createProduct(ProductPARM productPARM);

    /**
     * 修改产品状态
     *
     * @param productId
     * @param status
     */
    void updateStatus(Integer productId, StatusEnum status);
    /**
     * 修改产品信息
     *
     * @param id
     * @param productPARM
     */
    void updateProductById(Integer id,ProductPARM productPARM);
    /**
     * 删除产品信息
     *
     * @param id
     */
    void deletProductById(Integer id);

}
