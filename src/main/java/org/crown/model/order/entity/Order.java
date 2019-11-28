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

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import org.crown.framework.model.BaseModel;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * <p>
 * 订单主表
 * </p>
 *
 * @author ykMa
 */
@TableName("`order`")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Order extends BaseModel {
    private static final long serialVersionUID = 1L;


    @ApiModelProperty(notes = "订单号")
    private String orderNum;
    @ApiModelProperty(notes = "订单类型：0普通订单，1分享返礼订单")
    private Integer orderType;
    @ApiModelProperty(notes = "总金额")
    private BigDecimal totalFee;
    @ApiModelProperty(notes = "实付金额")
    private BigDecimal actualFee;
    @ApiModelProperty(notes = "活动id")
    private Integer couponId;
    @ApiModelProperty(notes = "支付类型：1在线支付，2货到付款")
    private Integer paymentType;
    @ApiModelProperty(notes = "邮费")
    private BigDecimal postFee;
    @ApiModelProperty(notes = "客户id")
    private Integer customerId;
    @ApiModelProperty(notes = "商品id")
    private Integer productId;
    @ApiModelProperty(notes = "购买数量")
    private Integer num;
    @ApiModelProperty(notes = "商品标题")
    private String title;
    @ApiModelProperty(notes = "价格,单位：分")
    private BigDecimal price;
    @ApiModelProperty(notes = "商品图片")
    private String image;
    @ApiModelProperty(notes = "发票类型：（0无发票，1普通发票，2电子发票，3增值税发票）")
    private Integer invoiceType;
    @ApiModelProperty(notes = "订单状态：（ 1：未付款， 2：已付款，未发货， 3：已发货，未收货 4：已收货，未确认 5：确认收货，未评价 6：已评价）")
    private Integer status;
    @ApiModelProperty(notes = "下单时间")
    private LocalDateTime createTime;
    @ApiModelProperty(notes = "支付时间")
    private LocalDateTime payTime;
    @ApiModelProperty(notes = "发货时间")
    private LocalDateTime consignTime;
    @ApiModelProperty(notes = "订单结束时间")
    private LocalDateTime endTime;
    @ApiModelProperty(notes = "交易关闭时间")
    private LocalDateTime closeTime;
    @ApiModelProperty(notes = "评价时间")
    private LocalDateTime commentTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(notes = "更新时间")
    private LocalDateTime updateTime;

}
