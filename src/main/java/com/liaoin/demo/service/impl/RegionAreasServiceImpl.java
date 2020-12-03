package com.liaoin.demo.service.impl;

import com.github.pagehelper.Page;
import com.liaoin.demo.common.R;
import com.liaoin.demo.common.ResultCode;
import com.liaoin.demo.domain.RegionAreasVO;
import com.liaoin.demo.entity.RegionAreas;
import com.liaoin.demo.exception.CustomException;
import com.liaoin.demo.mapper.RegionAreasMapper;
import com.liaoin.demo.service.RegionAreasService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static com.liaoin.demo.common.R.ok;


/**
  * @author mc
  * Create date 2020-02-10 10:15:20
  * Version 1.0
  * Description 区县信息表实现类
  */
@Slf4j
@Service
@Transactional(rollbackFor={RuntimeException.class, Exception.class})
public class RegionAreasServiceImpl extends BaseServiceImpl implements RegionAreasService {
    @Resource
    private RegionAreasMapper regionAreasMapper;

    @Override
    public RegionAreas insert(RegionAreas regionAreas) {
        regionAreasMapper.insert(regionAreas);
        return regionAreas;
    }

    @Override
    public void update(RegionAreas regionAreas) {
        regionAreasMapper.updateByPrimaryKeySelective(regionAreas);
    }

    @Override
    public void deleteById(Long id){
        Optional<RegionAreas> byId = this.findById(id);
        if (!byId.isPresent()) {
            throw new CustomException(ResultCode.ERROR.getCode(), ResultCode.RESULT_DATA_NONE.getMsg());
        }
        RegionAreas regionAreas = byId.get();
        this.update(regionAreas);
    }


    @Override
    public Optional<RegionAreas> findById(Long id) {
        return Optional.ofNullable(regionAreasMapper.selectByPrimaryKey(id));

    }

    @Override
    public R pageQuery(Integer page, Integer size, String sort, RegionAreasVO regionAreasVO) {
		super.pageQuery(page,size,sort);
        Example.Builder builder = new Example.Builder(RegionAreas.class);
        if(regionAreasVO != null){
        }
        Page<RegionAreas> all = (Page<RegionAreas>) regionAreasMapper.selectByExample(builder.build());
        return ok(all.getTotal(),all.getResult());
    }

    @Override
    public RegionAreas insertVO(RegionAreasVO vo) {
        return null;
    }

    @Override
    public RegionAreas updateVO(RegionAreasVO vo) {
        return null;
    }

    @Override
    public R pageQueryByCityCode(String cityCode) {
        Example.Builder builder = new Example.Builder(RegionAreas.class);
        if(cityCode != null){
            builder.where(WeekendSqls.<RegionAreas>custom().andEqualTo(RegionAreas::getCitiesCode, cityCode));
        }
        List<RegionAreas> regionAreas = regionAreasMapper.selectByExample(builder.build());
        return ok(regionAreas);
    }
}

