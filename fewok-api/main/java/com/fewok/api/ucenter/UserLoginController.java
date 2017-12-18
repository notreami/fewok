package com.fewok.api.ucenter;

import com.fewok.common.common.CommonOutput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author notreami on 17/11/13.
 */
@Slf4j
@RestController
@RequestMapping("/api/ucenter")
@Api(description = "用户注册或登陆")
public class UserLoginController {

    @ApiOperation(value = "")
    @PostMapping("/login")
    public CommonOutput login() {
        return CommonOutput.createSuccess(null);
    }
}
