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
package org.crown.model.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.crown.framework.model.BaseModel;

import java.time.LocalDateTime;


/**
 * <p>
 * 订单详情表
 * </p>
 *
 * @author ykMa
 */
@TableName("order_detail")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OrderDetail extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "订单id")
    private Long orderId;
    @ApiModelProperty(notes = "sku商品id")
    private Long skuId;
    @ApiModelProperty(notes = "购买数量")
    private Integer num;
    @ApiModelProperty(notes = "商品标题")
    private String title;
    @ApiModelProperty(notes = "商品动态属性键值集")
    private String ownSpec;
    @ApiModelProperty(notes = "价格,单位：分")
    private Double price;
    @ApiModelProperty(notes = "商品图片")
    private String image;
    @ApiModelProperty(notes = "创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty(notes = "更新时间")
    private LocalDateTime updateTime;

}
