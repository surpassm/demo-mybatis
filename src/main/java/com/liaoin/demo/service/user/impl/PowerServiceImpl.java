package com.liaoin.demo.service.user.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.surpassm.common.jackson.Result;
import com.github.surpassm.common.jackson.ResultCode;
import com.liaoin.demo.entity.user.Power;
import com.liaoin.demo.entity.user.UserInfo;
import com.liaoin.demo.mapper.user.PowerMapper;
import com.liaoin.demo.security.BeanConfig;
import com.liaoin.demo.service.user.PowerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import javax.annotation.Resource;

import static com.github.surpassm.common.jackson.Result.fail;
import static com.github.surpassm.common.jackson.Result.ok;


/**
  * @author mc
  * Create date 2019-11-28 10:47:51
  * Version 1.0
  * Description 权限表实现类
  */
@Slf4j
@Service
@Transactional(rollbackFor={RuntimeException.class, Exception.class})
public class PowerServiceImpl implements PowerService {
    @Resource
    private PowerMapper powerMapper;
    @Resource
	private BeanConfig beanConfig;

    @Override
    public Result insert(String token,Power power) {
        if (power == null){
            return fail(ResultCode.PARAM_IS_BLANK.getMsg());
        }
        UserInfo loginUser = beanConfig.getAccessToken(token);
        powerMapper.insert(power);
        return ok();
    }

    @Override
    public Result update(String token,Power power) {
        if (power == null){
            return fail(ResultCode.PARAM_IS_BLANK.getMsg());
        }
        UserInfo loginUser = beanConfig.getAccessToken(token);
        powerMapper.updateByPrimaryKeySelective(power);
        return ok();
    }

    @Override
    public Result deleteGetById(String token,Long id){
        if (id == null){
            return fail(ResultCode.PARAM_IS_BLANK.getMsg());
        }
        Power power = powerMapper.selectByPrimaryKey(id);
        if(power == null){
            return fail(ResultCode.RESULE_DATA_NONE.getMsg());
        }
        UserInfo loginUser = beanConfig.getAccessToken(token);
        powerMapper.updateByPrimaryKeySelective(power);
        return ok();
    }


    @Override
    public Result findById(String token,Long id) {
        if (id == null){
            return fail(ResultCode.PARAM_IS_BLANK.getMsg());
        }
		Power power = powerMapper.selectByPrimaryKey(id);
        if (power == null){
			return fail(ResultCode.RESULE_DATA_NONE.getMsg());
		}
        return ok(power);

    }

    @Override
    public Result pageQuery(String token,Integer page, Integer size, String sort, Power power) {
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
        Example.Builder builder = new Example.Builder(Power.class);
        builder.where(WeekendSqls.<Power>custom().andEqualTo(Power::getIsDelete, 0));
        if(power != null){
        if (power.getId() != null){
            builder.where(WeekendSqls.<Power>custom().andEqualTo(Power::getId,power.getId()));
        }
        if (power.getCreateTime() != null){
            builder.where(WeekendSqls.<Power>custom().andEqualTo(Power::getCreateTime,power.getCreateTime()));
        }
        if (power.getIsDelete() != null){
            builder.where(WeekendSqls.<Power>custom().andEqualTo(Power::getIsDelete,power.getIsDelete()));
        }
        if (power.getName() != null && !"".equals(power.getName().trim())){
            builder.where(WeekendSqls.<Power>custom().andLike(Power::getName,"%"+power.getName()+"%"));
        }
        if (power.getCreateUserId() != null){
            builder.where(WeekendSqls.<Power>custom().andEqualTo(Power::getCreateUserId,power.getCreateUserId()));
        }
        }
        Page<Power> all = (Page<Power>) powerMapper.selectByExample(builder.build());
        return ok(all.toPageInfo());
    }
}

