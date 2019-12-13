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
package org.crown.controller.activity;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.crown.common.annotations.Resources;
import org.crown.enums.AuthTypeEnum;
import org.crown.enums.StatusEnum;
import org.crown.framework.enums.ErrorCodeEnum;
import org.crown.framework.responses.ApiResponses;
import org.crown.framework.utils.ApiAssert;
import org.crown.model.activity.dto.MarketRecommendDTO;
import org.crown.model.activity.entity.MarketRecommend;
import org.crown.model.activity.param.MarketRecommendPARM;
import org.crown.model.brand.dto.BrandDTO;
import org.crown.model.brand.dto.BrandImgDTO;
import org.crown.model.brand.entity.Brand;
import org.crown.model.brand.parm.BrandPARM;
import org.crown.model.product.entity.ProductPrice;
import org.crown.service.activity.IMarketRecommendService;
import org.crown.service.brand.IBrandService;
import org.crown.service.product.IProductPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;

import io.swagger.annotations.Api;
import org.crown.framework.controller.SuperController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 推荐返礼信息表 前端控制器
 * </p>
 *
 * @author whZhang
 */
@Api(tags = {"MarketRecommendDTO"}, description = "推荐返礼信息表相关接口")
@RestController
@RequestMapping(value = "/marketRecommend", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class MarketRecommendRestController extends SuperController {
        @Autowired
        private IMarketRecommendService marketRecommendService;
        @Autowired
        private IProductPriceService productPriceService;

        @Resources(auth = AuthTypeEnum.AUTH)
        @ApiOperation("查询所有推荐返礼活动(分页)")
        @GetMapping
        public ApiResponses<IPage<MarketRecommendDTO>> page() {
                return success(
                        marketRecommendService.selectMarketRecommendPage(this.<MarketRecommendDTO>getPage())
                                .convert(e -> e.convert(MarketRecommendDTO.class))
                );
        }

        @Resources(auth = AuthTypeEnum.AUTH)
        @ApiOperation("查询单个推荐返礼活动")
        @ApiImplicitParams({
                @ApiImplicitParam(name = "id", value = "活动ID", required = true, paramType = "path")
        })
        @GetMapping("/{id}")
        public ApiResponses<MarketRecommendDTO> get(@PathVariable("id") Integer id) {
                MarketRecommend marketRecommend = marketRecommendService.getById(id);
                MarketRecommendDTO marketRecommendDTO = marketRecommend.convert(MarketRecommendDTO.class);
                return success(marketRecommendDTO);
        }

        @Resources(auth = AuthTypeEnum.AUTH)
        @ApiOperation("查询推荐返礼商品价格")
        @ApiImplicitParams({
                @ApiImplicitParam(name = "id", value = "产品Id", required = true, paramType = "path")
        })
        @GetMapping("/productPrice/{id}")
        public ApiResponses<String> getProductPriceById(@PathVariable("id") Integer id) {
                String price = productPriceService.query()
                        .eq(ProductPrice::getPid,id)
                        .eq(ProductPrice::getSNum,1)
                        .getOne()
                        .getPrice()
                        .toString();
                return success(price);
        }

        @Resources(auth = AuthTypeEnum.AUTH)
        @ApiOperation("添加推荐返礼活动")
        @PostMapping
        public ApiResponses<Void> create(@RequestBody @Validated(MarketRecommendPARM.Create.class) MarketRecommendPARM marketRecommendPARM) {
                MarketRecommend marketRecommend = marketRecommendPARM.convert(MarketRecommend.class);
                int count1 = marketRecommendService.query().eq(MarketRecommend::getActivePid,marketRecommendPARM.getActivePid()).eq(MarketRecommend::getStatus,0).count();
                int count2 = marketRecommendService.query().eq(MarketRecommend::getActivePid,marketRecommendPARM.getSendPid()).eq(MarketRecommend::getStatus,0).count();
                int count3 = marketRecommendService.query().eq(MarketRecommend::getSendPid,marketRecommendPARM.getActivePid()).eq(MarketRecommend::getStatus,0).count();
                int count4 = marketRecommendService.query().eq(MarketRecommend::getSendPid,marketRecommendPARM.getSendPid()).eq(MarketRecommend::getStatus,0).count();
                ApiAssert.isFalse(ErrorCodeEnum.PRODUCT_ALREADY_EXISTS, count1 != 0);
                ApiAssert.isFalse(ErrorCodeEnum.PRODUCT_ALREADY_EXISTS, count2 != 0);
                ApiAssert.isFalse(ErrorCodeEnum.PRODUCT_ALREADY_EXISTS, count3 != 0);
                ApiAssert.isFalse(ErrorCodeEnum.PRODUCT_ALREADY_EXISTS, count4 != 0);
                marketRecommend.setStatus(StatusEnum.NORMAL);
                marketRecommendService.save(marketRecommend);
                return success(HttpStatus.CREATED);
        }

        @Resources(auth = AuthTypeEnum.AUTH)
        @ApiOperation(value = "删除推荐返礼活动")
        @ApiImplicitParams({
                @ApiImplicitParam(name = "id", value = "活动ID", required = true, paramType = "path")
        })

        @DeleteMapping("/{id}")
        public ApiResponses<Void> delete(@PathVariable("id") Integer id) {
                marketRecommendService.removeById(id);
                return success(HttpStatus.NO_CONTENT);
        }

        @Resources(auth = AuthTypeEnum.AUTH)
        @ApiOperation(value = "修改推荐返礼活动信息")
        @ApiImplicitParams({
                @ApiImplicitParam(name = "id", value = "活动ID", required = true, paramType = "path")
        })
        @PutMapping("/{id}")
        public ApiResponses<Void> update(@PathVariable("id") Integer id,@RequestBody @Validated(MarketRecommendPARM.Update.class) MarketRecommendPARM marketRecommendPARM) {
                MarketRecommend marketRecommend = marketRecommendPARM.convert(MarketRecommend.class);
                marketRecommend.setId(id);
                marketRecommendService.updateById(marketRecommend);
                return success();
        }

        @Resources(auth = AuthTypeEnum.AUTH)
        @ApiOperation("设置推荐返礼活动状态")
        @ApiImplicitParams({
                @ApiImplicitParam(name = "id", value = "活动ID", required = true, paramType = "path")
        })
        @PutMapping("/{id}/status")
        public ApiResponses<Void> updateStatus(@PathVariable("id") Integer id, @RequestBody @Validated(MarketRecommendPARM.Status.class) MarketRecommendPARM marketRecommendPARM) {
            MarketRecommend marketRecommend = marketRecommendService.getById(id);
            marketRecommend.setStatus(marketRecommendPARM.getStatus());
            marketRecommendService.updateById(marketRecommend);
            return success();
        }
}
