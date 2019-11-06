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

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
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

    @ApiModelProperty(notes = "品牌名称")
    private String name;
    @ApiModelProperty(notes = "产品价格")
    private BigDecimal price;
    @ApiModelProperty(notes = "运费")
    private BigDecimal carriage;
    @ApiModelProperty(notes = "品牌id")
    private String brandId;
    @ApiModelProperty(notes = "产品图片")
    private String productImgId;
    @ApiModelProperty(notes = "详情图片")
    private String detailImgId;
    @ApiModelProperty(notes = "模板id")
    private String modelId;
    @ApiModelProperty(notes = "状态")
    private Integer status;
    @ApiModelProperty(notes = "创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty(notes = "创建人ID")
    private String createUid;

}
