package org.crown.model.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.crown.framework.model.BaseModel;

import java.time.LocalDateTime;

@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OrderUploadDTO extends BaseModel {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(notes = "订单号")
    private String orderNum;
    @ApiModelProperty(notes = "物流单号")
    private String LogisticsNumber;
}
