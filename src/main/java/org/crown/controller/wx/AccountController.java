package org.crown.controller.wx;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.crown.common.annotations.Resources;
import org.crown.common.utils.JWTUtils;
import org.crown.enums.AuthTypeEnum;
import org.crown.framework.controller.SuperController;
import org.crown.framework.responses.ApiResponses;
import org.crown.model.system.dto.TokenDTO;
import org.crown.model.system.parm.LoginPARM;
import org.crown.model.wx.parm.TokenPARM;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @version V1.0
 * @package org.crown.controller.wx
 * @title:
 * @description:
 * @author: whZhang
 * @date: 2019/11/4 10:35
 */
@Api(tags = {"WxAccount"}, description = "微信账号相关接口")
@RestController
@RequestMapping(value = "/wx", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class AccountController extends SuperController {
    @Resources(auth = AuthTypeEnum.OPEN)
    @ApiOperation("获取Token")
    @PostMapping(value = "/token")
    public ApiResponses<String> getToken(@RequestBody @Validated TokenPARM tokenPARM) {
        String token = JWTUtils.generate(tokenPARM.getCode());
        return success(token);
    }
}
