package com.liaoin.demo.controller.user;

import com.liaoin.demo.annotation.Login;
import com.liaoin.demo.common.Result;
import com.liaoin.demo.entity.user.Group;
import com.liaoin.demo.service.user.GroupService;
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
 * Description 权限控制层
 */
@CrossOrigin
@RestController
@RequestMapping("/group/")
@Api(tags = "组Api")
public class GroupController {

    @Resource
    private GroupService groupService;

    @PostMapping("v1/insert")
    @ApiOperation(value = "新增")
    public Result insert(@ApiParam(hidden = true) @Login Long userId,
                         @RequestBody Group group, BindingResult errors) {
        if (errors.hasErrors()) {
            return Result.fail(errors.getAllErrors());
        }
        return groupService.insert(userId,group);
    }

    @PostMapping("v1/update")
    @ApiOperation(value = "修改")
    public Result update(@ApiParam(hidden = true) @Login Long userId,
                         @RequestBody Group group, BindingResult errors) {
        if (errors.hasErrors()) {
            return Result.fail(errors.getAllErrors());
        }
        return groupService.update(userId,group);
    }

    @PostMapping("v1/getById")
    @ApiOperation(value = "根据主键删除")
    public Result deleteGetById(@ApiParam(hidden = true) @Login Long userId,
                                @ApiParam(value = "主键", required = true) @RequestParam(value = "id") Long id) {
        return groupService.deleteGetById(userId,id);
    }

    @PostMapping("v1/findById")
    @ApiOperation(value = "根据主键查询")
    public Result findById(@ApiParam(hidden = true) @Login Long userId,
                           @ApiParam(value = "主键", required = true) @RequestParam(value = "id") Long id) {
        return groupService.findById(userId,id);
    }

    @PostMapping("v1/pageQuery")
    @ApiOperation(value = "条件分页查询")
    public Result pageQuery(@ApiParam(hidden = true) @Login Long userId,
                            @ApiParam(value = "第几页", required = true) @RequestParam(value = "page") Integer page,
                            @ApiParam(value = "多少条", required = true) @RequestParam(value = "size") Integer size,
                            @ApiParam(value = "排序字段") @RequestParam(value = "sort", required = false) String sort,
                            @RequestBody Group group) {
        return groupService.pageQuery(userId,page, size, sort, group);
    }

    @PostMapping("v1/findChildren")
    @ApiOperation(value = "根据父级Id查询所有子级")
    public Result getParentId(@ApiParam(hidden = true) @Login Long userId,
                              @ApiParam(value = "主键", required = true) @RequestParam(value = "parentId") @NotNull Long parentId) {
        return groupService.getParentId(userId,parentId);
    }

    @PostMapping("v1/findByOnlyAndChildren")
    @ApiOperation(value = "根据主键查询自己和所有子级")
    public Result findByOnlyAndChildren(@ApiParam(hidden = true) @Login Long userId,
                                        @ApiParam(value = "主键", required = true) @RequestParam(value = "id") @NotNull Long id) {
        return groupService.findByOnlyAndChildren(userId,id);
    }

    @PostMapping("v1/setGroupByRole")
    @ApiOperation(value = "设置组的角色", notes = "每次均需传全部角色ID，会把组原有的所有角色做物理删除")
    public Result setGroupByRole(@ApiParam(hidden = true) @Login Long userId,
                                 @ApiParam(value = "角色系统标识", required = true) @RequestParam(value = "id") @NotNull Long id,
                                 @ApiParam(value = "角色系统标识 多个角色请使用 ，分割", required = true) @RequestParam(value = "roleIds") @NotEmpty String roleIds) {
        return groupService.setGroupByRole(userId,id, roleIds);
    }

    @PostMapping("v1/findGroupToRole")
    @ApiOperation(value = "查询组的角色")
    public Result findGroupToRole(@ApiParam(hidden = true) @Login Long userId,
                                  @ApiParam(value = "主键", required = true) @RequestParam(value = "groupId") @NotNull Long groupId,
                                  @ApiParam(value = "第几页", required = true) @RequestParam(value = "page") Integer page,
                                  @ApiParam(value = "多少条", required = true) @RequestParam(value = "size") Integer size,
                                  @ApiParam(value = "排序字段") @RequestParam(value = "sort", required = false) String sort) {
        return groupService.findGroupToRole(userId,groupId, page, size, sort);
    }
}
