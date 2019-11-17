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
package org.crown.model.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.crown.framework.model.BaseModel;

import java.time.LocalDateTime;


/**
 * <p>
 *
 * </p>
 *
 * @author ykMa
 */
@ApiModel
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OrderDTO extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "订单id")
    private Integer Id;
    @ApiModelProperty(notes = "订单号")
    private String orderNum;
    @ApiModelProperty(notes = "订单状态")
    private int status;
    @ApiModelProperty(notes = "收货人")
    private String name;
    @ApiModelProperty(notes = "收货电话")
    private String phone;
    @ApiModelProperty(notes = "地址")
    private String address;
    @ApiModelProperty(notes = "快递单号")
    private String logisticsNum;
    @ApiModelProperty(notes = "运费")
    private Double postFee;
    @ApiModelProperty(notes = "商品总价")
    private Double totalFee;
    @ApiModelProperty(notes = "下单时间")
    private LocalDateTime createTime;
    @ApiModelProperty(notes = "支付时间")
    private LocalDateTime payTime;

}
