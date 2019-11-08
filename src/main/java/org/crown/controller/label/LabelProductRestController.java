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
package org.crown.controller.label;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.crown.common.annotations.Resources;
import org.crown.enums.AuthTypeEnum;
import org.crown.framework.controller.SuperController;
import org.crown.framework.responses.ApiResponses;
import org.crown.model.label.dto.LabelProductDTO;
import org.crown.model.label.entity.LabelProduct;
import org.crown.model.label.parm.LabelProductPARM;
import org.crown.service.label.ILabelProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 产品标签表 前端控制器
 * </p>
 *
 * @author ykMa
 */
@Api(tags = {"LabelProduct"}, description = "产品标签表相关接口")
@RestController
@RequestMapping(value = "/labelProduct", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class LabelProductRestController extends SuperController {

    @Autowired
    private ILabelProductService labelProductService;

    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation("查询所有产品标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "标签名称", paramType = "query")
    })
    @GetMapping
    public ApiResponses<IPage<LabelProductDTO>> page(@RequestParam(value = "name", required = false) String name) {
        return success(labelProductService.query()
                .likeRight(StringUtils.isNotEmpty(name), LabelProduct::getName, name)
                .page(this.<LabelProduct>getPage())
                .convert(e -> e.convert(LabelProductDTO.class))
        );
    }


    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation("设置标签状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path")
    })
    @PutMapping("/{id}/status")
    public ApiResponses<Void> updateStatus(@PathVariable("id") Integer id, @RequestBody @Validated(LabelProductPARM.Status.class) LabelProductPARM labelProductPARM) {

        labelProductService.updateStatus(id, labelProductPARM.getStatus());
        return success();
    }

    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation(value = "删除产品标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "标签ID", required = true, paramType = "path")
    })
    @DeleteMapping("/{id}")
    public ApiResponses<Void> delete(@PathVariable("id") Long id) {
        labelProductService.removeById(id);
        return success(HttpStatus.NO_CONTENT);
    }


    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation("修改产品标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "客户ID", required = true, paramType = "path")
    })
    @PutMapping("/{id}")
    public ApiResponses<Void> update(@PathVariable("id") Integer id, @RequestBody @Validated(LabelProductPARM.Update.class) LabelProductPARM labelProductPARM) {
        LabelProduct labelProduct = labelProductPARM.convert(LabelProduct.class);
        labelProduct.setId(id);
        labelProductService.updateById(labelProduct);
        return success();
    }



    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation("添加产品标签")
    @PostMapping()
    public ApiResponses<Void> create(@RequestBody @Validated(LabelProductPARM.Create.class) LabelProductPARM labelProductPARM) {
        labelProductService.save(labelProductPARM.convert(LabelProduct.class));
        return success(HttpStatus.CREATED);
    }
}
