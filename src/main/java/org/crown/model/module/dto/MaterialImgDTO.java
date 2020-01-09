package org.crown.model.module.dto;
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
public class MaterialImgDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "素材图片地址")
    private String image;
    @ApiModelProperty(notes = "宽")
    private Integer width;
    @ApiModelProperty(notes = "高")
    private Integer high;
}
