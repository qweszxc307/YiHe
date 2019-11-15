package org.crown.model.brand.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.crown.enums.StatusEnum;
import org.crown.framework.model.convert.Convert;

import java.time.LocalDateTime;

/**
 * @version V1.0
 * @package org.crown.model.image.dto
 * @title:
 * @description:
 * @author: ykMa
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
    @ApiModelProperty(notes = "产品图片")
    private String imgUrl;
    @ApiModelProperty(notes = "序号")
    private Integer orderNum;
    @ApiModelProperty(notes = "状态 0：禁用 1：正常")
    private StatusEnum status;
    @ApiModelProperty(notes = "产品图片Id")
    private Integer imageId;
}
