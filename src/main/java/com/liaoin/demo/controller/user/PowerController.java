package com.liaoin.demo.controller.user;

import com.liaoin.demo.annotation.Login;
import com.liaoin.demo.common.Result;
import com.liaoin.demo.entity.user.Power;
import com.liaoin.demo.service.user.PowerService;
import io.swagger.annotations.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;

import static com.liaoin.demo.common.Result.fail;


/**
  * @author mc
  * Create date 2019-11-28 10:47:51
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

    @PostMapping("v1/insert")
    @ApiOperation(value = "新增")
    public Result insert(@ApiParam(hidden = true)@Login Long userId,
                         Power power, BindingResult errors) {
        if (errors.hasErrors()){
			StringBuilder builder = new StringBuilder();
			errors.getAllErrors().forEach(i -> builder.append(i.getDefaultMessage()).append(","));
			return fail(builder.toString());
		}
        return powerService.insert(userId,power);
    }

    @PostMapping("v1/update")
    @ApiOperation(value = "修改")
    public Result update(@ApiParam(hidden = true)@Login Long userId,
                         Power power, BindingResult errors) {
        if (errors.hasErrors()){
			StringBuilder builder = new StringBuilder();
			errors.getAllErrors().forEach(i -> builder.append(i.getDefaultMessage()).append(","));
			return fail(builder.toString());
		}
        return powerService.update(userId,power);
    }

    @PostMapping("v1/getById")
    @ApiOperation(value = "根据主键删除")
    public Result deleteGetById(@ApiParam(hidden = true)@Login Long userId,
                                @ApiParam(value = "主键",required = true)@RequestParam(value = "id") Long id) {
        return powerService.deleteGetById(userId,id);
    }

    @PostMapping("v1/findById")
    @ApiOperation(value = "根据主键查询")
    public Result findById(@ApiParam(hidden = true)@Login Long userId,
                           @ApiParam(value = "主键",required = true)@RequestParam(value = "id") Long id) {
        return powerService.findById(userId,id);
    }

    @PostMapping("v1/pageQuery")
    @ApiOperation(value = "条件分页查询")
    public Result pageQuery(@ApiParam(hidden = true)@Login Long userId,
                            @ApiParam(value = "第几页", required = true,example = "1") @RequestParam(value = "page") Integer page,
                            @ApiParam(value = "多少条",required = true,example = "10")@RequestParam(value = "size") Integer size,
                            @ApiParam(value = "排序字段",example = "create_time desc")@RequestParam(value = "sort",required = false) String sort,
                            Power power) {
        return powerService.pageQuery(userId,page, size, sort, power);
    }

    @PostMapping("v1/getPower/menu")
    @ApiOperation(value = "根据权限Id查询菜单列表")
    public Result getPowerMenu(@ApiParam(hidden = true)@Login Long userId,
                               @ApiParam(value = "主键",required = true)@RequestParam(value = "id")@Min(1) Long id) {
        return powerService.getPowerMenu(userId,id);
    }
}
