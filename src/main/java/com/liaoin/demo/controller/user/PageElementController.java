package com.liaoin.demo.controller.user;

import com.liaoin.demo.annotation.Login;
import com.liaoin.demo.common.Result;
import com.liaoin.demo.entity.user.PageElement;
import com.liaoin.demo.service.user.PageElementService;
import io.swagger.annotations.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.liaoin.demo.common.Result.fail;


/**
  * @author mc
  * Create date 2019-11-28 10:47:51
  * Version 1.0
  * Description 页面元素控制层
  */
@CrossOrigin
@RestController
@RequestMapping("/pageElement/")
@Api(tags  =  "页面元素Api")
public class PageElementController {

    @Resource
    private PageElementService pageElementService;

    @PostMapping("v1/insert")
    @ApiOperation(value = "新增")
    public Result insert(@ApiParam(hidden = true)@Login Long userId,
                         PageElement pageElement, BindingResult errors) {
        if (errors.hasErrors()){
			StringBuilder builder = new StringBuilder();
			errors.getAllErrors().forEach(i -> builder.append(i.getDefaultMessage()).append(","));
			return fail(builder.toString());
		}
        return pageElementService.insert(userId,pageElement);
    }

    @PostMapping("v1/update")
    @ApiOperation(value = "修改")
    public Result update(@ApiParam(hidden = true)@Login Long userId,
                         PageElement pageElement, BindingResult errors) {
        if (errors.hasErrors()){
			StringBuilder builder = new StringBuilder();
			errors.getAllErrors().forEach(i -> builder.append(i.getDefaultMessage()).append(","));
			return fail(builder.toString());
		}
        return pageElementService.update(userId,pageElement);
    }

    @PostMapping("v1/getById")
    @ApiOperation(value = "根据主键删除")
    public Result deleteGetById(@ApiParam(hidden = true)@Login Long userId,
                                @ApiParam(value = "主键",required = true)@RequestParam(value = "id") Long id) {
        return pageElementService.deleteGetById(userId,id);
    }

    @PostMapping("v1/findById")
    @ApiOperation(value = "根据主键查询")
    public Result findById(@ApiParam(hidden = true)@Login Long userId,
                           @ApiParam(value = "主键",required = true)@RequestParam(value = "id") Long id) {
        return pageElementService.findById(userId,id);
    }

    @PostMapping("v1/pageQuery")
    @ApiOperation(value = "条件分页查询")
    public Result pageQuery(@ApiParam(hidden = true)@Login Long userId,
                            @ApiParam(value = "第几页", required = true,example = "1") @RequestParam(value = "page") Integer page,
                            @ApiParam(value = "多少条",required = true,example = "10")@RequestParam(value = "size") Integer size,
                            @ApiParam(value = "排序字段",example = "create_time desc")@RequestParam(value = "sort",required = false) String sort,
                            PageElement pageElement) {
        return pageElementService.pageQuery(userId,page, size, sort, pageElement);
    }
}
