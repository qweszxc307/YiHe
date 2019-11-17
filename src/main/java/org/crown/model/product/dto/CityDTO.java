package org.crown.model.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.crown.framework.model.convert.Convert;

/**
 * @version V1.0
 * @package org.crown.model.product.dto
 * @title:
 * @description:
 * @author: whZhang
 * @date: 2019/11/17 17:45
 */
@ApiModel
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CityDTO  extends Convert {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "id")
    private String value;
    @ApiModelProperty(notes = "地名")
    private String title;
}
