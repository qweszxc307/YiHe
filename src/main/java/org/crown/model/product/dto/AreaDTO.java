package org.crown.model.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.crown.framework.model.convert.Convert;

import java.util.List;

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
public class AreaDTO  extends Convert {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "id")
    private Integer id;
    @ApiModelProperty(notes = "产品运费策略名称")
    private String name;
    @ApiModelProperty(notes = "序号")
    private List<CityDTO> citys;

}
