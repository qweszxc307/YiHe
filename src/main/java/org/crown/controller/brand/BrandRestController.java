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
package org.crown.controller.brand;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.crown.common.annotations.Resources;
import org.crown.enums.AuthTypeEnum;
import org.crown.framework.responses.ApiResponses;
import org.crown.model.brand.dto.BrandDTO;
import org.crown.model.brand.entity.Brand;
import org.crown.model.product.dto.ProductDTO;
import org.crown.model.product.entity.Product;
import org.crown.service.brand.IBrandService;
import org.crown.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;

import io.swagger.annotations.Api;
import org.crown.framework.controller.SuperController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 品牌表 前端控制器
 * </p>
 *
 * @author whZhang
 */
@Api(tags = {"Brand"}, description = "品牌表相关接口")
@RestController
@RequestMapping(value = "/brand", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class BrandRestController extends SuperController {
        @Autowired
        private IBrandService brandService;

        @Resources(auth = AuthTypeEnum.AUTH)
        @ApiOperation("查询所有品牌")
        @GetMapping
        public ApiResponses<IPage<BrandDTO>> page() {
                return success(
                        brandService.page(this.<Brand>getPage())
                                .convert(e -> e.convert(BrandDTO.class))
                );
        }

        @Resources(auth = AuthTypeEnum.AUTH)
        @ApiOperation("查询单个品牌")
        @ApiImplicitParams({
                @ApiImplicitParam(name = "id", value = "品牌ID", required = true, paramType = "path")
        })
        @GetMapping("/{id}")
        public ApiResponses<BrandDTO> get(@PathVariable("id") Integer id) {
                Brand brand = brandService.getById(id);
                BrandDTO brandDTO = brand.convert(BrandDTO.class);
                return success(brandDTO);
        }

        @Resources(auth = AuthTypeEnum.AUTH)
        @ApiOperation("添加品牌")
        @PostMapping
        public ApiResponses<Void> create() {
                return success(HttpStatus.CREATED);
        }

        @Resources(auth = AuthTypeEnum.AUTH)
        @ApiOperation(value = "删除品牌")
        @ApiImplicitParams({
                @ApiImplicitParam(name = "id", value = "品牌ID", required = true, paramType = "path")
        })

        @DeleteMapping("/{id}")
        public ApiResponses<Void> delete(@PathVariable("id") Integer id) {
                return success(HttpStatus.NO_CONTENT);
        }

        @Resources(auth = AuthTypeEnum.AUTH)
        @ApiOperation(value = "修改品牌")
        @ApiImplicitParams({
                @ApiImplicitParam(name = "id", value = "产品ID", required = true, paramType = "path")
        })

        @PutMapping("/{id}")
        public ApiResponses<Void> update(@PathVariable("id") Integer id) {
                return success();
        }

        @Resources(auth = AuthTypeEnum.AUTH)
        @ApiOperation("添加品牌图片")
        @PostMapping(value = "/upload")
        public ApiResponses<Void> create(MultipartFile file) {
                return success(HttpStatus.CREATED);
        }
}