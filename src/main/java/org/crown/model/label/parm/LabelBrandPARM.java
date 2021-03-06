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
package org.crown.model.label.parm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.crown.framework.model.BaseModel;

import javax.validation.constraints.NotNull;


/**
 * <p>
 * 品牌标签表
 *
 * </p>
 *
 * @author ykMa
 */
@ApiModel
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class LabelBrandPARM extends BaseModel {

    private static final long serialVersionUID = 1L;

    @NotNull(groups = {LabelBrandPARM.Create.class, LabelBrandPARM.Update.class},message = "标签内容不能为空")
    @ApiModelProperty(notes = "标签内容")
    private String name;
    @NotNull(groups = {LabelBrandPARM.Create.class, LabelBrandPARM.Update.class, LabelBrandPARM.Status.class},message = "状态不能为空")
    @ApiModelProperty(notes = "（状态：0：开启，1：禁用）")
    private Integer status;
    public interface Create {

    }

    public interface Update {

    }

    public interface Status {

    }
}
