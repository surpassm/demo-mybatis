package com.liaoin.demo.mapper.user;

import com.liaoin.demo.entity.user.RegionCities;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
  * @author mc
  * Create date 2019-11-28 10:47:51
  * Version 1.0
  * Description 城市信息表持久层
  */
public interface RegionCitiesMapper extends Mapper<RegionCities>, MySqlMapper<RegionCities> {


}