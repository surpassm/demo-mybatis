package com.liaoin.demo.controller;

import com.liaoin.demo.annotation.Login;
import com.liaoin.demo.domain.PositionsVO;
import com.liaoin.demo.service.PositionsService;
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
  * Description 职位控制层
  */
@CrossOrigin
@RestController
@RequestMapping("/position/")
@Api(tags  =  "职位Api")
public class PositionsController {

    @Resource
    private PositionsService positionsService;



    @Login
    @PostMapping("v1/insert")
    @ApiOperation(value = "新增")
    public Object insert(@ApiParam(hidden = true)@Login Long userId,
                         @Valid @RequestBody PositionsVO vo, BindingResult errors) {
        ValidateUtil.check(errors);
        return positionsService.insertOrUpdate(vo);
    }

    @Login
    @PostMapping("v1/update")
    @ApiOperation(value = "修改")
    public Object update(@ApiParam(hidden = true)@Login Long userId,
                         @Valid @RequestBody PositionsVO vo, BindingResult errors) {
        ValidateUtil.check(errors);
        return positionsService.insertOrUpdate(vo);
    }

    @Login
    @PostMapping("v1/getById")
    @ApiOperation(value = "根据主键删除")
    public Object deleteGetById(@ApiParam(hidden = true)@Login Long userId,
                                @ApiParam(value = "主键",required = true)@RequestParam(value = "id")@NotNull @Min(1) Long id) {
        positionsService.deleteById(id);
		return ok();
    }

    @Login
    @PostMapping("v1/findById")
    @ApiOperation(value = "根据主键查询")
    public Object findById(@ApiParam(hidden = true)@Login Long userId,
                           @ApiParam(value = "主键",required = true)@RequestParam(value = "id") @NotNull @Min(1) Long id) {
        return positionsService.findById(id);
    }

    @Login
    @PostMapping("v1/pageQuery")
    @ApiOperation(value = "条件分页查询父级ID")
    public Object pageQuery(@ApiParam(hidden = true)@Login Long userId,
                            @ApiParam(value = "第几页", required = true,example = "1") @RequestParam(value = "page") @NotNull @Min(0) Integer page,
                            @ApiParam(value = "多少条", required = true,example = "10")@RequestParam(value = "size") @NotNull @Min(1) Integer size,
                            @ApiParam(value = "排序字段")@RequestParam(value = "sort",required = false) String sort,
                            @RequestBody PositionsVO vo) {
        return positionsService.pageQuery(page, size, sort, vo);
    }


	@Login
	@PostMapping("v1/findAllParent")
	@ApiOperation(value = "查询所有父级")
	public Object findAllChild(@ApiParam(hidden = true)@Login Long userId) {
		return positionsService.findAllParent();
	}

	@Login
	@PostMapping("v1/findAllChild")
	@ApiOperation(value = "根据父级ID查询所有子级")
	public Object findAllChild(@ApiParam(hidden = true)@Login Long userId,
							   @ApiParam(value = "父级ID",required = true)@RequestParam(value = "parentId") @NotNull @Min(1) Long parentId) {
		return positionsService.findAllChild(parentId);
	}
}
