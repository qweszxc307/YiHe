package org.crown.model.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.crown.framework.model.convert.Convert;

import java.math.BigDecimal;
import java.util.List;

/**
 * @version V1.0
 * @package org.crown.model.product.dto
 * @title:
 * @description:
 * @author: whZhang
 * @date: 2019/11/17 12:24
 */
@ApiModel
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CarriageConfigDTO extends Convert {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "id")
    private Integer id;
    @ApiModelProperty(notes = "cid")
    private Integer cid;
    @ApiModelProperty(notes = "运费策略所属模板名称")
    private String modelName;
    @ApiModelProperty(notes = "运费策略名称")
    private String name;
    @ApiModelProperty(notes = "满免价格")
    private BigDecimal freePrice;
    @ApiModelProperty(notes = "运费说明")
    private List<CarriageConfigPriceDTO> carrigeConfigPrices;

}
