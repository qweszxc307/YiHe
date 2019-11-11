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
package org.crown.model.brand.parm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.crown.enums.StatusEnum;
import org.crown.framework.model.convert.Convert;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 轮播图PARM
 * </p>
 *
 * @author whZhang
 */
@ApiModel
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BrandPARM extends Convert {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "序号")
    @NotBlank(groups = {BrandPARM.Create.class, BrandPARM.Update.class}, message = "序号不能为空")
    private String orderNum;
    @ApiModelProperty(notes = "品牌名")
    @NotBlank(groups = {BrandPARM.Create.class, BrandPARM.Update.class}, message = "品牌名称不能为空")
    private String name;
    @ApiModelProperty(notes = "图片ID")
    @NotBlank(groups = {BrandPARM.Create.class, BrandPARM.Update.class}, message = "品牌图片不能为空")
    private String imageId;
    @ApiModelProperty(notes = "状态")
    private StatusEnum status;


    public interface Create {

    }

    public interface Update {

    }
    public interface Status {

    }

}
