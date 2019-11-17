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
package org.crown.controller.product;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.crown.common.annotations.Resources;
import org.crown.enums.AuthTypeEnum;
import org.crown.framework.responses.ApiResponses;
import org.crown.model.product.dto.AreaDTO;
import org.crown.model.product.dto.CarriageConfigDTO;
import org.crown.model.product.dto.ProductDTO;
import org.crown.model.product.entity.Carriage;
import org.crown.model.product.entity.CarriageConfig;
import org.crown.model.product.parm.CarriageConfigPARM;
import org.crown.model.product.parm.CarriagePARM;
import org.crown.service.product.ICarriageConfigService;
import org.crown.service.product.ICarriageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;

import io.swagger.annotations.Api;
import org.crown.framework.controller.SuperController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 运费策略配置表 前端控制器
 * </p>
 *
 * @author whZhang
 */
@Api(tags = {"CarriageConfig"}, description = "运费策略配置表相关接口")
@RestController
@RequestMapping(value = "/carriageConfig", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class CarriageConfigRestController extends SuperController {

        @Autowired
        private ICarriageConfigService carriageConfigService;

        @Resources(auth = AuthTypeEnum.AUTH)
        @ApiOperation("查询运费模板相关运费策略")
        @ApiImplicitParams({
                @ApiImplicitParam(name = "id", value = "产品运费策略ID", required = true, paramType = "path")
        })
        @GetMapping("/{id}")
        public ApiResponses<IPage<CarriageConfigDTO>> page(@PathVariable("id") Integer id) {
                return success(
                        carriageConfigService.selectCarriageConfigPage(this.getPage(),id)
                                .convert(e -> e.convert(CarriageConfigDTO.class))
                );
        }

        @Resources(auth = AuthTypeEnum.AUTH)
        @ApiOperation("添加产品运费策略配置")
        @PostMapping
        public ApiResponses<Void> create(@RequestBody @Validated(CarriageConfigPARM.Create.class) CarriageConfigPARM carriageConfigPARM) {
                carriageConfigService.saveCarriageConfig(carriageConfigPARM);
                return success(HttpStatus.CREATED);
        }

        @Resources(auth = AuthTypeEnum.AUTH)
        @ApiOperation("获取运费区域")
        @GetMapping(value = "/areas")
        public ApiResponses<List<AreaDTO>> getAreas() {
                return success(carriageConfigService.getAreas());
        }

}
