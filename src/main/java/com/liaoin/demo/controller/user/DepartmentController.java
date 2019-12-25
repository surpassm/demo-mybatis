package com.liaoin.demo.controller.user;

import com.liaoin.demo.annotation.Login;
import com.liaoin.demo.common.Result;
import com.liaoin.demo.common.ResultCode;
import com.liaoin.demo.domain.user.DepartmentDTO;
import com.liaoin.demo.entity.user.Department;
import com.liaoin.demo.exception.CustomException;
import com.liaoin.demo.service.user.DepartmentService;
import io.swagger.annotations.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;

import static com.liaoin.demo.common.Result.fail;
import static com.liaoin.demo.common.Result.ok;


/**
 * @author mc
 * Create date 2019-03-14 20:41:03
 * Version 1.0
 * Description 部门控制层
 */
@CrossOrigin
@RestController
@RequestMapping("/department/")
@Api(tags = "部门Api")
public class DepartmentController {

    @Resource
    private DepartmentService departmentService;

    @Login
    @PostMapping("v1/insert")
    @ApiOperation(value = "新增")
    public Object insert(@ApiParam(hidden = true) @Login Long userId,
                         @Valid @RequestBody DepartmentDTO dto, BindingResult errors) {
        if (errors.hasErrors()) {
            return errors.getAllErrors();
        }
        return departmentService.insert(userId,dto);
    }

    @PostMapping("v1/update")
    @ApiOperation(value = "修改")
    public Object update(@ApiParam(hidden = true) @Login Long userId,
                         @RequestBody DepartmentDTO dto, BindingResult errors) {
        if (errors.hasErrors()) {
            return fail(errors.getAllErrors());
        }
        return departmentService.update(userId,dto);
    }

    @PostMapping("v1/getById")
    @ApiOperation(value = "根据主键删除")
    public Object deleteGetById(@ApiParam(hidden = true) @Login Long userId,
                                @ApiParam(value = "主键", required = true) @RequestParam(value = "id") Long id) {
        return departmentService.deleteGetById(userId, id);
    }

    @PostMapping("v1/findById")
    @ApiOperation(value = "根据主键查询,不返回子级")
    public Result findById(@ApiParam(hidden = true) @Login Long userId,
                           @ApiParam(value = "主键", required = true) @RequestParam(value = "id") Long id) {
        return departmentService.findById(userId, id);
    }

    @PostMapping("v1/findChildren")
    @ApiOperation(value = "根据父级Id查询所有子级")
    public Result getParentId(@ApiParam(hidden = true) @Login Long userId,
                              @ApiParam(value = "主键", required = true) @RequestParam(value = "parentId") @NotNull Integer parentId) {
        return departmentService.getParentId(userId, parentId);
    }

    @PostMapping("v1/findByOnlyAndChildren")
    @ApiOperation(value = "根据主键查询自己和所有子级")
    public Result findByOnlyAndChildren(@ApiParam(hidden = true) @Login Long userId,
                                        @ApiParam(value = "主键", required = true) @RequestParam(value = "id") @NotNull Integer id) {
        return departmentService.findByOnlyAndChildren(userId, id);
    }

    @PostMapping("v1/pageQuery")
    @ApiOperation(value = "条件分页查询返回所有父级")
    public Result pageQuery(@ApiParam(hidden = true) @Login Long userId,
                            @ApiParam(value = "第几页", required = true) @RequestParam(value = "page") Integer page,
                            @ApiParam(value = "多少条", required = true) @RequestParam(value = "size") Integer size,
                            @ApiParam(value = "排序字段") @RequestParam(value = "sort", required = false) String sort,
                            Department department) {
        return departmentService.pageQuery(userId, page, size, sort, department);
    }

    @PostMapping("v1/getDepartmentId")
    @ApiOperation(value = "根据部门查询所有员工")
    public Result getDepartmentId(@ApiParam(hidden = true) @Login Long userId,
                                  @ApiParam(value = "主键", required = true) @RequestParam(value = "id") @Min(1) Long id) {
        return departmentService.getDepartmentId(userId, id);
    }
}
