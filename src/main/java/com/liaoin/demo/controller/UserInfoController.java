package com.liaoin.demo.controller;

import com.liaoin.demo.annotation.Login;
import com.liaoin.demo.common.ResultCode;
import com.liaoin.demo.domain.UserInfoVO;
import com.liaoin.demo.entity.UserInfo;
import com.liaoin.demo.exception.CustomException;
import com.liaoin.demo.service.UserInfoService;
import com.liaoin.demo.util.ValidateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Optional;

import static com.liaoin.demo.common.Result.ok;


/**
  * @author mc
  * Create date 2020-02-10 10:15:21
  * Version 1.0
  * Description 用户控制层
  */
@CrossOrigin
@RestController
@RequestMapping("/userInfo/")
@Api(tags  =  "用户Api")
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;


    @Login
    @PostMapping("v1/insert")
    @ApiOperation(value = "新增")
    public Object insert(@ApiParam(hidden = true)@Login Long userId,
						 @Valid @RequestBody UserInfoVO vo, BindingResult errors) {
		ValidateUtil.check(errors);
        return userInfoService.insertOrUpdate(vo);
    }

    @Login
    @PostMapping("v1/update")
    @ApiOperation(value = "修改")
    public Object update(@ApiParam(hidden = true)@Login Long userId,
						 @Valid @RequestBody UserInfoVO vo, BindingResult errors) {
		ValidateUtil.check(errors);
        return userInfoService.insertOrUpdate(vo);
    }

    @Login
    @PostMapping("v1/getById")
    @ApiOperation(value = "根据主键删除")
    public Object deleteGetById(@ApiParam(hidden = true)@Login Long userId,
                                @ApiParam(value = "主键",required = true)@RequestParam(value = "id")@NotNull @Min(1) Long id) {
        userInfoService.deleteById(id);
        return ok();
    }

    @Login
    @PostMapping("v1/findById")
    @ApiOperation(value = "根据主键查询")
    public Object findById(@ApiParam(hidden = true)@Login Long userId,
                           @ApiParam(value = "主键",required = true)@RequestParam(value = "id") @NotNull @Min(1) Long id) {
		Optional<UserInfo> optionalUserInfo = userInfoService.findById(id);
		if (!optionalUserInfo.isPresent()){
			throw new CustomException(ResultCode.RESULT_DATA_NONE.getCode(),ResultCode.RESULT_DATA_NONE.getMsg());
		}
		return optionalUserInfo.get();
    }

	@Login
	@PostMapping("v1/addUserGroup")
	@ApiOperation(value = "添加用户组")
	public Object addUserGroup(@ApiParam(hidden = true)@Login Long userId,
							   @ApiParam(value = "用户主键",required = true)@RequestParam(value = "userInfoId") @NotNull @Min(1) Long userInfoId,
							   @ApiParam(value = "组主键",required = true)@RequestParam(value = "groupId") @NotNull @Min(1) Long groupId) {
		userInfoService.addUserGroup(userInfoId,groupId);
		return ok();
	}
	@Login
	@PostMapping("v1/deleteUserGroup")
	@ApiOperation(value = "删除用户组")
	public Object deleteUserGroup(@ApiParam(hidden = true)@Login Long userId,
								  @ApiParam(value = "用户主键",required = true)@RequestParam(value = "userInfoId") @NotNull @Min(1) Long userInfoId,
								  @ApiParam(value = "组主键",required = true)@RequestParam(value = "groupId") @NotNull @Min(1) Long groupId) {
		userInfoService.deleteUserGroup(userInfoId,groupId);
		return ok();
	}

	@Login
	@PostMapping("v1/pageQueryGroup")
	@ApiOperation(value = "根据用户ID分页查询组")
	public Object pageQueryGroup(@ApiParam(hidden = true)@Login Long userId,
								 @ApiParam(value = "第几页", required = true,example = "1") @RequestParam(value = "page") @NotNull @Min(0) Integer page,
								 @ApiParam(value = "多少条", required = true,example = "10")@RequestParam(value = "size") @NotNull @Min(1) Integer size,
								 @ApiParam(value = "用户主键",required = true)@RequestParam(value = "userInfoId") @NotNull @Min(1) Long userInfoId,
								 @ApiParam(value = "排序字段")@RequestParam(value = "sort",required = false) String sort) {
		return userInfoService.pageQueryGroup(page, size, sort, userInfoId);
	}
	@Login
	@PostMapping("v1/addUserRole")
	@ApiOperation(value = "添加用户角色")
	public Object addUserRole(@ApiParam(hidden = true)@Login Long userId,
							   @ApiParam(value = "用户主键",required = true)@RequestParam(value = "userInfoId") @NotNull @Min(1) Long userInfoId,
							   @ApiParam(value = "角色主键",required = true)@RequestParam(value = "roleId") @NotNull @Min(1) Long roleId) {
		userInfoService.addUserRole(userInfoId,roleId);
		return ok();
	}

	@Login
	@PostMapping("v1/deleteUserRole")
	@ApiOperation(value = "删除用户角色")
	public Object deleteUserRole(@ApiParam(hidden = true)@Login Long userId,
								  @ApiParam(value = "用户主键",required = true)@RequestParam(value = "userInfoId") @NotNull @Min(1) Long userInfoId,
								  @ApiParam(value = "角色主键",required = true)@RequestParam(value = "roleId") @NotNull @Min(1) Long roleId) {
		userInfoService.deleteUserRole(userInfoId,roleId);
		return ok();
	}

	@Login
	@PostMapping("v1/pageQueryRole")
	@ApiOperation(value = "根据用户ID分页查询角色")
	public Object pageQueryRole(@ApiParam(hidden = true)@Login Long userId,
								 @ApiParam(value = "第几页", required = true,example = "1") @RequestParam(value = "page") @NotNull @Min(0) Integer page,
								 @ApiParam(value = "多少条", required = true,example = "10")@RequestParam(value = "size") @NotNull @Min(1) Integer size,
								 @ApiParam(value = "用户主键",required = true)@RequestParam(value = "userInfoId") @NotNull @Min(1) Long userInfoId,
								 @ApiParam(value = "排序字段")@RequestParam(value = "sort",required = false) String sort) {
		return userInfoService.pageQueryRole(page, size, sort, userInfoId);
	}

	@Login
	@PostMapping("v1/selectUserMenu")
	@ApiOperation(value = "获取用户菜单")
	public Object selectUserMenu(@ApiParam(hidden = true)@Login Long userId) {
		return userInfoService.selectUserMenu(userId);
	}

	@Login
	@PostMapping("v1/getUsername")
	@ApiOperation(value = "查询账号是否存在")
	public Object getUsername(@ApiParam(hidden = true)@Login Long userId,
							  @ApiParam(value = "账号",required = true)@RequestParam(value = "username") @NotEmpty String username) {
		return userInfoService.getUsername(username);
	}
}
