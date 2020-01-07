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
package org.crown.controller.module;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.crown.common.annotations.Resources;
import org.crown.enums.AuthTypeEnum;
import org.crown.framework.responses.ApiResponses;
import org.crown.model.module.dto.SortDTO;
import org.crown.model.module.entity.Sort;
import org.crown.model.module.parm.SortPARM;
import org.crown.service.module.ISortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;

import io.swagger.annotations.Api;
import org.crown.framework.controller.SuperController;

/**
 * <p>
 * 分类表 前端控制器
 * </p>
 *
 * @author ykMa
 */
@Api(tags = {"Sort"}, description = "分类表相关接口")
@RestController
@RequestMapping(value = "/sort", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class SortRestController extends SuperController {
    @Autowired
    private ISortService sortService;


    @Resources(auth = AuthTypeEnum.OPEN)
    @ApiOperation("分类查询所有")
    @ApiImplicitParam(name = "type", value = "分类类型，1 模板分类， 2 素材分类", paramType = "query")
    @GetMapping
    public ApiResponses<IPage<SortDTO>> page(@RequestParam(value = "type", required = false) String type) {
        IPage<SortDTO> convert = sortService.query().eq(StringUtils.isNotEmpty(type), Sort::getType, type).page(this.getPage()).convert(e -> e.convert(SortDTO.class));
        return success(convert);
    }


    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation("分类修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "分类id", required = true, paramType = "path")
    })
    @PutMapping("/{id}")
    public ApiResponses<Void> update(@PathVariable("id") Integer id,@RequestBody @Validated(SortPARM.Update.class) SortPARM sortPARM) {
        Sort sort = sortPARM.convert(Sort.class);
        sort.setId(id);
        sortService.updateById(sort);
        return success();
    }



    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation("分类新增")
    @ApiImplicitParam(name = "sortPARM", value = "分类", required = true, paramType = "body")
    @PostMapping
    public ApiResponses<Void> create(@RequestBody @Validated(SortPARM.Create.class) SortPARM sortPARM) {
        Sort sort = sortPARM.convert(Sort.class);
        sortService.save(sort);
        return success();
    }
    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation(value = "分类删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "分类id", required = true, paramType = "path")
    })
    @DeleteMapping("/{id}")
    public ApiResponses<Void> delete(@PathVariable("id") Integer id) {
        sortService.removeById(id);
        return success();
    }

}
