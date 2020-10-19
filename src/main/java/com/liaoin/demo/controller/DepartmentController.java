package com.liaoin.demo.controller;

import com.liaoin.demo.annotation.Login;
import com.liaoin.demo.domain.DepartmentVO;
import com.liaoin.demo.service.DepartmentService;
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
  * Create date 2020-02-10 10:15:20
  * Version 1.0
  * Description 部门控制层
  */
@CrossOrigin
@RestController
@RequestMapping("/department/")
@Api(tags  =  "部门Api")
public class DepartmentController {

    @Resource
    private DepartmentService departmentService;



    @Login
    @PostMapping("v1/insert")
    @ApiOperation(value = "新增")
    public Object insert(@ApiParam(hidden = true)@Login Long userId,
						 @Valid @RequestBody DepartmentVO vo, BindingResult errors) {
		ValidateUtil.check(errors);
        return departmentService.insertOrUpdate(vo);
    }

    @Login
    @PostMapping("v1/update")
    @ApiOperation(value = "修改")
    public Object update(@ApiParam(hidden = true)@Login Long userId,
						 @Valid @RequestBody DepartmentVO vo, BindingResult errors) {
		ValidateUtil.check(errors);
        return departmentService.insertOrUpdate(vo);
    }

    @Login
    @PostMapping("v1/getById")
    @ApiOperation(value = "根据主键删除")
    public void deleteGetById(@ApiParam(hidden = true)@Login Long userId,
                                @ApiParam(value = "主键",required = true)@RequestParam(value = "id")@NotNull @Min(1) Long id) {
        departmentService.deleteById(id);
    }

    @Login
    @PostMapping("v1/findById")
    @ApiOperation(value = "根据主键查询")
    public Object findById(@ApiParam(hidden = true)@Login Long userId,
                           @ApiParam(value = "主键",required = true)@RequestParam(value = "id") @NotNull @Min(1) Long id) {
        return departmentService.findById(id);
    }

    @Login
    @PostMapping("v1/pageQuery")
    @ApiOperation(value = "条件分页查询父级")
    public Object pageQuery(@ApiParam(hidden = true)@Login Long userId,
                            @ApiParam(value = "第几页", required = true,example = "1") @RequestParam(value = "page") @NotNull @Min(0) Integer page,
                            @ApiParam(value = "多少条", required = true,example = "10")@RequestParam(value = "size") @NotNull @Min(1) Integer size,
                            @ApiParam(value = "排序字段")@RequestParam(value = "sort",required = false) String sort,
                            @RequestBody DepartmentVO vo) {
        return departmentService.pageQuery(page, size, sort, vo);
    }

	@Login
	@PostMapping("v1/findAllParent")
	@ApiOperation(value = "查询所有父级")
	public Object findAllChild(@ApiParam(hidden = true)@Login Long userId) {
		return departmentService.findAllParent();
	}

	@Login
	@PostMapping("v1/findAllChild")
	@ApiOperation(value = "根据父级ID查询所有子级")
	public Object findAllChild(@ApiParam(hidden = true)@Login Long userId,
							   @ApiParam(value = "父级ID",required = true)@RequestParam(value = "parentId") @NotNull @Min(1) Long parentId) {
		return departmentService.findAllChild(parentId);
	}

	@Login
	@PostMapping("v1/addDepartmentPerson")
	@ApiOperation(value = "添加部门人员")
	public Object addDepartmentPerson(@ApiParam(hidden = true)@Login Long userId,
									  @ApiParam(value = "部门主键",required = true)@RequestParam(value = "departmentId") @NotNull @Min(1) Long departmentId,
									  @ApiParam(value = "用户主键",required = true)@RequestParam(value = "userInfoId") @NotNull @Min(1) Long userInfoId) {
		departmentService.addDepartmentPerson(departmentId,userInfoId);
    	return ok();
	}

	@Login
	@PostMapping("v1/deleteDepartmentPerson")
	@ApiOperation(value = "刪除部门人员")
	public Object deleteDepartmentPerson(@ApiParam(hidden = true)@Login Long userId,
									  	 @ApiParam(value = "部门主键",required = true)@RequestParam(value = "departmentId") @NotNull @Min(1) Long departmentId,
										 @ApiParam(value = "用户主键",required = true)@RequestParam(value = "userInfoId") @NotNull @Min(1) Long userInfoId) {
		departmentService.deleteDepartmentPerson(departmentId,userInfoId);
    	return ok();
	}

	@Login
	@PostMapping("v1/pageQueryDepartmentPerson")
	@ApiOperation(value = "根据部门ID分页查询部门下员工")
	public Object pageQueryDepartmentPerson(@ApiParam(hidden = true)@Login Long userId,
											@ApiParam(value = "部门主键",required = true)@RequestParam(value = "departmentId") @NotNull @Min(1) Long departmentId,
											@ApiParam(value = "第几页", required = true,example = "1") @RequestParam(value = "page") @NotNull @Min(0) Integer page,
											@ApiParam(value = "多少条", required = true,example = "10")@RequestParam(value = "size") @NotNull @Min(1) Integer size,
											@ApiParam(value = "排序字段")@RequestParam(value = "sort",required = false) String sort) {
		return departmentService.pageQueryDepartmentPerson(departmentId,page, size, sort);
	}
}
