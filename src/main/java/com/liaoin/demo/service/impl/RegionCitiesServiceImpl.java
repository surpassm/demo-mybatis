package com.liaoin.demo.service.impl;

import com.github.pagehelper.Page;
import com.liaoin.demo.common.R;
import com.liaoin.demo.common.ResultCode;
import com.liaoin.demo.domain.RegionCitiesVO;
import com.liaoin.demo.entity.RegionCities;
import com.liaoin.demo.exception.CustomException;
import com.liaoin.demo.mapper.RegionCitiesMapper;
import com.liaoin.demo.service.RegionCitiesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Optional;

import static com.liaoin.demo.common.R.ok;


/**
  * @author mc
  * Create date 2020-02-10 10:15:20
  * Version 1.0
  * Description 城市信息表实现类
  */
@Slf4j
@Service
@Transactional(rollbackFor={RuntimeException.class, Exception.class})
public class RegionCitiesServiceImpl extends BaseServiceImpl implements RegionCitiesService {
    @Resource
    private RegionCitiesMapper regionCitiesMapper;

    @Override
    public RegionCities insert(RegionCities regionCities) {
        regionCitiesMapper.insert(regionCities);
        return regionCities;
    }

    @Override
    public void update(RegionCities regionCities) {
        regionCitiesMapper.updateByPrimaryKeySelective(regionCities);
    }

    @Override
    public void deleteById(Long id){
        Optional<RegionCities> byId = this.findById(id);
        if (!byId.isPresent()) {
            throw new CustomException(ResultCode.ERROR.getCode(), ResultCode.RESULT_DATA_NONE.getMsg());
        }
        RegionCities regionCities = byId.get();
        this.update(regionCities);
    }


    @Override
    public Optional<RegionCities> findById(Long id) {
        return Optional.ofNullable(regionCitiesMapper.selectByPrimaryKey(id));

    }

    @Override
    public R pageQuery(Integer page, Integer size, String sort, RegionCitiesVO regionCitiesVO) {
		super.pageQuery(page,size,sort);
        Example.Builder builder = new Example.Builder(RegionCities.class);
        if(regionCitiesVO != null){
        }
        Page<RegionCities> all = (Page<RegionCities>) regionCitiesMapper.selectByExample(builder.build());
        return ok(all.getTotal(),all.getResult());
    }

    @Override
    public RegionCities insertVO(RegionCitiesVO vo) {
        return null;
    }

    @Override
    public RegionCities updateVO(RegionCitiesVO vo) {
        return null;
    }
}

