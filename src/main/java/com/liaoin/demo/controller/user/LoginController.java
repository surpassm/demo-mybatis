package com.liaoin.demo.controller.user;

import com.liaoin.demo.annotation.Login;
import com.liaoin.demo.common.Result;
import com.liaoin.demo.util.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("v1/in")
    @ApiOperation(value = "使用token获取用户基本信息")
    public Result loginIn(@ApiParam(value = "账号", required = true) @RequestParam String username,
                          @ApiParam(value = "密码", required = true) @RequestParam String password) {
		String token = JwtUtils.generateWithSub(1L + "");
		return ok(token);
    }

    @PostMapping("v1/auth/refreshToken")
    @ApiOperation(value = "刷新token时效")
    public Result refreshToken(@ApiParam(value = "刷新token") @RequestParam String refreshToken,
                               @ApiParam(value = "head 应用账号密码Basic64位加密") @RequestParam String head) {
        return ok();
    }
}
