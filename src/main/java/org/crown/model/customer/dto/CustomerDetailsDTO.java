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
package org.crown.model.customer.dto;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.crown.framework.model.BaseModel;
import org.crown.model.label.dto.LabelBrandDTO;
import org.crown.model.label.entity.LabelBrand;

import java.time.LocalDateTime;
import java.util.List;


/**
 * <p>
 * 客户详情表
 * </p>
 *
 * @author whZhang
 */
@ApiModel
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CustomerDetailsDTO extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "微信名称")
    private String nickName;
    @ApiModelProperty(notes = "会员号")
    private String memberNum;
    @ApiModelProperty(notes = "会员等级")
    private String memberName;
    @ApiModelProperty(notes = "会员标签")
    private List<LabelBrandDTO> brands;
    @ApiModelProperty(notes = "真实姓名")
    private String name;
    @ApiModelProperty(notes = "手机号")
    private String phone;
    @ApiModelProperty(notes = "性别:{1：男，2：女，0：不确认}")
    private Integer sex;
    @ApiModelProperty(notes = "地址")
    private String address;
    @ApiModelProperty(notes = "注册时间")
    private LocalDateTime createTime;
    @ApiModelProperty(notes = "成交订单数")
    private Integer orderNum;
    @ApiModelProperty(notes = "成交金额")
    private Double sum;
    @ApiModelProperty(notes = "最后一次交易时间")
    private LocalDateTime lastTime;

}
