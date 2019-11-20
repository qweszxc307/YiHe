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
package org.crown.model.activity.param;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.crown.enums.StatusEnum;
import org.crown.framework.model.BaseModel;
import org.crown.model.brand.parm.BrandPARM;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * <p>
 * 推荐返礼信息表
 * </p>
 *
 * @author whZhang
 */
@ApiModel
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MarketRecommendPARM extends BaseModel {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(notes = "会员等级")
    @NotNull(groups = {MarketRecommendPARM.Create.class,MarketRecommendPARM.Update.class}, message = "会员等级不能为空")
    @JsonProperty(value = "mId")
    private Integer mId;
    @ApiModelProperty(notes = "参加活动商品id")
    @NotNull(groups = {MarketRecommendPARM.Create.class,MarketRecommendPARM.Update.class}, message = "指定商品不能为空")
    @JsonProperty(value = "aPid")
    private Integer aPid;
    @ApiModelProperty(notes = "参加活动商品价格")
    @NotNull(groups = {MarketRecommendPARM.Create.class,MarketRecommendPARM.Update.class}, message = "商品价格不能为空")
    @JsonProperty(value = "pPrice")
    private BigDecimal pPrice;
    @ApiModelProperty(notes = "领取商品id")
    @NotNull(groups = {MarketRecommendPARM.Create.class,MarketRecommendPARM.Update.class}, message = "领取商品不能为空")
    @JsonProperty(value = "sPid")
    private Integer sPid;
    @ApiModelProperty(notes = "领取商品价格")
    @NotNull(groups = {MarketRecommendPARM.Create.class,MarketRecommendPARM.Update.class}, message = "领取商品价格不能为空")
    @JsonProperty(value = "sPrice")
    private BigDecimal sPrice;
    @ApiModelProperty(notes = "运费")
    @NotNull(groups = {MarketRecommendPARM.Create.class,MarketRecommendPARM.Update.class}, message = "运费不能为空")
    private BigDecimal carriagePrice;
    @ApiModelProperty(notes = "可领取人数")
    @NotNull(groups = {MarketRecommendPARM.Create.class,MarketRecommendPARM.Update.class}, message = "领取人数不能为空")
    private String peopleNum;
    @ApiModelProperty(notes = "领取商品后返现价格")
    @NotNull(groups = {MarketRecommendPARM.Create.class,MarketRecommendPARM.Update.class}, message = "支付成功返现价格不能为空")
    private String payReturnMoney;
    @ApiModelProperty(notes = "购买指定商品后返现价格")
    @NotNull(groups = {MarketRecommendPARM.Create.class,MarketRecommendPARM.Update.class}, message = "购买指定商品返现价格不能为空")
    private String buyReturnMoney;
    @ApiModelProperty(notes = "状态:0：禁用 1：正常")
    private StatusEnum status;

    public interface Create {

    }

    public interface Update {

    }
    public interface Status {

    }


}
