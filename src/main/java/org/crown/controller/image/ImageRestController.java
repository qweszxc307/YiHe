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
package org.crown.controller.image;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.Md5Crypt;
import org.crown.common.annotations.Resources;
import org.crown.enums.AuthTypeEnum;
import org.crown.enums.ImagesEnum;
import org.crown.enums.StatusEnum;
import org.crown.framework.controller.SuperController;
import org.crown.framework.enums.ErrorCodeEnum;
import org.crown.framework.responses.ApiResponses;
import org.crown.framework.utils.ApiAssert;
import org.crown.model.image.dto.ImageDTO;
import org.crown.model.image.entity.Image;
import org.crown.model.image.parm.ImagePARM;
import org.crown.model.system.dto.UserDTO;
import org.crown.model.system.entity.User;
import org.crown.model.system.parm.UserPARM;
import org.crown.service.image.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 存储所有图片信息 前端控制器
 * </p>
 *
 * @author whZhang
 */
@Api(tags = {"Image"}, description = "存储所有图片信息相关接口")
@RestController
@RequestMapping(value = "/image", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class ImageRestController extends SuperController {
    @Autowired
    private IImageService imageService;

    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation("查询所有图片")
    @GetMapping
    public ApiResponses<IPage<ImageDTO>> page() {
        return success(
                imageService.page(this.<Image>getPage())
                        .convert(e -> e.convert(ImageDTO.class))
        );
    }

    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation("查询单个图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "图片ID", required = true, paramType = "path")
    })
    @GetMapping("/{id}")
    public ApiResponses<ImageDTO> get(@PathVariable("id") Integer id) {
        Image image = imageService.getById(id);
        ImageDTO imageDTO = image.convert(ImageDTO.class);
        return success(imageDTO);
    }

    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation("上传单个图片")
    @PostMapping(value = "/upload")
    public ApiResponses<ImageDTO> create(HttpServletResponse httpServletResponse, MultipartFile file) {
        return imageService.uploadImg(httpServletResponse,file, ImagesEnum.CAROUSEL_IMAGE);
    }

    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation(value = "删除图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "图片ID", required = true, paramType = "path")
    })
    @DeleteMapping("/{id}")
    public ApiResponses<Void> delete(@PathVariable("id") Integer id) {
        return success(HttpStatus.NO_CONTENT);
    }

    @Resources(auth = AuthTypeEnum.AUTH)
    @ApiOperation(value = "修改图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "图片ID", required = true, paramType = "path")
    })
    @PutMapping("/{id}")
    public ApiResponses<Void> update(@PathVariable("id") Integer id) {
        return success();
    }
}
