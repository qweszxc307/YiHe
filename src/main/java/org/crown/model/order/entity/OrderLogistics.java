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
 *物流信息表
 * </p>
 *
 * @author ykMa
 */
@TableName("order_logistics")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OrderLogistics extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "订单id")
    private Long orderId;
    @ApiModelProperty(notes = "物流单号")
    private String logisticsNumber;
    @ApiModelProperty(notes = "物流公司名称")
    private String logisticsCompany;
    @ApiModelProperty(notes = "收件人")
    private String addressee;
    @ApiModelProperty(notes = "收件人手机号码")
    private String phone;
    @ApiModelProperty(notes = "省")
    private String province;
    @ApiModelProperty(notes = "市")
    private String city;
    @ApiModelProperty(notes = "区")
    private String district;
    @ApiModelProperty(notes = "街道")
    private String street;
    @ApiModelProperty(notes = "邮编")
    private Long postcode;
    @ApiModelProperty(notes = "创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty(notes = "更新时间")
    private LocalDateTime updateTime;

}
