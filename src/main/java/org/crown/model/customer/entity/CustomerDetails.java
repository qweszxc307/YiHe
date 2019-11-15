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
package org.crown.model.customer.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.crown.framework.model.BaseModel;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * <p>
 * 客户详情表
 * </p>
 *
 * @author whZhang
 */
@TableName("customer_details")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CustomerDetails extends BaseModel {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(notes = "id")
    private Integer id;
    @ApiModelProperty(notes = "微信名称")
    private String nickName;
    @ApiModelProperty(notes = "会员表id")
    private Integer cId;
    @ApiModelProperty(notes = "真实姓名")
    private String name;
    @ApiModelProperty(notes = "手机号")
    private String phone;
    @ApiModelProperty(notes = "性别")
    private Integer sex;
    @ApiModelProperty(notes = "地址")
    private String address;
    @ApiModelProperty(notes = "创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty(notes = "修改时间")
    private LocalDateTime updateTime;

}
