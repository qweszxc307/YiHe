package org.crown.model.wx.parm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.crown.framework.model.convert.Convert;

import javax.validation.constraints.NotBlank;

/**
 * @version V1.0
 * @package org.crown.model.wx.parm
 * @title:
 * @description:
 * @author: whZhang
 * @date: 2019/11/14 13:48
 */
@ApiModel
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TokenPARM extends Convert {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "code")
    @NotBlank(groups = {TokenPARM.Create.class, TokenPARM.Update.class}, message = "code不能为空")
    private String code;
    public interface Create {

    }

    public interface Update {

    }

    public interface Status {

    }
}
