package com.liaoin.demo.domain;


import com.liaoin.demo.annotation.Convert;
import com.liaoin.demo.entity.RegionCities;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;


/**
* @author mc
* Create date 2020-02-10 10:15:20
* Version 1.0
* Description 城市信息表VO数据流
*/

@Data
@ApiModel(value = "城市信息表VO")
public class RegionCitiesVO implements Serializable {








    public RegionCities convertTo(){
        ConvertImpl impl = new ConvertImpl();
        return impl.doForward(this);
    }

    public RegionCitiesVO convertFor(RegionCities regionCities){
        ConvertImpl impl = new ConvertImpl();
        return impl.doBackward(regionCities,this);
    }

    private static class ConvertImpl implements Convert<RegionCitiesVO, RegionCities> {
        @Override
        public RegionCities doForward(RegionCitiesVO vo) {
            RegionCities regionCities = new RegionCities();
            BeanUtils.copyProperties(vo,regionCities);
            return regionCities;
        }
        @Override
        public RegionCitiesVO doBackward(RegionCities regionCities) {
                RegionCitiesVO vo = new RegionCitiesVO();
                BeanUtils.copyProperties(regionCities, vo);
                return vo;
        }
        public RegionCitiesVO doBackward(RegionCities regionCities, RegionCitiesVO vo) {
                BeanUtils.copyProperties(regionCities, vo);
                return vo;
        }
    }




}
