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
public class CarriageConfigPriceDTO extends Convert {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "configId")
    private Integer configId;
    @ApiModelProperty(notes = "价格区间最小数量")
    private Integer sNum;
    @ApiModelProperty(notes = "价格区间最大数量")
    private Integer eNum;
    @ApiModelProperty(notes = "运费价格")
    private BigDecimal price;

}
