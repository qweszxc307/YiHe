package org.crown.model.member.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @version V1.0
 * @package org.crown.model.brand.dto
 * @title:
 * @description:
 * @author:  ykMa
 */
@ApiModel
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MemberImgDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "id")
    private Integer imgId;
    @ApiModelProperty(notes = "url")
    private String imgUrl;
}
