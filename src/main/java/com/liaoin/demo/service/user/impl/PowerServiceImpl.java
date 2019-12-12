package com.liaoin.demo.service.user.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.liaoin.demo.common.Result;
import com.liaoin.demo.common.ResultCode;
import com.liaoin.demo.entity.user.Power;
import com.liaoin.demo.entity.user.UserInfo;
import com.liaoin.demo.mapper.user.PowerMapper;
import com.liaoin.demo.service.user.PowerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import javax.annotation.Resource;

import java.time.LocalDateTime;
import java.util.Collections;

import static com.liaoin.demo.common.Result.fail;
import static com.liaoin.demo.common.Result.ok;


/**
 * @author mc
 * Create date 2019-11-28 10:47:51
 * Version 1.0
 * Description 权限表实现类
 */
@Slf4j
@Service
@Transactional(rollbackFor = {RuntimeException.class, Exception.class})
public class PowerServiceImpl implements PowerService {
    @Resource
    private PowerMapper powerMapper;

    @Override
    public Result insert(Long userId, Power power) {
        if (power == null) {
            return fail(ResultCode.PARAM_IS_BLANK.getMsg());
        }
        //效验当前权限名称是否重复
        int i = powerMapper.selectCount(Power.builder().name(power.getName()).build());
        if (i > 0) {
            return fail(ResultCode.DATA_ALREADY_EXISTED.getMsg());
        }
        power.setCreateTime(LocalDateTime.now());
        power.setCreateUserId(userId);
        powerMapper.insert(power);
        return ok();
    }

    @Override
    public Result update(Long userId, Power power) {
        if (power == null) {
            return fail(ResultCode.PARAM_IS_BLANK.getMsg());
        }
        //效验当前权限名称是否重复
        Example.Builder builder = new Example.Builder(Power.class);
        builder.where(WeekendSqls.<Power>custom().andEqualTo(Power::getName, power.getName()));
        builder.where(WeekendSqls.<Power>custom().andNotIn(Power::getId, Collections.singletonList(power.getId())));
        int i = powerMapper.selectCountByExample(builder.build());
        if (i > 0) {
            return fail(ResultCode.DATA_ALREADY_EXISTED.getMsg());
        }
        power.setUpdateTime(LocalDateTime.now());
        power.setCreateUserId(userId);
        powerMapper.updateByPrimaryKeySelective(power);
        return ok();
    }

    @Override
    public Result deleteGetById(Long userId, Long id) {
        if (id == null) {
            return fail(ResultCode.PARAM_IS_BLANK.getMsg());
        }
        Power power = powerMapper.selectByPrimaryKey(id);
        if (power == null) {
            return fail(ResultCode.RESULE_DATA_NONE.getMsg());
        }
        powerMapper.delete(power);
        return ok();
    }


    @Override
    public Result findById(Long userId, Long id) {
        if (id == null) {
            return fail(ResultCode.PARAM_IS_BLANK.getMsg());
        }
        Power power = powerMapper.selectByPrimaryKey(id);
        if (power == null) {
            return fail(ResultCode.RESULE_DATA_NONE.getMsg());
        }
        return ok(power);

    }

    @Override
    public Result pageQuery(Long userId, Integer page, Integer size, String sort, Power power) {
        page = null == page ? 1 : page;
        size = null == size ? 10 : size;
        if (size > 101) {
            return fail("num must not be greater than 100");
        }
        if (sort != null && !"".equals(sort.trim())) {
            PageHelper.startPage(page, size, sort);
        } else {
            PageHelper.startPage(page, size, "create_time desc");
        }
        Example.Builder builder = new Example.Builder(Power.class);
        if (power != null) {
            if (power.getId() != null) {
                builder.where(WeekendSqls.<Power>custom().andEqualTo(Power::getId, power.getId()));
            }
            if (power.getCreateTime() != null) {
                builder.where(WeekendSqls.<Power>custom().andEqualTo(Power::getCreateTime, power.getCreateTime()));
            }
            if (power.getName() != null && !"".equals(power.getName().trim())) {
                builder.where(WeekendSqls.<Power>custom().andLike(Power::getName, "%" + power.getName() + "%"));
            }
            if (power.getCreateUserId() != null) {
                builder.where(WeekendSqls.<Power>custom().andEqualTo(Power::getCreateUserId, power.getCreateUserId()));
            }
        }
        Page<Power> all = (Page<Power>) powerMapper.selectByExample(builder.build());
        return ok(all.toPageInfo());
    }

    @Override
    public Result getPowerMenu(Long userId, Long id) {
        return null;
    }
}

