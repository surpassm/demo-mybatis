package com.liaoin.demo.service.user.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.liaoin.demo.common.Result;
import com.liaoin.demo.common.ResultCode;
import com.liaoin.demo.entity.user.Operations;
import com.liaoin.demo.entity.user.UserInfo;
import com.liaoin.demo.mapper.user.OperationsMapper;
import com.liaoin.demo.service.user.OperationsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.liaoin.demo.common.Result.fail;
import static com.liaoin.demo.common.Result.ok;


/**
  * @author mc
  * Create date 2019-11-28 10:47:51
  * Version 1.0
  * Description 后台功能接口实现类
  */
@Slf4j
@Service
@Transactional(rollbackFor={RuntimeException.class, Exception.class})
public class OperationsServiceImpl implements OperationsService {
    @Resource
    private OperationsMapper operationsMapper;

    @Override
    public Result insert(Long userId, Operations operations) {
        if (operations == null){
            return fail(ResultCode.PARAM_IS_BLANK.getMsg());
        }
        operationsMapper.insert(operations);
        return ok();
    }

    @Override
    public Result update(Long userId,Operations operations) {
        if (operations == null){
            return fail(ResultCode.PARAM_IS_BLANK.getMsg());
        }
        operationsMapper.updateByPrimaryKeySelective(operations);
        return ok();
    }

    @Override
    public void deleteGetById(Long id){
        operationsMapper.deleteByPrimaryKey(id);
    }


    @Override
    public Optional<Operations> findById(Long id) {
        return Optional.ofNullable(operationsMapper.selectByPrimaryKey(id));
    }

    @Override
    public Result pageQuery(Long userId,Integer page, Integer size, String sort, Operations operations) {
        page = null  == page ? 1 : page;
        size = null  == size ? 10 : size;
        if (size > 101){
            return fail("num must not be greater than 100");
        }
        if (sort != null && !"".equals(sort.trim())){
			PageHelper.startPage(page, size,sort);
		}else {
			PageHelper.startPage(page, size,"create_time desc");
		}
        Example.Builder builder = new Example.Builder(Operations.class);
        if(operations != null){
        if (operations.getId() != null){
            builder.where(WeekendSqls.<Operations>custom().andEqualTo(Operations::getId,operations.getId()));
        }
        if (operations.getApiUrl() != null && !"".equals(operations.getApiUrl().trim())){
            builder.where(WeekendSqls.<Operations>custom().andLike(Operations::getApiUrl,"%"+operations.getApiUrl()+"%"));
        }
        if (operations.getCreateTime() != null){
            builder.where(WeekendSqls.<Operations>custom().andEqualTo(Operations::getCreateTime,operations.getCreateTime()));
        }
        if (operations.getDescribes() != null && !"".equals(operations.getDescribes().trim())){
            builder.where(WeekendSqls.<Operations>custom().andLike(Operations::getDescribes,"%"+operations.getDescribes()+"%"));
        }
        if (operations.getMenuIndex() != null){
            builder.where(WeekendSqls.<Operations>custom().andEqualTo(Operations::getMenuIndex,operations.getMenuIndex()));
        }
        if (operations.getName() != null && !"".equals(operations.getName().trim())){
            builder.where(WeekendSqls.<Operations>custom().andLike(Operations::getName,"%"+operations.getName()+"%"));
        }
        if (operations.getType() != null){
            builder.where(WeekendSqls.<Operations>custom().andEqualTo(Operations::getType,operations.getType()));
        }
        if (operations.getParentId() != null){
            builder.where(WeekendSqls.<Operations>custom().andEqualTo(Operations::getParentId,operations.getParentId()));
        }
        }
        Page<Operations> all = (Page<Operations>) operationsMapper.selectByExample(builder.build());
        Map<String,Object> result = new HashMap<>(3);
        result.put("total",all.getTotal());
        result.put("rows",all.getResult());
        return ok(result);
    }

    @Override
    public Boolean selectCountParentId(Long parentId) {
        int i = operationsMapper.selectCount(Operations.builder().parentId(parentId).build());
        return i > 0;
    }
}

