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
import org.crown.enums.StatusEnum;
import org.crown.framework.model.convert.Convert;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Array;
import java.util.List;

/**
 * <p>
 * 产品PARM
 * </p>
 *
 * @author whZhang
 */
@ApiModel
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProductPARM extends Convert {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "序号")
    @NotBlank(groups = {ProductPARM.Create.class, ProductPARM.Update.class}, message = "序号不能为空")
    private String orderNum;

    @ApiModelProperty(notes = "产品名称")
    @NotBlank(groups = {ProductPARM.Create.class, ProductPARM.Update.class}, message = "产品名称不能为空")
    private String name;

    @ApiModelProperty(notes = "品牌ID")
    @NotBlank(groups = {ProductPARM.Create.class, ProductPARM.Update.class}, message = "品牌不能为空")
    private String brandId;

    @ApiModelProperty(notes = "运费策略ID")
    @NotBlank(groups = {ProductPARM.Create.class, ProductPARM.Update.class}, message = "运费策略不能为空")
    private String carriageId;

    @ApiModelProperty(notes = "产品图片")
    @NotEmpty(groups = {ProductPARM.Create.class, ProductPARM.Update.class}, message = "请先上传产品图片")
    private List<Integer> productImgs;

    @ApiModelProperty(notes = "产品详情图片")
    @NotBlank(groups = {ProductPARM.Create.class, ProductPARM.Update.class}, message = "请先上传产品详情图片")
    private String detailImgId;

    @ApiModelProperty(notes = "起始数量")
    @NotEmpty(groups = {ProductPARM.Create.class, ProductPARM.Update.class}, message = "请填写起始数量")
    private List<String> bnums;

    @ApiModelProperty(notes = "最大数量")
    @NotEmpty(groups = {ProductPARM.Create.class, ProductPARM.Update.class}, message = "请填写最大数量")
    private List<String> onums;

    @ApiModelProperty(notes = "区间价格")
    @NotEmpty(groups = {ProductPARM.Create.class, ProductPARM.Update.class}, message = "请填写区间价格")
    private List<String> price;

    @ApiModelProperty(notes = "状态:0：禁用 1：正常")
    private StatusEnum status;

    public interface Create {

    }

    public interface Update {

    }

    public interface Status {

    }

}
