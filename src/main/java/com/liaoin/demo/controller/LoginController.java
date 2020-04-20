package com.liaoin.demo.controller;

import com.liaoin.demo.annotation.ResponseResult;
import com.liaoin.demo.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;

/**
 * @author mc
 * Create date 2020/2/10 11:04
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

	@ResponseResult
	@PostMapping("pc/v1/createSupperAdmin")
	@ApiOperation(value = "创建超级管理员[临时接口,正式环境删除]")
	public Object createSupperAdmin() {
		return userInfoService.createSupperAdmin();
	}

	@PostMapping("v1/small")
	@ApiOperation(value = "小程序登陆")
	public Object smallLogin(@ApiParam(value = "手机号码",required = true) @RequestParam(value = "mobile") @NotEmpty String mobile) {
		return userInfoService.smallLogin(mobile);
	}

	@PostMapping("v1/system")
	@ApiOperation(value = "pc平台登陆")
	public Object systemLogin(@ApiParam(value = "账号", required = true) @RequestParam(value = "username") @NotEmpty String username,
							  @ApiParam(value = "密码", required = true) @RequestParam(value = "password") @NotEmpty String password) {
		return userInfoService.login(username, password);
	}



}
