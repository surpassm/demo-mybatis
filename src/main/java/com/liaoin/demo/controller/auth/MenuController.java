package com.liaoin.demo.controller.auth;

import com.liaoin.demo.annotation.Login;
import com.liaoin.demo.annotation.ResponseResult;
import com.liaoin.demo.domain.MenuVO;
import com.liaoin.demo.domain.MenuVos;
import com.liaoin.demo.service.MenuService;
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
import java.util.List;

import static com.liaoin.demo.common.R.ok;


/**
 * @author mc
 * Create date 2020-02-10 10:15:20
 * Version 1.0
 * Description 权限控制层
 */
@CrossOrigin
@RestController
@RequestMapping("/menu/")
@Api(tags = "菜单Api")
public class MenuController {

    @Resource
    private MenuService menuService;


    @Login
    @Deprecated
    @PostMapping("v1/insert")
    @ApiOperation(value = "新增",hidden = true,notes = "废弃")
    @ResponseResult
    public Object insert(@ApiParam(hidden = true) @Login Long userId,
                         @RequestBody List<MenuVos> vos, BindingResult errors) {
        ValidateUtil.check(errors);
        return menuService.insertVO(vos);
    }



    @Login
    @PostMapping("v1/insert/list")
    @ApiOperation(value = "一键同步")
    @ResponseResult
    public Object insertList(@ApiParam(hidden = true) @Login Long userId,
                             @RequestBody List<MenuVos> vos) {
        menuService.insertList(vos);
        return ok();
    }

    @Login
    @PostMapping("v1/update")
    @ApiOperation(value = "修改")
    @ResponseResult
    public Object update(@ApiParam(hidden = true) @Login Long userId,
                         @Valid @RequestBody MenuVO vo, BindingResult errors) {
        ValidateUtil.check(errors);
        return menuService.updateVO(vo);
    }

    @Login
    @PostMapping("v1/getById")
    @ApiOperation(value = "根据主键删除")
    @ResponseResult
    public Object deleteGetById(@ApiParam(hidden = true) @Login Long userId,
                                @ApiParam(value = "主键", required = true) @RequestParam(value = "id") @NotNull @Min(1) Long id) {
        menuService.deleteById(id);
        return ok();
    }

    @Login
    @PostMapping("v1/findById")
    @ApiOperation(value = "根据主键查询")
    @ResponseResult
    public Object findById(@ApiParam(hidden = true) @Login Long userId,
                           @ApiParam(value = "主键", required = true) @RequestParam(value = "id") @NotNull @Min(1) Long id) {
        return menuService.findById(id);
    }

    @Login
    @PostMapping("v1/pageQuery")
    @ApiOperation(value = "条件分页查询父级")
    @ResponseResult
    public Object pageQuery(@ApiParam(hidden = true) @Login Long userId,
                            @ApiParam(value = "第几页", required = true, example = "1") @RequestParam(value = "page") @NotNull @Min(0) Integer page,
                            @ApiParam(value = "多少条", required = true, example = "10") @RequestParam(value = "size") @NotNull @Min(1) Integer size,
                            @ApiParam(value = "排序字段") @RequestParam(value = "sort", required = false) String sort,
                            @RequestBody MenuVO vo) {
        return menuService.pageQuery(page, size, sort, vo);
    }

    @Login
    @PostMapping("v1/findAllParent")
    @ApiOperation(value = "查询所有父级")
    @ResponseResult
    public Object findAllChild(@ApiParam(hidden = true) @Login Long userId) {
        return menuService.findAllParent();
    }

    @Login
    @PostMapping("v1/findAllChild")
    @ApiOperation(value = "根据父级ID查询所有子级")
    @ResponseResult
    public Object findAllChild(@ApiParam(hidden = true) @Login Long userId,
                               @ApiParam(value = "父级ID", required = true) @RequestParam(value = "parentId") @NotNull @Min(1) Long parentId) {
        return menuService.findAllChild(parentId);
    }

    @Login
    @PostMapping("v1/findAll")
    @ApiOperation(value = "查询所有菜单")
    @ResponseResult
    public Object findAll(@ApiParam(hidden = true) @Login Long userId,@ApiParam(value = "权限id", required = true) @RequestParam(value = "powerId") @NotNull @Min(1) Long powerId) {
        return menuService.findAll(powerId);
    }

}
