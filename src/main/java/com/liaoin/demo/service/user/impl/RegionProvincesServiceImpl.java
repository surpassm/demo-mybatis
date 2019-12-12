package com.liaoin.demo.service.user.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.liaoin.demo.common.Result;
import com.liaoin.demo.common.ResultCode;
import com.liaoin.demo.entity.user.RegionProvinces;
import com.liaoin.demo.entity.user.UserInfo;
import com.liaoin.demo.mapper.user.RegionProvincesMapper;
import com.liaoin.demo.service.user.RegionProvincesService;
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
  * Description 省实现类
  */
@Slf4j
@Service
@Transactional(rollbackFor={RuntimeException.class, Exception.class})
public class RegionProvincesServiceImpl implements RegionProvincesService {
    @Resource
    private RegionProvincesMapper regionProvincesMapper;

    @Override
    public Result insert(Long userId, RegionProvinces regionProvinces) {
        if (regionProvinces == null){
            return fail(ResultCode.PARAM_IS_BLANK.getMsg());
        }
        regionProvincesMapper.insert(regionProvinces);
        return ok();
    }

    @Override
    public Result update(Long userId,RegionProvinces regionProvinces) {
        if (regionProvinces == null){
            return fail(ResultCode.PARAM_IS_BLANK.getMsg());
        }
        regionProvincesMapper.updateByPrimaryKeySelective(regionProvinces);
        return ok();
    }

    @Override
    public Result deleteGetById(Long userId,Long id){
        if (id == null){
            return fail(ResultCode.PARAM_IS_BLANK.getMsg());
        }
        RegionProvinces regionProvinces = regionProvincesMapper.selectByPrimaryKey(id);
        if(regionProvinces == null){
            return fail(ResultCode.RESULE_DATA_NONE.getMsg());
        }
        regionProvincesMapper.updateByPrimaryKeySelective(regionProvinces);
        return ok();
    }


    @Override
    public Result findById(Long userId,Long id) {
        if (id == null){
            return fail(ResultCode.PARAM_IS_BLANK.getMsg());
        }
		RegionProvinces regionProvinces = regionProvincesMapper.selectByPrimaryKey(id);
        if (regionProvinces == null){
			return fail(ResultCode.RESULE_DATA_NONE.getMsg());
		}
        return ok(regionProvinces);

    }

    @Override
    public Result pageQuery(Long userId,Integer page, Integer size, String sort, RegionProvinces regionProvinces) {
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
        Example.Builder builder = new Example.Builder(RegionProvinces.class);
        if(regionProvinces != null){
        if (regionProvinces.getId() != null){
            builder.where(WeekendSqls.<RegionProvinces>custom().andEqualTo(RegionProvinces::getId,regionProvinces.getId()));
        }
        if (regionProvinces.getCode() != null && !"".equals(regionProvinces.getCode().trim())){
            builder.where(WeekendSqls.<RegionProvinces>custom().andLike(RegionProvinces::getCode,"%"+regionProvinces.getCode()+"%"));
        }
        if (regionProvinces.getName() != null && !"".equals(regionProvinces.getName().trim())){
            builder.where(WeekendSqls.<RegionProvinces>custom().andLike(RegionProvinces::getName,"%"+regionProvinces.getName()+"%"));
        }
        }
        Page<RegionProvinces> all = (Page<RegionProvinces>) regionProvincesMapper.selectByExample(builder.build());
        return ok(all.toPageInfo());
    }
}

