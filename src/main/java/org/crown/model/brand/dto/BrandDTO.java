package org.crown.model.brand.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.crown.framework.model.convert.Convert;

import java.time.LocalDateTime;

/**
 * @version V1.0
 * @package org.crown.model.image.dto
 * @title:
 * @description:
 * @author: whZhang
 * @date: 2019/10/31 15:59
 */
@ApiModel
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BrandDTO extends Convert {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "id")
    private Integer id;
    @ApiModelProperty(notes = "品牌名")
    private String name;
    @ApiModelProperty(notes = "图片id")
    private String imageId;
    @ApiModelProperty(notes = "状态")
    private Integer status;
    @ApiModelProperty(notes = "创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty(notes = "创建人")
    private String createUid;
    @ApiModelProperty(notes = "修改时间")
    private LocalDateTime updateTime;
    @ApiModelProperty(notes = "修改人")
    private String updateUid;
    @ApiModelProperty(notes = "序号")
    private Integer orderNum;
}
