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
package org.crown.model.order.parm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.crown.common.cons.Regex;
import org.crown.framework.model.BaseModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
public class OrderPARM extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "收货人")
    @NotNull(groups = {Update.class}, message = "收货人不能为空")
    private String addressee;
    @ApiModelProperty(notes = "收货电话")
    @Pattern(groups = {Create.class, Update.class}, regexp = Regex.PHONE, message = "手机号码格式不正确")
    private String phone;
    @ApiModelProperty(notes = "省")
    @NotNull(groups = {Update.class},message = "地址人不能为空")
    private String province;
    @ApiModelProperty(notes = "市")
    @NotNull(groups = {Update.class},message = "地址人不能为空")
    private String city;
    @ApiModelProperty(notes = "区")
    @NotNull(groups = {Update.class},message = "地址人不能为空")
    private String district;
    @ApiModelProperty(notes = "街道")
    @NotNull(groups = {Update.class},message = "运费不能为空")
    private String street;




    public interface Create {

    }

    public interface Update {

    }

    public interface Status {

    }

}
