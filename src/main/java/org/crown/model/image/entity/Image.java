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
package org.crown.model.image.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import org.crown.enums.ImagesEnum;
import org.crown.framework.model.BaseModel;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * <p>
 * 存储所有图片信息
 * </p>
 *
 * @author whZhang
 */
@TableName("image")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Image extends BaseModel {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(notes = "图片url")
    private String imgUrl;
    @ApiModelProperty(notes = "图片类型(0:轮播图;1:品牌图片;2:产品图片;3:产品详情图片")
    private ImagesEnum type;
    @ApiModelProperty(notes = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @ApiModelProperty(notes = "创建人")
    @TableField(fill = FieldFill.INSERT)
    private Integer createUid;
    @ApiModelProperty(notes = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @ApiModelProperty(notes = "修改人")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updateUid;

}
