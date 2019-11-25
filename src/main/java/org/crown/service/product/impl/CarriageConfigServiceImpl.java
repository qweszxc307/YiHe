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
package org.crown.service.product.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.crown.common.utils.TypeUtils;
import org.crown.framework.responses.ApiResponses;
import org.crown.model.product.dto.AreaDTO;
import org.crown.model.product.dto.CarriageConfigDTO;
import org.crown.model.product.entity.CarriageAreaCity;
import org.crown.model.product.entity.CarriageConfig;
import org.crown.mapper.product.CarriageConfigMapper;
import org.crown.model.product.entity.CarriageConfigPrice;
import org.crown.model.product.parm.CarriageConfigPARM;
import org.crown.service.product.ICarriageAreaCityService;
import org.crown.service.product.ICarriageConfigPriceService;
import org.crown.service.product.ICarriageConfigService;
import org.crown.framework.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 运费策略配置表 服务实现类
 * </p>
 *
 * @author whZhang
 */
@Service
public class CarriageConfigServiceImpl extends BaseServiceImpl<CarriageConfigMapper, CarriageConfig>implements ICarriageConfigService {
        @Autowired
        ICarriageConfigService carriageConfigService;

        @Autowired
        ICarriageConfigPriceService carriageConfigPriceService;

        @Autowired
        ICarriageAreaCityService carriageAreaCityService;

        @Override
        public IPage<CarriageConfigDTO> selectCarriageConfigPage(IPage<CarriageConfigDTO> page, Integer cid) {
                List<CarriageConfigDTO> list = baseMapper.selectCarriageConfigPage(page,cid);
                return  page.setRecords(list);
        }

        @Override
        @Transactional(rollbackFor = Exception.class)
        public ApiResponses<Void> saveCarriageConfig(CarriageConfigPARM carriageConfigPARM) {
                /*保存运费策略基本信息*/
                CarriageConfig carriageConfig = new CarriageConfig();
                carriageConfig.setName(carriageConfigPARM.getCarriageConfigName());
                carriageConfig.setFreePrice(TypeUtils.castToBigDecimal(carriageConfigPARM.getCarriageConfigFreePrice()));
                carriageConfig.setCId(carriageConfigPARM.getId());
                carriageConfigService.save(carriageConfig);
                /*保存运费策略区域价格信息*/
                List<String> pricesList = carriageConfigPARM.getPrice();
                List<CarriageConfigPrice> carriageConfigPriceList = new ArrayList<>();
                for(int i=0;i<pricesList.size();i++){
                        CarriageConfigPrice carriageConfigPrice = new CarriageConfigPrice();
                        carriageConfigPrice.setConfigId(carriageConfig.getId());
                        carriageConfigPrice.setPrice(TypeUtils.castToBigDecimal(pricesList.get(i)));
                        carriageConfigPrice.setSNum(Integer.parseInt(carriageConfigPARM.getBnums().get(i)));
                        carriageConfigPrice.setENum(Integer.parseInt(carriageConfigPARM.getOnums().get(i)));
                        carriageConfigPriceList.add(carriageConfigPrice);
                }
                carriageConfigPriceService.saveBatch(carriageConfigPriceList);
                /*保存运费策略适用区域*/
                List<CarriageAreaCity> areaCityList = new ArrayList<>();
                for(int i = 0;i<carriageConfigPARM.getCityIds().size();i++){
                        int count = carriageAreaCityService.query().eq(CarriageAreaCity::getCarriageId,carriageConfigPARM.getId()).eq(CarriageAreaCity::getCityId,carriageConfigPARM.getCityIds().get(i)).count();
                        if(count==0){
                                CarriageAreaCity carriageAreaCity = new CarriageAreaCity();
                                carriageAreaCity.setCityId(carriageConfigPARM.getCityIds().get(i));
                                carriageAreaCity.setConfigId(carriageConfig.getId());
                                carriageAreaCity.setCarriageId(carriageConfigPARM.getId());
                                areaCityList.add(carriageAreaCity);
                        }
                }
                carriageAreaCityService.saveBatch(areaCityList);
                return null;
        }

        @Override
        public List<AreaDTO> getAreas() {
                List<AreaDTO> list = baseMapper.getAreas();
                return list;
        }

        @Override
        public List<String> getAreasByConfigId(Integer id) {
                List<String> list = baseMapper.getAreasByConfigId(id);
                return list;
        }
}
