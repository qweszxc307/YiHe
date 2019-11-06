package org.crown.controller.wx;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.crown.common.annotations.Resources;
import org.crown.enums.AuthTypeEnum;
import org.crown.framework.controller.SuperController;
import org.crown.framework.responses.ApiResponses;
import org.crown.model.image.dto.ImageDTO;
import org.crown.model.image.entity.Image;
import org.crown.model.wx.dto.AssembleDTO;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version V1.0
 * @package org.crown.controller.wx
 * @title:
 * @description:
 * @author: whZhang
 * @date: 2019/11/4 10:35
 */
@Api(tags = {"Assemble"}, description = "微信拼团相关接口")
@RestController
@RequestMapping(value = "/assemble", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class AssembleController  extends SuperController {
/*    @Resources(auth = AuthTypeEnum.OPEN)
    @ApiOperation("拼团列表")
    @GetMapping()
    public ApiResponses<AssembleDTO> get() {
        AssembleDTO assembleDTO = image.convert(AssembleDTO.class);
        return success(assembleDTO);
    }*/
}
