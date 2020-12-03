package com.liaoin.demo.controller.auth;

import com.liaoin.demo.annotation.Login;
import com.liaoin.demo.annotation.ResponseResult;
import com.liaoin.demo.domain.RoleVO;
import com.liaoin.demo.service.RoleService;
import com.liaoin.demo.util.ValidateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static com.liaoin.demo.common.R.ok;


/**
  * @author mc
  * Create date 2020-02-10 10:15:21
  * Version 1.0
  * Description 角色控制层
  */
@CrossOrigin
@RestController
@RequestMapping("/role/")
@Api(tags  =  "角色Api")
public class RoleController {

    @Resource
    private RoleService roleService;



    @Login
    @PostMapping("v1/insert")
    @ApiOperation(value = "新增")
    public Object insert(@ApiParam(hidden = true)@Login Long userId,
						 @Valid @RequestBody RoleVO vo, BindingResult errors) {
		ValidateUtil.check(errors);
        return roleService.insertOrUpdate(vo);
    }

    @Login
    @PostMapping("v1/update")
    @ApiOperation(value = "修改")
    public Object update(@ApiParam(hidden = true)@Login Long userId,
						 @Valid @RequestBody RoleVO vo, BindingResult errors) {
		ValidateUtil.check(errors);
        return roleService.insertOrUpdate(vo);
    }

    @Login
    @PostMapping("v1/getById")
    @ApiOperation(value = "根据主键删除")
    public Object deleteGetById(@ApiParam(hidden = true)@Login Long userId,
                                @ApiParam(value = "主键",required = true)@RequestParam(value = "id")@NotNull @Min(1) Long id) {
        roleService.deleteById(id);
        return ok();
    }

    @Login
    @PostMapping("v1/findById")
    @ApiOperation(value = "根据主键查询")
    public Object findById(@ApiParam(hidden = true)@Login Long userId,
                           @ApiParam(value = "主键",required = true)@RequestParam(value = "id") @NotNull @Min(1) Long id) {
        return roleService.findById(id);
    }

    @Login
    @PostMapping("v1/pageQuery")
    @ApiOperation(value = "条件分页查询")
    public Object pageQuery(@ApiParam(hidden = true)@Login Long userId,
                            @ApiParam(value = "第几页", required = true,example = "1") @RequestParam(value = "page") @NotNull @Min(0) Integer page,
                            @ApiParam(value = "多少条", required = true,example = "10")@RequestParam(value = "size") @NotNull @Min(1) Integer size,
                            @ApiParam(value = "排序字段")@RequestParam(value = "sort",required = false) String sort,
                            @RequestBody RoleVO vo) {
        return roleService.pageQuery(page, size, sort, vo);
    }

	@Login
	@PostMapping("v1/addRoleDepartment")
	@ApiOperation(value = "添加角色部门")
	public Object addRoleDepartment(@ApiParam(hidden = true)@Login Long userId,
							   @ApiParam(value = "角色主键",required = true)@RequestParam(value = "roleId") @NotNull @Min(1) Long roleId,
							   @ApiParam(value = "部门主键",required = true)@RequestParam(value = "departmentId") @NotNull @Min(1) Long departmentId) {
		roleService.addRoleDepartment(roleId,departmentId);
		return ok();
	}
	@Login
	@PostMapping("v1/deleteRoleDepartment")
	@ApiOperation(value = "删除角色部门")
	public Object deleteRoleDepartment(@ApiParam(hidden = true)@Login Long userId,
								  @ApiParam(value = "角色主键",required = true)@RequestParam(value = "roleId") @NotNull @Min(1) Long roleId,
								  @ApiParam(value = "部门主键",required = true)@RequestParam(value = "departmentId") @NotNull @Min(1) Long departmentId) {
		roleService.deleteRoleDepartment(roleId,departmentId);
		return ok();
	}

	@Login
	@PostMapping("v1/pageQueryDepartment")
	@ApiOperation(value = "根据角色ID分页查询部门")
	public Object pageQueryDepartment(@ApiParam(hidden = true)@Login Long userId,
								@ApiParam(value = "第几页", required = true,example = "1") @RequestParam(value = "page") @NotNull @Min(0) Integer page,
								@ApiParam(value = "多少条", required = true,example = "10")@RequestParam(value = "size") @NotNull @Min(1) Integer size,
								@ApiParam(value = "角色主键",required = true)@RequestParam(value = "roleId") @NotNull @Min(1) Long roleId,
								@ApiParam(value = "排序字段")@RequestParam(value = "sort",required = false) String sort) {
		return roleService.pageQueryDepartment(page, size, sort, roleId);
	}

	@Login
	@PostMapping("v1/addRolePower")
	@ApiOperation(value = "添加角色权限")
	public Object addRolePower(@ApiParam(hidden = true)@Login Long userId,
									@ApiParam(value = "角色主键",required = true)@RequestParam(value = "roleId") @NotNull @Min(1) Long roleId,
									@ApiParam(value = "权限主键",required = true)@RequestParam(value = "powerId") @NotNull @Min(1) Long powerId) {
		roleService.addRolePower(roleId,powerId);
		return ok();
	}
	@Login
	@PostMapping("v1/deleteRolePower")
	@ApiOperation(value = "删除角色权限")
	public Object deleteRolePower(@ApiParam(hidden = true)@Login Long userId,
									   @ApiParam(value = "角色主键",required = true)@RequestParam(value = "roleId") @NotNull @Min(1) Long roleId,
									   @ApiParam(value = "权限主键",required = true)@RequestParam(value = "powerId") @NotNull @Min(1) Long powerId) {
		roleService.deleteRolePower(roleId,powerId);
		return ok();
	}

	@Login
	@PostMapping("v1/pageQueryPower")
	@ApiOperation(value = "根据角色ID分页查询权限")
	public Object pageQueryPower(@ApiParam(hidden = true)@Login Long userId,
								@ApiParam(value = "第几页", required = true,example = "1") @RequestParam(value = "page") @NotNull @Min(0) Integer page,
								@ApiParam(value = "多少条", required = true,example = "10")@RequestParam(value = "size") @NotNull @Min(1) Integer size,
								@ApiParam(value = "角色主键",required = true)@RequestParam(value = "roleId") @NotNull @Min(1) Long roleId,
								@ApiParam(value = "排序字段")@RequestParam(value = "sort",required = false) String sort) {
		return roleService.pageQueryPower(page, size, sort, roleId);
	}


	@Login
	@PostMapping("v1/findAll")
	@ApiOperation(value = "查询所有角色信息")
	@ResponseResult
	public Object findAll(@ApiParam(hidden = true)@Login Long userId) {
		return roleService.findAll();
	}

}
