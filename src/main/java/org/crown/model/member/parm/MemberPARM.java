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
package org.crown.model.member.parm;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.crown.framework.model.BaseModel;
import org.crown.model.customer.parm.CustomerPARM;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


/**
 * <p>
 * 会员等级表
 * </p>
 *
 * @author ykMa
 */
@TableName("member")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MemberPARM extends BaseModel {

    private static final long serialVersionUID = 1L;
    @NotNull(groups = MemberPARM.Status.class, message = "等级名称不能为空")
    @ApiModelProperty(notes = "等级名称")
    private String name;
    @NotNull(groups = MemberPARM.Status.class, message = "升级条件不可以为空")
    @ApiModelProperty(notes = "升级条件")
    private String upgrade;
    @NotNull(groups = MemberPARM.Status.class, message = "会员等级不能为空")
    @ApiModelProperty(notes = "会员等级：1-X ")
    private Integer level;
    @ApiModelProperty(notes = "会员卡背景图")
    private String backImage;
    @ApiModelProperty(notes = "会员特权")
    private String special;

    public interface Create {
    }

    public interface Update {

    }

    public interface Status {

    }
}
