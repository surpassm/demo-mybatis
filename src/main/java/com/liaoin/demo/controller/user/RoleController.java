package com.liaoin.demo.controller.user;

import com.liaoin.demo.annotation.Login;
import com.liaoin.demo.common.Result;
import com.liaoin.demo.entity.user.Role;
import com.liaoin.demo.service.user.RoleService;
import io.swagger.annotations.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping("/role/")
@Api(tags  =  "角色Api")
public class RoleController {

    @Resource
    private RoleService roleService;

    @PostMapping("v1/insert")
    @ApiOperation(value = "新增")
    public Result insert(@ApiParam(hidden = true)@Login Long userId,
						 @RequestBody Role role, BindingResult errors) {
        if (errors.hasErrors()){
			return Result.fail(errors.getAllErrors());
		}
        return roleService.insert(userId,role);
    }

    @PostMapping("v1/update")
    @ApiOperation(value = "修改")
    public Result update(@ApiParam(hidden = true)@Login Long userId,
						 @RequestBody Role role, BindingResult errors) {
        if (errors.hasErrors()){
			return Result.fail(errors.getAllErrors());
		}
        return roleService.update(userId,role);
    }

    @PostMapping("v1/getById")
    @ApiOperation(value = "根据主键删除")
    public Result deleteGetById(@ApiParam(hidden = true)@Login Long userId,
                                @ApiParam(value = "主键",required = true)@RequestParam(value = "id") Long id) {
        return roleService.deleteGetById(userId,id);
    }

    @PostMapping("v1/findById")
    @ApiOperation(value = "根据主键查询")
    public Result findById(@ApiParam(hidden = true)@Login Long userId,
                           @ApiParam(value = "主键",required = true)@RequestParam(value = "id") Long id) {
        return roleService.findById(userId,id);
    }

    @PostMapping("v1/pageQuery")
    @ApiOperation(value = "条件分页查询")
    public Result pageQuery(@ApiParam(hidden = true)@Login Long userId,
                            @ApiParam(value = "第几页", required = true) @RequestParam(value = "page") Integer page,
                            @ApiParam(value = "多少条",required = true)@RequestParam(value = "size") Integer size,
                            @ApiParam(value = "排序字段")@RequestParam(value = "sort",required = false) String sort,
							Role role) {
        return roleService.pageQuery(userId,page, size, sort, role);
    }

	@PostMapping("v1/findMenus")
	@ApiOperation(value = "根据主键查询角色及权限列表")
	public Result findMenus(@ApiParam(hidden = true)@Login Long userId,
						    @ApiParam(value = "主键",required = true)@RequestParam(value = "id")@NotNull Long id) {
		return roleService.findMenus(userId,id);
	}

	@PostMapping("v1/setRoleByMenu")
	@ApiOperation(value = "设置角色权限",notes = "每次均需传全部权限ID，会把角色原有的所有权限做物理删除")
	public Result setRoleByMenu(@ApiParam(hidden = true)@Login Long userId,
								@ApiParam(value = "角色系统标识",required = true)@RequestParam(value = "id")@NotNull Long id,
								@ApiParam(value = "权限系统标识 多个权限请使用 ，分割",required = true)@RequestParam(value = "menuId")@NotEmpty String menuId) {
		return roleService.setRoleByMenu(userId,id,menuId);
	}

}
