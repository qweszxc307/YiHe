package org.crown.model.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.crown.framework.model.convert.Convert;

import java.math.BigDecimal;
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
public class ProductDTO extends Convert {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "id")
    private Integer id;
    @ApiModelProperty(notes = "产品名称")
    private String name;
    @ApiModelProperty(notes = "品牌id")
    private Integer brandId;
    @ApiModelProperty(notes = "模板id")
    private Integer modelId;
    @ApiModelProperty(notes = "运费策略模板id")
    private Integer carriageId;
    @ApiModelProperty(notes = "库存")
    private Integer stock;
    @ApiModelProperty(notes = "产品相关图片")
    private List<ProductImgDTO> productImgList;
    @ApiModelProperty(notes = "产品相关图片")
    private List<ProductPriceDTO> productPriceList;
    @ApiModelProperty(notes = "状态")
    private Integer status;
    @ApiModelProperty(notes = "序号")
    private Integer orderNum;
}
