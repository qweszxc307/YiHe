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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.crown.common.annotations.Resources;
import org.crown.enums.AuthTypeEnum;
import org.crown.framework.controller.SuperController;
import org.crown.framework.responses.ApiResponses;
import org.crown.model.module.dto.MaterialDTO;
import org.crown.model.module.dto.MaterialImgDTO;
import org.crown.model.module.dto.SortDTO;
import org.crown.model.module.entity.Material;
import org.crown.model.module.parm.MaterialPARM;
import org.crown.service.module.IMaterialService;
import org.crown.service.module.ISortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 素材表 前端控制器
 * </p>
 *
 * @author ykMa
 */
@Api(tags = {"Material"}, description = "素材表相关接口")
@RestController
@RequestMapping(value = "/material", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class MaterialRestController extends SuperController {

    @Autowired
    private IMaterialService materialService;
    @Autowired
    private ISortService sortService;

    @Resources(auth = AuthTypeEnum.OPEN)
    @ApiOperation("素材所有")
    @GetMapping
    public ApiResponses<IPage<MaterialDTO>> page() {
        IPage<MaterialDTO> materialDTO = materialService.query().eq(Material::getStatus, 0).page(this.getPage()).convert(e -> {
            MaterialDTO convert = e.convert(MaterialDTO.class);
            convert.setSortDTO(sortService.getById(e.getSortId()).convert(SortDTO.class));
            return convert;
        });
            return success(materialDTO);
    }

    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation("素材修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "分类id", required = true, paramType = "path")
    })
    @PutMapping("/{id}")
    public ApiResponses<Void> update(@PathVariable("id") Integer id, @RequestBody @Validated(MaterialPARM.Update.class) MaterialPARM materialPARM) {
        Material material = materialPARM.convert(Material.class);
        material.setId(id);
        materialService.updateById(material);
        return success();
    }


    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation("素材新增")
    @ApiImplicitParam(name = "sortPARM", value = "分类", required = true, paramType = "body")
    @PostMapping
    public ApiResponses<Void> create(@RequestBody @Validated(MaterialPARM.Create.class) MaterialPARM materialPARM) {
        Material material = materialPARM.convert(Material.class);
        materialService.save(material);
        return success();
    }


    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation(value = "素材删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "分类id", required = true, paramType = "path")
    })
    @DeleteMapping("/{id}")
    public ApiResponses<Void> delete(@PathVariable("id") Integer id) {
        Material material = materialService.getById(id);
        material.setStatus(1);
        materialService.updateById(material);
        return success();
    }
    @Resources(auth = AuthTypeEnum.OPEN)
    @ApiOperation("上传单个图片")
    @PostMapping(value = "/upload")
    public ApiResponses<MaterialImgDTO> create(HttpServletResponse httpServletResponse, MultipartFile file) {
        return materialService.uploadImg(httpServletResponse,file);
    }




}
