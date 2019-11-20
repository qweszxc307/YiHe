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
import org.crown.framework.responses.ApiResponses;
import org.crown.model.product.dto.AreaDTO;
import org.crown.model.product.dto.CarriageConfigDTO;
import org.crown.model.product.entity.CarriageConfig;
import org.crown.framework.service.BaseService;
import org.crown.model.product.parm.CarriageConfigPARM;

import java.util.List;

/**
 * <p>
 * 运费策略配置表 服务类
 * </p>
 *
 * @author whZhang
 */
public interface ICarriageConfigService extends BaseService<CarriageConfig> {
        /**
         * 运费策略价格列表
         *
         * @param page
         */
        IPage<CarriageConfigDTO> selectCarriageConfigPage(IPage<CarriageConfigDTO> page, Integer cid);

        /**
         * 添加运费策略配置
         *
         * @param carriageConfigPARM
         */
        ApiResponses<Void>  saveCarriageConfig(CarriageConfigPARM carriageConfigPARM);

        /**
         * 获取运费策略配置区域信息
         *
         */
        List<AreaDTO> getAreas();

        /**
         * 获取运费策略配置区域信息
         *
         */
        List<String>getAreasByConfigId(Integer id);

}
