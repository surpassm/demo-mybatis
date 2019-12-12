package com.liaoin.demo.service.user.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.liaoin.demo.common.Result;
import com.liaoin.demo.common.ResultCode;
import com.liaoin.demo.entity.user.RegionAreas;
import com.liaoin.demo.entity.user.UserInfo;
import com.liaoin.demo.mapper.user.RegionAreasMapper;
import com.liaoin.demo.service.user.RegionAreasService;
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
  * Description 区县信息表实现类
  */
@Slf4j
@Service
@Transactional(rollbackFor={RuntimeException.class, Exception.class})
public class RegionAreasServiceImpl implements RegionAreasService {
    @Resource
    private RegionAreasMapper regionAreasMapper;

    @Override
    public Result insert(Long userId, RegionAreas regionAreas) {
        if (regionAreas == null){
            return fail(ResultCode.PARAM_IS_BLANK.getMsg());
        }
        regionAreasMapper.insert(regionAreas);
        return ok();
    }

    @Override
    public Result update(Long userId,RegionAreas regionAreas) {
        if (regionAreas == null){
            return fail(ResultCode.PARAM_IS_BLANK.getMsg());
        }
        regionAreasMapper.updateByPrimaryKeySelective(regionAreas);
        return ok();
    }

    @Override
    public Result deleteGetById(Long userId,Long id){
        if (id == null){
            return fail(ResultCode.PARAM_IS_BLANK.getMsg());
        }
        RegionAreas regionAreas = regionAreasMapper.selectByPrimaryKey(id);
        if(regionAreas == null){
            return fail(ResultCode.RESULE_DATA_NONE.getMsg());
        }
        regionAreasMapper.updateByPrimaryKeySelective(regionAreas);
        return ok();
    }


    @Override
    public Result findById(Long userId,Long id) {
        if (id == null){
            return fail(ResultCode.PARAM_IS_BLANK.getMsg());
        }
		RegionAreas regionAreas = regionAreasMapper.selectByPrimaryKey(id);
        if (regionAreas == null){
			return fail(ResultCode.RESULE_DATA_NONE.getMsg());
		}
        return ok(regionAreas);

    }

    @Override
    public Result pageQuery(Long userId,Integer page, Integer size, String sort, RegionAreas regionAreas) {
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
        Example.Builder builder = new Example.Builder(RegionAreas.class);
        if(regionAreas != null){
        if (regionAreas.getId() != null){
            builder.where(WeekendSqls.<RegionAreas>custom().andEqualTo(RegionAreas::getId,regionAreas.getId()));
        }
        if (regionAreas.getCitiesCode() != null && !"".equals(regionAreas.getCitiesCode().trim())){
            builder.where(WeekendSqls.<RegionAreas>custom().andLike(RegionAreas::getCitiesCode,"%"+regionAreas.getCitiesCode()+"%"));
        }
        if (regionAreas.getCode() != null && !"".equals(regionAreas.getCode().trim())){
            builder.where(WeekendSqls.<RegionAreas>custom().andLike(RegionAreas::getCode,"%"+regionAreas.getCode()+"%"));
        }
        if (regionAreas.getName() != null && !"".equals(regionAreas.getName().trim())){
            builder.where(WeekendSqls.<RegionAreas>custom().andLike(RegionAreas::getName,"%"+regionAreas.getName()+"%"));
        }
        }
        Page<RegionAreas> all = (Page<RegionAreas>) regionAreasMapper.selectByExample(builder.build());
        return ok(all.toPageInfo());
    }
}

