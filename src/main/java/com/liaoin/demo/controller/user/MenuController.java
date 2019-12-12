package com.liaoin.demo.controller.user;

import com.liaoin.demo.annotation.Login;
import com.liaoin.demo.common.Result;
import com.liaoin.demo.entity.user.Menu;
import com.liaoin.demo.service.user.MenuService;
import io.swagger.annotations.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
  * @author mc
  * Create date 2019-03-14 20:41:03
  * Version 1.0
  * Description 权限控制层
  */
@CrossOrigin
@RestController
@RequestMapping("/menu/")
@Api(tags  =  "权限Api")
public class MenuController {

    @Resource
    private MenuService menuService;

    @PostMapping("v1/insert")
    @ApiOperation(value = "新增")
    public Result insert(@ApiParam(hidden = true)@Login Long userId,
						 @RequestBody Menu menu, BindingResult errors) {
        if (errors.hasErrors()){
			return Result.fail(errors.getAllErrors());
		}
        return menuService.insert(userId,menu);
    }

    @PostMapping("v1/update")
    @ApiOperation(value = "修改")
    public Result update(@ApiParam(hidden = true)@Login Long userId,
						 @RequestBody Menu menu, BindingResult errors) {
        if (errors.hasErrors()){
			return Result.fail(errors.getAllErrors());
		}
        return menuService.update(userId,menu);
    }

    @PostMapping("v1/getById")
    @ApiOperation(value = "根据主键删除")
    public Result deleteGetById(@ApiParam(hidden = true)@Login Long userId,
                                @ApiParam(value = "主键",required = true)@RequestParam(value = "id") Long id) {
        return menuService.deleteGetById(userId,id);
    }

    @PostMapping("v1/findById")
    @ApiOperation(value = "根据主键查询")
    public Result findById(@ApiParam(hidden = true)@Login Long userId,
                           @ApiParam(value = "主键",required = true)@RequestParam(value = "id") Long id) {
        return menuService.findById(userId,id);
    }

    @PostMapping("v1/pageQuery")
    @ApiOperation(value = "条件分页查询")
    public Result pageQuery(@ApiParam(hidden = true)@Login Long userId,
                            @ApiParam(value = "第几页", required = true) @RequestParam(value = "page") Integer page,
                            @ApiParam(value = "多少条",required = true)@RequestParam(value = "size") Integer size,
                            @ApiParam(value = "排序字段")@RequestParam(value = "sort",required = false) String sort,
							@RequestBody Menu menu) {
        return menuService.pageQuery(userId,page, size, sort, menu);
    }

	@PostMapping("v1/findChildren")
	@ApiOperation(value = "根据父级Id查询所有子级")
	public Result getParentId(@ApiParam(hidden = true)@Login Long userId,
							  @ApiParam(value = "主键",required = true)@RequestParam(value = "parentId")@NotNull Long parentId) {
		return menuService.getParentId(userId,parentId);
	}

	@PostMapping("v1/findByOnlyAndChildren")
	@ApiOperation(value = "根据主键查询自己和所有子级")
	public Result findByOnlyAndChildren(@ApiParam(hidden = true)@Login Long userId,
										@ApiParam(value = "主键",required = true)@RequestParam(value = "id")@NotNull Long id) {
		return menuService.findByOnlyAndChildren(userId,id);
	}

}
