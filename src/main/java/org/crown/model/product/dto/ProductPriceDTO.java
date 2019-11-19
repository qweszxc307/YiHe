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
public class ProductPriceDTO extends Convert {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "起始区间值")
    private Integer sNum;
    @ApiModelProperty(notes = "结束区间值")
    private Integer eNum;
    @ApiModelProperty(notes = "价格")
    private BigDecimal price;
}
