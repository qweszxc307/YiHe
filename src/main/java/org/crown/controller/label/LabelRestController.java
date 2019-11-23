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
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.crown.common.annotations.Resources;
import org.crown.enums.AuthTypeEnum;
import org.crown.framework.responses.ApiResponses;
import org.crown.model.label.dto.LabelBrandDTO;
import org.crown.model.label.dto.LabelDTO;
import org.crown.model.label.entity.Label;
import org.crown.model.label.parm.LabelPARM;
import org.crown.service.label.ILabelBrandService;
import org.crown.service.label.ILabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;

import io.swagger.annotations.Api;
import org.crown.framework.controller.SuperController;

import java.util.Objects;

/**
 * <p>
 * 标签表 前端控制器
 * </p>
 *
 * @author ykMa
 */
@Api(tags = {"Label"}, description = "标签表相关接口")
@RestController
@RequestMapping(value = "/label", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class LabelRestController extends SuperController {
    @Autowired
    private ILabelService labelService;
    @Autowired
    private ILabelBrandService labelBrandService;

    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation("查询所有标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "标签名称", paramType = "query"),
            @ApiImplicitParam(name = "brandId", value = "标签id", paramType = "query")
    })
    @GetMapping
    public ApiResponses<IPage<LabelDTO>> page(@RequestParam(value = "name", required = false) String name,
                                              @RequestParam(value = "brandId", required = false) Integer brandId) {
        return success(labelService.query()
                .eq(StringUtils.isNotEmpty(name), Label::getName, name)
                .eq(Objects.nonNull(brandId), Label::getBrandId, brandId)
                .page(this.getPage()).convert(e -> {
                            LabelDTO convert = e.convert(LabelDTO.class);
                            convert.setBrand(labelBrandService.getById(e.getBrandId()).convert(LabelBrandDTO.class));
                            return convert;
                        }
                ));
    }

    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation("修改标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path"),
    })
    @PutMapping("/{id}")
    public ApiResponses<Void> update(@PathVariable("id") Integer id, @RequestBody @Validated(LabelPARM.Update.class) LabelPARM labelPARM) {
        Label convert = labelPARM.convert(Label.class);
        labelService.updateById(convert);
        return success();
    }

    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation("删除标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path"),
    })
    @DeleteMapping("/{id}")
    public ApiResponses<Void> delete(@PathVariable("id") Integer id) {
        labelService.removeLabelById(id);
        return success();
    }

    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation("新增标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path"),
    })
    @PostMapping
    public ApiResponses<Void> create(@RequestBody @Validated(LabelPARM.Update.class) LabelPARM labelPARM) {
        labelService.save(labelPARM.convert(Label.class));
        return success();
    }

    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation("设置标签状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path")
    })
    @PutMapping("/{id}/status")
    public ApiResponses<Void> updateStatus(@PathVariable("id") Integer id, @RequestBody Integer status) {
        Label label = new Label();
        label.setId(id);
        label.setStatus(status);
        labelService.updateById(label);
        return success();
    }
}
