package com.liaoin.demo.controller.auth;

import com.liaoin.demo.annotation.Login;
import com.liaoin.demo.entity.Operations;
import com.liaoin.demo.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author mc
 * Create date 2020/2/12 11:57
 * Version 1.0
 * Description
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/interfaceAuth/")
@Api(tags  =  "接口权限验证Api")
public class InterfaceAuthController {

	private AntPathMatcher antPathMatcher = new AntPathMatcher();
	@Resource
	private UserInfoService userInfoService;

	@Login
	@PostMapping("v1/getRequestURI")
	@ApiOperation(value = "修改")
	public Object getRequestURI(@ApiParam(hidden = true)@Login Long userId,
						 @ApiParam(value = "请求路径",required = true)@RequestParam(value = "requestURI") String requestURI) {
        boolean flag = false;
        if (!requestURI.equals("/favicon.ico")) {
            log.info("请求验证的URL" + requestURI);
            //获取用户接口信息
			List<Operations> operations = userInfoService.selectUserOperations(userId);
//            for (UserMenu menu : menuList) {
//                if (menu.getMenuUrl() != null && antPathMatcher.match(menu.getMenuUrl(), requestURI)) {
//                    flag = true;
//                    break;
//                }
//            }
        }
        return flag;
	}

}
