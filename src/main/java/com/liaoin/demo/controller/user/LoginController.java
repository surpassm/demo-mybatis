package com.liaoin.demo.controller.user;

import com.liaoin.demo.annotation.Login;
import com.liaoin.demo.common.Result;
import com.liaoin.demo.service.user.UserInfoService;
import com.liaoin.demo.util.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;

import static com.liaoin.demo.common.Result.fail;
import static com.liaoin.demo.common.Result.ok;


/**
 * @author mc
 * Create date 2019/2/16 14:44
 * Version 1.0
 * Description
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/login/")
@Api(tags = "1、登录")
public class LoginController {

    @Resource
    private UserInfoService userInfoService;

    @PostMapping("v1/createAdmin")
    @ApiOperation(value = "创建超级管理员", position = 0)
    public Result createAdmin() {
        return userInfoService.createAdmin();
    }

    @PostMapping("v1/in")
    @ApiOperation(value = "登录获取token", position = 1)
    public Result loginIn(@ApiParam(value = "登录时获取的 code", required = true) @RequestParam @NotEmpty String code) {
        return userInfoService.loginIn(code);
    }

}
