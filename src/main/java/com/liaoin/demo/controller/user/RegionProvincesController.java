package com.liaoin.demo.controller.user;

import com.github.surpassm.common.constant.Constant;
import com.github.surpassm.common.jackson.Result;
import com.github.surpassm.common.service.InsertView;
import com.github.surpassm.common.service.UpdateView;
import com.github.surpassm.config.annotation.AuthorizationToken;
import com.liaoin.demo.entity.user.RegionProvinces;
import com.liaoin.demo.service.user.RegionProvincesService;
import io.swagger.annotations.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.github.surpassm.common.jackson.Result.fail;

/**
  * @author mc
  * Create date 2019-11-28 10:47:51
  * Version 1.0
  * Description 省控制层
  */
@CrossOrigin
@RestController
@RequestMapping("/regionProvinces/")
@Api(tags  =  "省Api")
public class RegionProvincesController {

    @Resource
    private RegionProvincesService regionProvincesService;

    @PostMapping("v1/insert")
    @ApiOperation(value = "新增")
    @ApiResponses({
            @ApiResponse(code=Constant.SUCCESS_CODE,message=Constant.SUCCESS_MSG),
            @ApiResponse(code=Constant.FAIL_SESSION_CODE,message=Constant.FAIL_SESSION_MSG),
            @ApiResponse(code=Constant.FAIL_CODE,message=Constant.FAIL_MSG,response=Result.class)})
    public Result insert(@ApiParam(hidden = true)@AuthorizationToken String token,
                         @Validated(InsertView.class) RegionProvinces regionProvinces, BindingResult errors) {
        if (errors.hasErrors()){
			StringBuilder builder = new StringBuilder();
			errors.getAllErrors().forEach(i -> builder.append(i.getDefaultMessage()).append(","));
			return fail(builder.toString());
		}
        return regionProvincesService.insert(token,regionProvinces);
    }

    @PostMapping("v1/update")
    @ApiOperation(value = "修改")
    @ApiResponses({
            @ApiResponse(code=Constant.SUCCESS_CODE,message=Constant.SUCCESS_MSG),
            @ApiResponse(code=Constant.FAIL_SESSION_CODE,message=Constant.FAIL_SESSION_MSG),
            @ApiResponse(code=Constant.FAIL_CODE,message=Constant.FAIL_MSG,response=Result.class)})
    public Result update(@ApiParam(hidden = true)@AuthorizationToken String token,
                         @Validated(UpdateView.class) RegionProvinces regionProvinces, BindingResult errors) {
        if (errors.hasErrors()){
			StringBuilder builder = new StringBuilder();
			errors.getAllErrors().forEach(i -> builder.append(i.getDefaultMessage()).append(","));
			return fail(builder.toString());
		}
        return regionProvincesService.update(token,regionProvinces);
    }

    @PostMapping("v1/getById")
    @ApiOperation(value = "根据主键删除")
    @ApiResponses({
            @ApiResponse(code=Constant.SUCCESS_CODE,message=Constant.SUCCESS_MSG),
            @ApiResponse(code=Constant.FAIL_SESSION_CODE,message=Constant.FAIL_SESSION_MSG),
            @ApiResponse(code=Constant.FAIL_CODE,message=Constant.FAIL_MSG,response=Result.class)})
    public Result deleteGetById(@ApiParam(hidden = true)@AuthorizationToken String token,
                                @ApiParam(value = "主键",required = true)@RequestParam(value = "id") Long id) {
        return regionProvincesService.deleteGetById(token,id);
    }

    @PostMapping("v1/findById")
    @ApiOperation(value = "根据主键查询")
    @ApiResponses({
            @ApiResponse(code=Constant.FAIL_SESSION_CODE,message=Constant.FAIL_SESSION_MSG),
            @ApiResponse(code=Constant.SUCCESS_CODE,message=Constant.SUCCESS_MSG,response=RegionProvinces.class),
            @ApiResponse(code=Constant.FAIL_CODE,message=Constant.FAIL_MSG,response=Result.class)})
    public Result findById(@ApiParam(hidden = true)@AuthorizationToken String token,
                           @ApiParam(value = "主键",required = true)@RequestParam(value = "id") Long id) {
        return regionProvincesService.findById(token,id);
    }

    @PostMapping("v1/pageQuery")
    @ApiOperation(value = "条件分页查询")
    @ApiResponses({@ApiResponse(code=Constant.SUCCESS_CODE,message=Constant.SUCCESS_MSG,response=RegionProvinces.class),
                   @ApiResponse(code=Constant.FAIL_SESSION_CODE,message=Constant.FAIL_SESSION_MSG)})
    public Result pageQuery(@ApiParam(hidden = true)@AuthorizationToken String token,
                            @ApiParam(value = "第几页", required = true,example = "1") @RequestParam(value = "page") Integer page,
                            @ApiParam(value = "多少条",required = true,example = "10")@RequestParam(value = "size") Integer size,
                            @ApiParam(value = "排序字段",example = "create_time desc")@RequestParam(value = "sort",required = false) String sort,
                            RegionProvinces regionProvinces) {
        return regionProvincesService.pageQuery(token,page, size, sort, regionProvinces);
    }
}