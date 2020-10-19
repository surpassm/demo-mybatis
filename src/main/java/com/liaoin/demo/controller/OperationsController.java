package com.liaoin.demo.controller;

import com.liaoin.demo.annotation.Login;
import com.liaoin.demo.domain.OperationsVO;
import com.liaoin.demo.service.OperationsService;
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
  * Description 后台功能接口控制层
  */
@CrossOrigin
@RestController
@RequestMapping("/operations/")
@Api(tags  =  "后台功能接口Api")
public class OperationsController {

    @Resource
    private OperationsService operationsService;



    @Login
    @PostMapping("v1/insert")
    @ApiOperation(value = "新增")
    public Object insert(@ApiParam(hidden = true)@Login Long userId,
						 @Valid @RequestBody OperationsVO vo, BindingResult errors) {
        ValidateUtil.check(errors);
        return operationsService.insertOrUpdate(vo);
    }

    @Login
    @PostMapping("v1/update")
    @ApiOperation(value = "修改")
    public Object update(@ApiParam(hidden = true)@Login Long userId,
						 @Valid @RequestBody OperationsVO vo, BindingResult errors) {
        ValidateUtil.check(errors);
        return operationsService.insertOrUpdate(vo);
    }

    @Login
    @PostMapping("v1/getById")
    @ApiOperation(value = "根据主键删除")
    public Object deleteGetById(@ApiParam(hidden = true)@Login Long userId,
                                @ApiParam(value = "主键",required = true)@RequestParam(value = "id")@NotNull @Min(1) Long id) {
        operationsService.deleteById(id);
		return ok();
    }

    @Login
    @PostMapping("v1/findById")
    @ApiOperation(value = "根据主键查询")
    public Object findById(@ApiParam(hidden = true)@Login Long userId,
                           @ApiParam(value = "主键",required = true)@RequestParam(value = "id") @NotNull @Min(1) Long id) {
        return operationsService.findById(id);
    }

    @Login
    @PostMapping("v1/pageQuery")
    @ApiOperation(value = "条件分页查询父级")
    public Object pageQuery(@ApiParam(hidden = true)@Login Long userId,
                            @ApiParam(value = "第几页", required = true,example = "1") @RequestParam(value = "page") @NotNull @Min(0) Integer page,
                            @ApiParam(value = "多少条", required = true,example = "10")@RequestParam(value = "size") @NotNull @Min(1) Integer size,
                            @ApiParam(value = "排序字段")@RequestParam(value = "sort",required = false) String sort,
                            @RequestBody OperationsVO vo) {
        return operationsService.pageQuery(page, size, sort, vo);
    }
	@Login
	@PostMapping("v1/findAllParent")
	@ApiOperation(value = "查询所有父级")
	public Object findAllChild(@ApiParam(hidden = true)@Login Long userId) {
		return operationsService.findAllParent();
	}

	@Login
	@PostMapping("v1/findAllChild")
	@ApiOperation(value = "根据父级ID查询所有子级")
	public Object findAllChild(@ApiParam(hidden = true)@Login Long userId,
							   @ApiParam(value = "父级ID",required = true)@RequestParam(value = "parentId") @NotNull @Min(1) Long parentId) {
		return operationsService.findAllChild(parentId);
	}
}
