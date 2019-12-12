package com.liaoin.demo.service.user.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.liaoin.demo.common.Result;
import com.liaoin.demo.common.ResultCode;
import com.liaoin.demo.entity.user.RegionCities;
import com.liaoin.demo.entity.user.UserInfo;
import com.liaoin.demo.mapper.user.RegionCitiesMapper;
import com.liaoin.demo.service.user.RegionCitiesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import javax.annotation.Resource;

import static com.liaoin.demo.common.Result.fail;
import static com.liaoin.demo.common.Result.ok;


/**
  * @author mc
  * Create date 2019-11-28 10:47:51
  * Version 1.0
  * Description 城市信息表实现类
  */
@Slf4j
@Service
@Transactional(rollbackFor={RuntimeException.class, Exception.class})
public class RegionCitiesServiceImpl implements RegionCitiesService {
    @Resource
    private RegionCitiesMapper regionCitiesMapper;

    @Override
    public Result insert(Long userId, RegionCities regionCities) {
        if (regionCities == null){
            return fail(ResultCode.PARAM_IS_BLANK.getMsg());
        }
        regionCitiesMapper.insert(regionCities);
        return ok();
    }

    @Override
    public Result update(Long userId,RegionCities regionCities) {
        if (regionCities == null){
            return fail(ResultCode.PARAM_IS_BLANK.getMsg());
        }
        regionCitiesMapper.updateByPrimaryKeySelective(regionCities);
        return ok();
    }

    @Override
    public Result deleteGetById(Long userId,Long id){
        if (id == null){
            return fail(ResultCode.PARAM_IS_BLANK.getMsg());
        }
        RegionCities regionCities = regionCitiesMapper.selectByPrimaryKey(id);
        if(regionCities == null){
            return fail(ResultCode.RESULE_DATA_NONE.getMsg());
        }
        regionCitiesMapper.updateByPrimaryKeySelective(regionCities);
        return ok();
    }


    @Override
    public Result findById(Long userId,Long id) {
        if (id == null){
            return fail(ResultCode.PARAM_IS_BLANK.getMsg());
        }
		RegionCities regionCities = regionCitiesMapper.selectByPrimaryKey(id);
        if (regionCities == null){
			return fail(ResultCode.RESULE_DATA_NONE.getMsg());
		}
        return ok(regionCities);

    }

    @Override
    public Result pageQuery(Long userId,Integer page, Integer size, String sort, RegionCities regionCities) {
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
        Example.Builder builder = new Example.Builder(RegionCities.class);
        if(regionCities != null){
        if (regionCities.getId() != null){
            builder.where(WeekendSqls.<RegionCities>custom().andEqualTo(RegionCities::getId,regionCities.getId()));
        }
        if (regionCities.getCode() != null && !"".equals(regionCities.getCode().trim())){
            builder.where(WeekendSqls.<RegionCities>custom().andLike(RegionCities::getCode,"%"+regionCities.getCode()+"%"));
        }
        if (regionCities.getName() != null && !"".equals(regionCities.getName().trim())){
            builder.where(WeekendSqls.<RegionCities>custom().andLike(RegionCities::getName,"%"+regionCities.getName()+"%"));
        }
        if (regionCities.getProvincesCode() != null && !"".equals(regionCities.getProvincesCode().trim())){
            builder.where(WeekendSqls.<RegionCities>custom().andLike(RegionCities::getProvincesCode,"%"+regionCities.getProvincesCode()+"%"));
        }
        }
        Page<RegionCities> all = (Page<RegionCities>) regionCitiesMapper.selectByExample(builder.build());
        return ok(all.toPageInfo());
    }
}

