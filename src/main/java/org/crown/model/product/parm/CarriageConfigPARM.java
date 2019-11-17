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
package org.crown.model.product.parm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.crown.framework.model.BaseModel;

import javax.validation.constraints.NotEmpty;
import java.util.List;


/**
 * <p>
 * 产品运费策略PARM
 * </p>
 *
 * @author whZhang
 */
@ApiModel
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CarriageConfigPARM extends BaseModel {

private static final long serialVersionUID=1L;

    @ApiModelProperty(notes = "运费策略Id")
    @NotEmpty(groups = {ProductPARM.Create.class, ProductPARM.Update.class}, message = "关联运费策略信息失败，请重试")
    private Integer id;

    @ApiModelProperty(notes = "策略名称")
    @NotEmpty(groups = {ProductPARM.Create.class, ProductPARM.Update.class}, message = "请填写策略名称")
    private String carriageConfigName;

    @ApiModelProperty(notes = "满免价格")
    @NotEmpty(groups = {ProductPARM.Create.class, ProductPARM.Update.class}, message = "请填写满免价格")
    private String carriageConfigFreePrice;

    @ApiModelProperty(notes = "起始数量")
    @NotEmpty(groups = {ProductPARM.Create.class, ProductPARM.Update.class}, message = "请填写起始数量")
    private List<String> bnums;

    @ApiModelProperty(notes = "最大数量")
    @NotEmpty(groups = {ProductPARM.Create.class, ProductPARM.Update.class}, message = "请填写最大数量")
    private List<String> onums;

    @ApiModelProperty(notes = "区间价格")
    @NotEmpty(groups = {ProductPARM.Create.class, ProductPARM.Update.class}, message = "请填写区间价格")
    private List<String> price;

    @ApiModelProperty(notes = "区域id")
    @NotEmpty(groups = {ProductPARM.Create.class, ProductPARM.Update.class}, message = "请填选择适用地区")
    private List<String> cityIds;

    public interface Create {

    }

    public interface Update {

    }

    public interface Status {

    }

}
