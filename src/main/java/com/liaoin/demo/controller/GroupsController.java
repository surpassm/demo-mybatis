package com.liaoin.demo.controller;

import com.liaoin.demo.annotation.Login;
import com.liaoin.demo.common.ResultCode;
import com.liaoin.demo.domain.GroupsVO;
import com.liaoin.demo.exception.CustomException;
import com.liaoin.demo.service.GroupsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static com.liaoin.demo.common.Result.ok;


/**
  * @author mc
  * Create date 2020-02-10 10:15:20
  * Version 1.0
  * Description 权限控制层
  */
@CrossOrigin
@RestController
@RequestMapping("/group/")
@Api(tags  =  "组Api")
public class GroupsController {

    @Resource
    private GroupsService groupsService;



    @Login
    @PostMapping("v1/insert")
    @ApiOperation(value = "新增")
    public Object insert(@ApiParam(hidden = true)@Login Long userId,
						 @Valid @RequestBody GroupsVO vo, BindingResult errors) {
        if (errors.hasErrors()){
			StringBuilder builder = new StringBuilder();
			errors.getAllErrors().forEach(i -> builder.append(i.getDefaultMessage()).append(","));
            throw new CustomException(ResultCode.PARAM_IS_INVALID.getCode(),builder.toString());
		}
        return groupsService.insertOrUpdate(vo);
    }

    @Login
    @PostMapping("v1/update")
    @ApiOperation(value = "修改")
    public Object update(@ApiParam(hidden = true)@Login Long userId,
						 @Valid @RequestBody GroupsVO vo, BindingResult errors) {
        if (errors.hasErrors()){
			StringBuilder builder = new StringBuilder();
			errors.getAllErrors().forEach(i -> builder.append(i.getDefaultMessage()).append(","));
            throw new CustomException(ResultCode.PARAM_IS_INVALID.getCode(),builder.toString());
		}
        return groupsService.insertOrUpdate(vo);
    }

    @Login
    @PostMapping("v1/getById")
    @ApiOperation(value = "根据主键删除")
    public Object deleteGetById(@ApiParam(hidden = true)@Login Long userId,
                                @ApiParam(value = "主键",required = true)@RequestParam(value = "id")@NotNull @Min(1) Long id) {
        groupsService.deleteById(id);
		return ok();
    }

    @Login
    @PostMapping("v1/findById")
    @ApiOperation(value = "根据主键查询")
    public Object findById(@ApiParam(hidden = true)@Login Long userId,
                           @ApiParam(value = "主键",required = true)@RequestParam(value = "id") @NotNull @Min(1) Long id) {
        return groupsService.findById(id);
    }

    @Login
    @PostMapping("v1/pageQuery")
    @ApiOperation(value = "条件分页查询父级")
    public Object pageQuery(@ApiParam(hidden = true)@Login Long userId,
                            @ApiParam(value = "第几页", required = true,example = "1") @RequestParam(value = "page") @NotNull @Min(0) Integer page,
                            @ApiParam(value = "多少条", required = true,example = "10")@RequestParam(value = "size") @NotNull @Min(1) Integer size,
                            @ApiParam(value = "排序字段")@RequestParam(value = "sort",required = false) String sort,
                            @RequestBody GroupsVO vo) {
        return groupsService.pageQuery(page, size, sort, vo);
    }

	@Login
	@PostMapping("v1/findAllParent")
	@ApiOperation(value = "查询所有父级")
	public Object findAllChild(@ApiParam(hidden = true)@Login Long userId) {
		return groupsService.findAllParent();
	}

	@Login
	@PostMapping("v1/findAllChild")
	@ApiOperation(value = "根据父级ID查询所有子级")
	public Object findAllChild(@ApiParam(hidden = true)@Login Long userId,
							   @ApiParam(value = "父级ID",required = true)@RequestParam(value = "parentId") @NotNull @Min(1) Long parentId) {
		return groupsService.findAllChild(parentId);
	}

	@Login
	@PostMapping("v1/addGroupDepartment")
	@ApiOperation(value = "添加组部门")
	public Object addGroupDepartment(@ApiParam(hidden = true)@Login Long userId,
									 @ApiParam(value = "组主键",required = true)@RequestParam(value = "groupId") @NotNull @Min(1) Long groupId,
									 @ApiParam(value = "部门主键",required = true)@RequestParam(value = "departmentId") @NotNull @Min(1) Long departmentId) {
		groupsService.addGroupDepartment(groupId,departmentId);
		return ok();
	}
	@Login
	@PostMapping("v1/deleteGroupDepartment")
	@ApiOperation(value = "删除组部门")
	public Object deleteGroupDepartment(@ApiParam(hidden = true)@Login Long userId,
									 	@ApiParam(value = "组主键",required = true)@RequestParam(value = "groupId") @NotNull @Min(1) Long groupId,
										@ApiParam(value = "部门主键",required = true)@RequestParam(value = "departmentId") @NotNull @Min(1) Long departmentId) {
		groupsService.deleteGroupDepartment(groupId,departmentId);
		return ok();
	}
	@Login
	@PostMapping("v1/pageQueryDepartment")
	@ApiOperation(value = "根据组ID分页查询部门")
	public Object pageQueryDepartment(@ApiParam(hidden = true)@Login Long userId,
									  @ApiParam(value = "第几页", required = true,example = "1") @RequestParam(value = "page") @NotNull @Min(0) Integer page,
									  @ApiParam(value = "多少条", required = true,example = "10")@RequestParam(value = "size") @NotNull @Min(1) Integer size,
									  @ApiParam(value = "组主键",required = true)@RequestParam(value = "groupId") @NotNull @Min(1) Long groupId,
									  @ApiParam(value = "排序字段")@RequestParam(value = "sort",required = false) String sort) {
		return groupsService.pageQueryDepartment(page, size, sort, groupId);
	}

	@Login
	@PostMapping("v1/addGroupMenu")
	@ApiOperation(value = "添加组菜单")
	public Object addGroupMenu(@ApiParam(hidden = true)@Login Long userId,
							   @ApiParam(value = "组主键",required = true)@RequestParam(value = "groupId") @NotNull @Min(1) Long groupId,
							   @ApiParam(value = "菜单主键",required = true)@RequestParam(value = "menuId") @NotNull @Min(1) Long menuId) {
		groupsService.addGroupMenu(groupId,menuId);
		return ok();
	}
	@Login
	@PostMapping("v1/deleteGroupMenu")
	@ApiOperation(value = "删除组菜單")
	public Object deleteGroupMenu(@ApiParam(hidden = true)@Login Long userId,
								  @ApiParam(value = "组主键",required = true)@RequestParam(value = "groupId") @NotNull @Min(1) Long groupId,
								  @ApiParam(value = "菜单主键",required = true)@RequestParam(value = "menuId") @NotNull @Min(1) Long menuId) {
		groupsService.deleteGroupMenu(groupId,menuId);
		return ok();
	}

	@Login
	@PostMapping("v1/pageQueryMenu")
	@ApiOperation(value = "根据组ID分页查询菜单")
	public Object pageQueryMenu(@ApiParam(hidden = true)@Login Long userId,
							    @ApiParam(value = "第几页", required = true,example = "1") @RequestParam(value = "page") @NotNull @Min(0) Integer page,
							    @ApiParam(value = "多少条", required = true,example = "10")@RequestParam(value = "size") @NotNull @Min(1) Integer size,
							    @ApiParam(value = "组主键",required = true)@RequestParam(value = "groupId") @NotNull @Min(1) Long groupId,
							    @ApiParam(value = "排序字段")@RequestParam(value = "sort",required = false) String sort) {
		return groupsService.pageQueryMenu(page, size, sort, groupId);
	}

	@Login
	@PostMapping("v1/addGroupRole")
	@ApiOperation(value = "添加组角色")
	public Object addGroupRole(@ApiParam(hidden = true)@Login Long userId,
							   @ApiParam(value = "组主键",required = true)@RequestParam(value = "groupId") @NotNull @Min(1) Long groupId,
							   @ApiParam(value = "角色主键",required = true)@RequestParam(value = "roleId") @NotNull @Min(1) Long roleId) {
		groupsService.addGroupRole(groupId,roleId);
		return ok();
	}
	@Login
	@PostMapping("v1/deleteGroupRole")
	@ApiOperation(value = "删除组角色")
	public Object deleteGroupRole(@ApiParam(hidden = true)@Login Long userId,
								  @ApiParam(value = "组主键",required = true)@RequestParam(value = "groupId") @NotNull @Min(1) Long groupId,
								  @ApiParam(value = "角色主键",required = true)@RequestParam(value = "roleId") @NotNull @Min(1) Long roleId) {
		groupsService.deleteGroupRole(groupId,roleId);
		return ok();
	}
	@Login
	@PostMapping("v1/pageQueryRole")
	@ApiOperation(value = "根据组ID分页查询角色")
	public Object pageQueryRole(@ApiParam(hidden = true)@Login Long userId,
								@ApiParam(value = "第几页", required = true,example = "1") @RequestParam(value = "page") @NotNull @Min(0) Integer page,
								@ApiParam(value = "多少条", required = true,example = "10")@RequestParam(value = "size") @NotNull @Min(1) Integer size,
								@ApiParam(value = "组主键",required = true)@RequestParam(value = "groupId") @NotNull @Min(1) Long groupId,
								@ApiParam(value = "排序字段")@RequestParam(value = "sort",required = false) String sort) {
		return groupsService.pageQueryRole(page, size, sort, groupId);
	}
}
