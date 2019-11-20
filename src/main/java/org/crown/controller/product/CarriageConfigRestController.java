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
import org.crown.common.utils.TypeUtils;
import org.crown.enums.AuthTypeEnum;
import org.crown.framework.responses.ApiResponses;
import org.crown.model.product.dto.AreaDTO;
import org.crown.model.product.dto.CarriageConfigDTO;
import org.crown.model.product.dto.ProductDTO;
import org.crown.model.product.entity.Carriage;
import org.crown.model.product.entity.CarriageAreaCity;
import org.crown.model.product.entity.CarriageConfig;
import org.crown.model.product.entity.CarriageConfigPrice;
import org.crown.model.product.parm.CarriageConfigPARM;
import org.crown.model.product.parm.CarriagePARM;
import org.crown.service.product.ICarriageAreaCityService;
import org.crown.service.product.ICarriageConfigPriceService;
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
        @Autowired
        private ICarriageConfigPriceService carriageConfigPriceService;
        @Autowired
        private ICarriageAreaCityService carriageAreaCityService;

        @Resources(auth = AuthTypeEnum.AUTH)
        @ApiOperation("查询运费策略")
        @ApiImplicitParams({
                @ApiImplicitParam(name = "id", value = "产品运费模板ID", required = true, paramType = "path")
        })
        @GetMapping("/{id}")
        public ApiResponses<IPage<CarriageConfigDTO>> page(@PathVariable("id") Integer id) {
                return success(
                        carriageConfigService.selectCarriageConfigPage(this.getPage(),id)
                                .convert(e -> e.convert(CarriageConfigDTO.class))
                );
        }
        @Resources(auth = AuthTypeEnum.AUTH)
        @ApiOperation(value = "修改产品运费策略配置信息")
        @ApiImplicitParams({
                @ApiImplicitParam(name = "id", value = "产品运费策略配置信息ID", required = true, paramType = "path")
        })
        @PutMapping("/{id}")
        public ApiResponses<Void> update(@PathVariable("id") Integer id,@RequestBody @Validated(CarriageConfigPARM.Update.class) CarriageConfigPARM carriageConfigPARM) {
                /*修改运费策略配置基本信息*/
                CarriageConfig carriageConfig = new CarriageConfig();
                carriageConfig.setId(id);
                carriageConfig.setFreePrice(TypeUtils.castToBigDecimal(carriageConfigPARM.getCarriageConfigFreePrice()));
                carriageConfig.setName(carriageConfigPARM.getCarriageConfigName());
                carriageConfigService.saveOrUpdate(carriageConfig);
                /*修改运费策略配置价格信息*/
                List<CarriageConfigPrice> carriageConfigPrices = new ArrayList<>();
                for(int i = 0;i<carriageConfigPARM.getBnums().size();i++){
                        CarriageConfigPrice carriageConfigPrice = new CarriageConfigPrice();
                        carriageConfigPrice.setSNum(TypeUtils.castToInt(carriageConfigPARM.getBnums().get(i)));
                        carriageConfigPrice.setENum(TypeUtils.castToInt(carriageConfigPARM.getOnums().get(i)));
                        carriageConfigPrice.setPrice(TypeUtils.castToBigDecimal(carriageConfigPARM.getPrice().get(i)));
                        carriageConfigPrice.setConfigId(id);
                        carriageConfigPrices.add(carriageConfigPrice);

                }
                carriageConfigPriceService.delete().eq(CarriageConfigPrice::getConfigId,id).execute();
                carriageConfigPriceService.saveOrUpdateBatch(carriageConfigPrices);
                /*修改运费策略配置地区信息*/
                carriageAreaCityService.delete().eq(CarriageAreaCity::getConfigId,id).execute();
                List<CarriageAreaCity> areaCityList = new ArrayList<>();
                for(int i = 0;i<carriageConfigPARM.getCityIds().size();i++){
                        CarriageAreaCity carriageAreaCity = new CarriageAreaCity();
                        carriageAreaCity.setCityId(carriageConfigPARM.getCityIds().get(i));
                        carriageAreaCity.setConfigId(carriageConfig.getId());
                        areaCityList.add(carriageAreaCity);
                }
                carriageAreaCityService.saveBatch(areaCityList);
                return success();
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

        @Resources(auth = AuthTypeEnum.AUTH)
        @ApiOperation("获取运费配置对应相关运费区域")
        @ApiImplicitParams({
                @ApiImplicitParam(name = "id", value = "产品运费策略配置信息ID", required = true, paramType = "path")
        })
        @GetMapping(value = "/areas/{id}")
        public ApiResponses<List<String>> getAreasById(@PathVariable("id") Integer id) {
                return success(carriageConfigService.getAreasByConfigId(id));
        }

        @Resources(auth = AuthTypeEnum.AUTH)
        @ApiOperation("删除相关运费配置信息")
        @ApiImplicitParams({
                @ApiImplicitParam(name = "id", value = "产品运费策略配置信息ID", required = true, paramType = "path")
        })
        @DeleteMapping(value = "/{id}")
        public ApiResponses<Void> delete(@PathVariable("id") Integer id) {
                carriageAreaCityService.delete().eq(CarriageAreaCity::getConfigId,id).execute();
                carriageConfigService.delete().eq(CarriageConfig::getId,id).execute();
                carriageConfigPriceService.delete().eq(CarriageConfigPrice::getConfigId,id).execute();
                return success(HttpStatus.NO_CONTENT);
        }

}
