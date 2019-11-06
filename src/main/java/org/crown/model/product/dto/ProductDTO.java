package org.crown.model.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.crown.framework.model.convert.Convert;

import java.math.BigDecimal;
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
public class ProductDTO extends Convert {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "id")
    private Integer id;
    @ApiModelProperty(notes = "品牌名称")
    private String name;
    @ApiModelProperty(notes = "产品价格")
    private BigDecimal price;
    @ApiModelProperty(notes = "运费")
    private BigDecimal carriage;
    @ApiModelProperty(notes = "品牌id")
    private String brandId;
    @ApiModelProperty(notes = "产品图片")
    private String productImgId;
    @ApiModelProperty(notes = "详情图片")
    private String detailImgId;
    @ApiModelProperty(notes = "模板id")
    private String modelId;
    @ApiModelProperty(notes = "状态")
    private Integer status;
}
