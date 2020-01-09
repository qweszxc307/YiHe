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
package org.crown.model.module.parm;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.crown.framework.model.BaseModel;
import org.crown.model.module.dto.SortDTO;

import java.time.LocalDateTime;


/**
 * <p>
 * 素材表
 * </p>
 *
 * @author ykMa
 */
@TableName("material")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MaterialPARM extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "素材名称")
    private String name;
    @ApiModelProperty(notes = "分类DTO")
    private Integer sortId;
    @ApiModelProperty(notes = "素材图片地址")
    private String image;
    @ApiModelProperty(notes = "0正常，1删除")
    private Integer status;
    @ApiModelProperty(notes = "宽")
    private Integer width;
    @ApiModelProperty(notes = "高")
    private Integer high;



    public interface Create {

    }

    public interface Update {

    }
    public interface Status {

    }
}
