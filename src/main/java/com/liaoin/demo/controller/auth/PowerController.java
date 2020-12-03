package com.liaoin.demo.controller.auth;

import com.liaoin.demo.annotation.Login;
import com.liaoin.demo.domain.PowerVO;
import com.liaoin.demo.service.PowerService;
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
  * Description 权限表控制层
  */
@CrossOrigin
@RestController
@RequestMapping("/power/")
@Api(tags  =  "权限表Api")
public class PowerController {

    @Resource
    private PowerService powerService;



    @Login
    @PostMapping("v1/insert")
    @ApiOperation(value = "新增")
    public Object insert(@ApiParam(hidden = true)@Login Long userId,
						 @Valid @RequestBody PowerVO vo, BindingResult errors) {
		ValidateUtil.check(errors);
        return powerService.insertOrUpdate(vo,userId);
    }

    @Login
    @PostMapping("v1/update")
    @ApiOperation(value = "修改")
    public Object update(@ApiParam(hidden = true)@Login Long userId,
						 @Valid @RequestBody PowerVO vo, BindingResult errors) {
		ValidateUtil.check(errors);
        return powerService.insertOrUpdate(vo,userId);
    }

    @Login
    @PostMapping("v1/getById")
    @ApiOperation(value = "根据主键删除")
    public Object deleteGetById(@ApiParam(hidden = true)@Login Long userId,
                                @ApiParam(value = "主键",required = true)@RequestParam(value = "id")@NotNull @Min(1) Long id) {
        powerService.deleteById(id);
		return ok();
    }

    @Login
    @PostMapping("v1/findById")
    @ApiOperation(value = "根据主键查询")
    public Object findById(@ApiParam(hidden = true)@Login Long userId,
                           @ApiParam(value = "主键",required = true)@RequestParam(value = "id") @NotNull @Min(1) Long id) {
        return powerService.findById(id);
    }

    @Login
    @PostMapping("v1/pageQuery")
    @ApiOperation(value = "条件分页查询")
    public Object pageQuery(@ApiParam(hidden = true)@Login Long userId,
                            @ApiParam(value = "第几页", required = true,example = "1") @RequestParam(value = "page") @NotNull @Min(0) Integer page,
                            @ApiParam(value = "多少条", required = true,example = "10")@RequestParam(value = "size") @NotNull @Min(1) Integer size,
                            @ApiParam(value = "排序字段")@RequestParam(value = "sort",required = false) String sort,
                            @RequestBody PowerVO vo) {
        return powerService.pageQuery(page, size, sort, vo);
    }

	@Login
	@PostMapping("v1/addPowerMenu")
	@ApiOperation(value = "添加权限菜单")
	public Object addPowerMenu(@ApiParam(hidden = true)@Login Long userId,
							   @ApiParam(value = "权限主键",required = true)@RequestParam(value = "powerId") @NotNull @Min(1) Long powerId,
							   @ApiParam(value = "菜单主键",required = true)@RequestParam(value = "menuId") @NotNull @Min(1) Long menuId) {
		powerService.addPowerMenu(powerId,menuId);
		return ok();
	}
	@Login
	@PostMapping("v1/deletePowerMenu")
	@ApiOperation(value = "删除权限菜單")
	public Object deletePowerMenu(@ApiParam(hidden = true)@Login Long userId,
								  @ApiParam(value = "权限主键",required = true)@RequestParam(value = "powerId") @NotNull @Min(1) Long powerId,
								  @ApiParam(value = "菜单主键",required = true)@RequestParam(value = "menuId") @NotNull @Min(1) Long menuId) {
		powerService.deletePowerMenu(powerId,menuId);
		return ok();
	}

	@Login
	@PostMapping("v1/pageQueryMenu")
	@ApiOperation(value = "根据权限ID分页查询菜单")
	public Object pageQueryMenu(@ApiParam(hidden = true)@Login Long userId,
								@ApiParam(value = "第几页", required = true,example = "1") @RequestParam(value = "page") @NotNull @Min(0) Integer page,
								@ApiParam(value = "多少条", required = true,example = "10")@RequestParam(value = "size") @NotNull @Min(1) Integer size,
								@ApiParam(value = "权限主键",required = true)@RequestParam(value = "powerId") @NotNull @Min(1) Long powerId,
								@ApiParam(value = "排序字段")@RequestParam(value = "sort",required = false) String sort) {
		return powerService.pageQueryMenu(page, size, sort, powerId);
	}

	@Login
	@PostMapping("v1/addPowerOperations")
	@ApiOperation(value = "添加权限接口")
	public Object addPowerOperations(@ApiParam(hidden = true)@Login Long userId,
								     @ApiParam(value = "权限主键",required = true)@RequestParam(value = "powerId") @NotNull @Min(1) Long powerId,
								     @ApiParam(value = "接口主键",required = true)@RequestParam(value = "operationsId") @NotNull @Min(1) Long operationsId) {
		powerService.addPowerOperations(powerId,operationsId);
		return ok();
	}
	@Login
	@PostMapping("v1/deletePowerOperations")
	@ApiOperation(value = "删除权限接口")
	public Object deletePowerOperations(@ApiParam(hidden = true)@Login Long userId,
								   		@ApiParam(value = "权限主键",required = true)@RequestParam(value = "powerId") @NotNull @Min(1) Long powerId,
								  		@ApiParam(value = "接口主键",required = true)@RequestParam(value = "operationsId") @NotNull @Min(1) Long operationsId) {
		powerService.deletePowerOperations(powerId,operationsId);
		return ok();
	}

	@Login
	@PostMapping("v1/pageQueryOperations")
	@ApiOperation(value = "根据权限ID分页查询接口")
	public Object pageQueryOperations(@ApiParam(hidden = true)@Login Long userId,
									  @ApiParam(value = "第几页", required = true,example = "1") @RequestParam(value = "page") @NotNull @Min(0) Integer page,
									  @ApiParam(value = "多少条", required = true,example = "10")@RequestParam(value = "size") @NotNull @Min(1) Integer size,
									  @ApiParam(value = "权限主键",required = true)@RequestParam(value = "powerId") @NotNull @Min(1) Long powerId,
									  @ApiParam(value = "排序字段")@RequestParam(value = "sort",required = false) String sort) {
		return powerService.pageQueryOperations(page, size, sort, powerId);
	}
}
