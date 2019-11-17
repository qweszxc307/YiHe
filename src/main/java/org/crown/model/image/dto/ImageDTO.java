package org.crown.model.image.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.crown.enums.StatusEnum;
import org.crown.framework.model.convert.Convert;

import java.time.LocalDateTime;
import java.util.List;

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
public class ImageDTO extends Convert {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "id")
    private Integer id;
    @ApiModelProperty(notes = "图片url")
    private String imgUrl;
}
