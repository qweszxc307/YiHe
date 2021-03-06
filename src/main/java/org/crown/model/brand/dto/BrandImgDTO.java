package org.crown.model.brand.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.crown.framework.model.convert.Convert;

/**
 * @version V1.0
 * @package org.crown.model.brand.dto
 * @title:
 * @description:
 * @author: whZhang
 * @date: 2019/11/7 15:39
 */
@ApiModel
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BrandImgDTO extends Convert {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "id")
    private Integer imgId;
    @ApiModelProperty(notes = "url")
    private String imgUrl;
}
