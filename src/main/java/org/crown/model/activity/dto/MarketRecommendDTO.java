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
package org.crown.model.activity.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Result;
import org.crown.enums.StatusEnum;
import org.crown.framework.model.BaseModel;
import org.crown.framework.model.convert.Convert;

import java.math.BigDecimal;


/**
 * <p>
 * 推荐返礼信息表
 * </p>
 *
 * @author whZhang
 */
@TableName("market_recommend")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MarketRecommendDTO extends Convert {

private static final long serialVersionUID=1L;

    @ApiModelProperty(notes = "id")
    private Integer id;
    @ApiModelProperty(notes = "会员等级Id")
    private Integer memberId;
    @ApiModelProperty(notes = "会员等级名称")
    private String memberName;
    @ApiModelProperty(notes = "参加活动商品id")
private Integer activePid;
    @ApiModelProperty(notes = "参加活动商品名称")
    private String activeName;
    @ApiModelProperty(notes = "参加活动商品价格")
private BigDecimal activeProductPrice;
    @ApiModelProperty(notes = "领取商品id")
private Integer sendPid;
    @ApiModelProperty(notes = "领取商品名称")
    private String sendName;
    @ApiModelProperty(notes = "领取商品价格")
private BigDecimal sendPrice;
    @ApiModelProperty(notes = "运费")
private BigDecimal carriagePrice;
    @ApiModelProperty(notes = "可领取人数")
private Integer peopleNum;
    @ApiModelProperty(notes = "领取商品后返现价格")
private BigDecimal payReturnMoney;
    @ApiModelProperty(notes = "购买指定商品后返现价格")
private BigDecimal buyReturnMoney;
    @ApiModelProperty(notes = "状态")
private StatusEnum status;

}
