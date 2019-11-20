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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.crown.common.annotations.Resources;
import org.crown.enums.AuthTypeEnum;
import org.crown.framework.controller.SuperController;
import org.crown.framework.responses.ApiResponses;
import org.crown.model.product.dto.CarriageDTO;
import org.crown.model.product.entity.Carriage;
import org.crown.model.product.entity.CarriageAreaCity;
import org.crown.model.product.entity.CarriageConfig;
import org.crown.model.product.entity.CarriageConfigPrice;
import org.crown.model.product.parm.CarriagePARM;
import org.crown.service.product.ICarriageAreaCityService;
import org.crown.service.product.ICarriageConfigPriceService;
import org.crown.service.product.ICarriageConfigService;
import org.crown.service.product.ICarriageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 产品运费策略信息表 前端控制器
 * </p>
 *
 * @author whZhang
 */
@Api(tags = {"Carriage"}, description = "产品运费策略信息表相关接口")
@RestController
@RequestMapping(value = "/carriage", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class CarriageRestController extends SuperController {

        @Autowired
        private ICarriageService carriageService;
        @Autowired
        private ICarriageConfigService carriageConfigService;
        @Autowired
        private ICarriageConfigPriceService carriageConfigPriceService;
        @Autowired
        private ICarriageAreaCityService carriageAreaCityService;

        @Resources(auth = AuthTypeEnum.AUTH)
        @ApiOperation("查询所有运费模板")
        @GetMapping
        public ApiResponses<IPage<CarriageDTO>> page() {
                return success(
                        carriageService.query().page(this.<Carriage>getPage())
                                .convert(e -> e.convert(CarriageDTO.class))
                );
        }

        @Resources(auth = AuthTypeEnum.AUTH)
        @ApiOperation("查询所有运费模板")
        @GetMapping(value = "/carriages")
        public ApiResponses<List<Carriage>> list() {
                return success(carriageService.list());
        }


        @Resources(auth = AuthTypeEnum.AUTH)
        @ApiOperation("添加产品运费运费模板")
        @PostMapping
        public ApiResponses<Void> create(@RequestBody @Validated(CarriagePARM.Create.class) CarriagePARM carriagePARM) {
                carriageService.save(carriagePARM.convert(Carriage.class));
                return success(HttpStatus.CREATED);
        }

        @Resources(auth = AuthTypeEnum.AUTH)
        @ApiOperation(value = "删除产品运费运费模板")
        @ApiImplicitParams({
                @ApiImplicitParam(name = "id", value = "产品运费模板ID", required = true, paramType = "path")
        })

        @DeleteMapping("/{id}")
        public ApiResponses<Void> delete(@PathVariable("id") Integer id) {
                carriageService.delete().eq(Carriage::getId,id).execute();
                return success(HttpStatus.NO_CONTENT);
        }

        @Resources(auth = AuthTypeEnum.AUTH)
        @ApiOperation(value = "查询产品运费模板")
        @ApiImplicitParams({
                @ApiImplicitParam(name = "id", value = "产品运费模板ID", required = true, paramType = "path")
        })

        @GetMapping("/{id}")
        public ApiResponses<CarriageDTO> get(@PathVariable("id") Integer id) {
                Carriage carriage = carriageService.getById(id);
                CarriageDTO carriageDTO = carriage.convert(CarriageDTO.class);
                return success(carriageDTO);
        }

        @Resources(auth = AuthTypeEnum.AUTH)
        @ApiOperation(value = "修改产品运费模板")
        @ApiImplicitParams({
                @ApiImplicitParam(name = "id", value = "产品运费模板ID", required = true, paramType = "path")
        })
        @PutMapping("/{id}")
        public ApiResponses<Void> update(@PathVariable("id") Integer id,@RequestBody @Validated(CarriagePARM.Create.class) CarriagePARM carriagePARM) {
                Carriage carriage = carriagePARM.convert(Carriage.class);
                carriage.setId(id);
                carriageService.updateById(carriage);
                return success();
        }
}
