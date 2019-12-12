package com.liaoin.demo.controller.user;

import com.liaoin.demo.annotation.Login;
import com.liaoin.demo.common.Result;
import com.liaoin.demo.entity.user.UserInfo;
import com.liaoin.demo.service.user.UserInfoService;
import io.swagger.annotations.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author mc
 * Create date 2019-03-14 20:41:03
 * Version 1.0
 * Description 控制层
 */
@CrossOrigin
@RestController
@RequestMapping("/userInfo/")
@Api(tags = "用户Api")
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;

    @PostMapping("v1/insert")
    @ApiOperation(value = "新增")
    public Result insert(@ApiParam(hidden = true) @Login Long userId,
                         @RequestBody UserInfo userInfo, BindingResult errors) {
        if (errors.hasErrors()) {
            return Result.fail(errors.getAllErrors());
        }
        return userInfoService.insert(userId, userInfo);
    }

    @PostMapping("v1/update")
    @ApiOperation(value = "修改")
    public Result update(@ApiParam(hidden = true) @Login Long userId,
                         @RequestBody UserInfo userInfo, BindingResult errors) {
        if (errors.hasErrors()) {
            return Result.fail(errors.getAllErrors());
        }
        return userInfoService.update(userId, userInfo);
    }

    @PostMapping("v1/getById")
    @ApiOperation(value = "根据主键删除")
    public Result deleteGetById(@ApiParam(hidden = true) @Login Long userId,
                                @ApiParam(value = "主键", required = true) @RequestParam(value = "id") Long id) {
        return userInfoService.deleteGetById(userId, id);
    }

    @PostMapping("v1/findById")
    @ApiOperation(value = "根据主键查询")
    public Result findById(@ApiParam(hidden = true) @Login Long userId,
                           @ApiParam(value = "主键", required = true) @RequestParam(value = "id") Long id) {
        return userInfoService.findById(userId, id);
    }

    @PostMapping("v1/pageQuery")
    @ApiOperation(value = "条件分页查询")
    public Result pageQuery(@ApiParam(hidden = true) @Login Long userId,
                            @ApiParam(value = "第几页", required = true) @RequestParam(value = "page") Integer page,
                            @ApiParam(value = "多少条", required = true) @RequestParam(value = "size") Integer size,
                            @ApiParam(value = "排序字段") @RequestParam(value = "sort", required = false) String sort,
                            @RequestBody UserInfo userInfo) {
        return userInfoService.pageQuery(userId, page, size, sort, userInfo);
    }

    @PostMapping("v1/findRolesAndMenus")
    @ApiOperation(value = "根据主键查询用户及角色、权限列表")
    public Result findRolesAndMenus(@ApiParam(hidden = true) @Login Long userId,
                                    @ApiParam(value = "用户系统标识", required = true) @RequestParam(value = "id") @NotNull Long id) {
        return userInfoService.findRolesAndMenus(userId, id);
    }

    @PostMapping("v1/setUserByGroups")
    @ApiOperation(value = "设置用户、组", notes = "每次均需传全部组ID，会把用户原有的所有组做物理删除")
    public Result setUserByGroup(@ApiParam(hidden = true) @Login Long userId,
                                 @ApiParam(value = "用户系统标识", required = true) @RequestParam(value = "id") @NotNull Long id,
                                 @ApiParam(value = "组系统标识 多个组请使用 ，分割", required = true) @RequestParam(value = "groupId") @NotEmpty String groupIds) {
        return userInfoService.setUserByGroup(userId, id, groupIds);
    }

    @PostMapping("v1/setUserByMenus")
    @ApiOperation(value = "设置用户、权限", notes = "每次均需传全部权限ID，会把用户原有的所有权限做物理删除")
    public Result setUserByMenu(@ApiParam(hidden = true) @Login Long userId,
                                @ApiParam(value = "用户系统标识", required = true) @RequestParam(value = "id") @NotNull Long id,
                                @ApiParam(value = "权限系统标识 多个权限请使用 ，分割", required = true) @RequestParam(value = "menuIds") @NotEmpty String menuIds) {
        return userInfoService.setUserByMenu(userId, id, menuIds);
    }

    @PostMapping("v1/setUserByRoles")
    @ApiOperation(value = "设置用户、角色", notes = "每次均需传全部角色ID，会把用户原有的所有角色做物理删除")
    public Result setUserByRoles(@ApiParam(hidden = true) @Login Long userId,
                                 @ApiParam(value = "用户系统标识", required = true) @RequestParam(value = "id") @NotNull Long id,
                                 @ApiParam(value = "角色系统标识 多个权限请使用 ，分割", required = true) @RequestParam(value = "roleIds") @NotEmpty String roleIds) {
        return userInfoService.setUserByRoles(userId, id, roleIds);
    }
}
