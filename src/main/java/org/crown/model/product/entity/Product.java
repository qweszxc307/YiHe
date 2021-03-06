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
package org.crown.model.product.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import org.crown.enums.StatusEnum;
import org.crown.framework.model.BaseModel;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * <p>
 * 产品表
 * </p>
 *
 * @author whZhang
 */
@TableName("product")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Product extends BaseModel {

private static final long serialVersionUID=1L;

    @ApiModelProperty(notes = "产品名称")
private String name;
    @ApiModelProperty(notes = "品牌id")
private String brandId;
    @ApiModelProperty(notes = "模板id")
private String modelId;
    @ApiModelProperty(notes = "状态")
private StatusEnum status;
    @ApiModelProperty(notes = "序号")
    private Integer orderNum;
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
