package com.liaoin.demo.controller.user;

import com.liaoin.demo.annotation.Login;
import com.liaoin.demo.common.Result;
import com.liaoin.demo.common.ResultCode;
import com.liaoin.demo.entity.user.Operations;
import com.liaoin.demo.service.user.OperationsService;
import io.swagger.annotations.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

import java.util.Optional;

import static com.liaoin.demo.common.Result.fail;
import static com.liaoin.demo.common.Result.ok;


/**
 * @author mc
 * Create date 2019-11-28 10:47:51
 * Version 1.0
 * Description 后台功能接口控制层
 */
@CrossOrigin
@RestController
@RequestMapping("/operations/")
@Api(tags = "后台功能接口Api")
public class OperationsController {

    @Resource
    private OperationsService operationsService;

    @Login
    @PostMapping("v1/update")
    @ApiOperation(value = "修改")
    public Result update(@ApiParam(hidden = true) @Login Long userId,
                         Operations operations, BindingResult errors) {
        if (errors.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            errors.getAllErrors().forEach(i -> builder.append(i.getDefaultMessage()).append(","));
            return fail(builder.toString());
        }
        return operationsService.update(userId, operations);
    }

    @Login
    @PostMapping("v1/getById")
    @ApiOperation(value = "根据主键删除")
    public Result deleteGetById(@ApiParam(hidden = true) @Login Long userId,
                                @ApiParam(value = "主键", required = true) @RequestParam(value = "id") @NotNull Long id) {
        Boolean flag = operationsService.selectCountParentId(id);
        if (flag) {
            return fail("存在子级关联");
        }
        operationsService.deleteGetById(id);
        return ok();
    }

    @Login
    @PostMapping("v1/findById")
    @ApiOperation(value = "根据主键查询")
    public Result findById(@ApiParam(value = "主键", required = true) @RequestParam(value = "id") Long id) {
        Optional<Operations> optional = operationsService.findById(id);
        return optional.map(Result::ok).orElseGet(() -> fail(ResultCode.RESULE_DATA_NONE.getMsg()));
    }

    @Login
    @PostMapping("v1/pageQuery")
    @ApiOperation(value = "条件分页查询")
    public Result pageQuery(@ApiParam(hidden = true) @Login Long userId,
                            @ApiParam(value = "第几页", required = true, example = "1") @RequestParam(value = "page") Integer page,
                            @ApiParam(value = "多少条", required = true, example = "10") @RequestParam(value = "size") Integer size,
                            @ApiParam(value = "排序字段", example = "create_time desc") @RequestParam(value = "sort", required = false) String sort,
                            Operations operations) {
        return operationsService.pageQuery(userId, page, size, sort, operations);
    }
}
