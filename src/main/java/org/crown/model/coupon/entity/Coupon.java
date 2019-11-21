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
package org.crown.model.coupon.entity;

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
 *
 * </p>
 *
 * @author ykMa
 */
@TableName("coupon")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Coupon extends BaseModel {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(notes = "id")
    private Integer id;
    @ApiModelProperty(notes = "优惠券描述")
    private String details;
    @ApiModelProperty(notes = "优惠卷名称")
    private String name;
    @ApiModelProperty(notes = "类型{1：满减，2：直减，3折扣}")
    private Integer type;
    @ApiModelProperty(notes = "折扣条件")
    private BigDecimal discount;
    @ApiModelProperty(notes = "优惠价格")
    private BigDecimal discountPoint;
    @ApiModelProperty(notes = "开始时间")
    private LocalDateTime startTime;
    @ApiModelProperty(notes = "结束时间")
    private LocalDateTime endTime;
    @ApiModelProperty(notes = "状态{1：禁用，0：启用}")
    private Integer status;
    @ApiModelProperty(notes = "发放数量")
    private Integer total;
    @ApiModelProperty(notes = "已发数量")
    private Integer provide;
    @ApiModelProperty(notes = "已用数量")
    private Integer used;
    @ApiModelProperty(notes = "分发规则{1：主动分发，2：下单分发，3：用户领取，4：中奖分发}")
    private Integer rule;
    @ApiModelProperty(notes = "品牌id")
    private Integer brandId;
    /*
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


}
