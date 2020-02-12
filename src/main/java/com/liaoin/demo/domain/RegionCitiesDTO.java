package com.liaoin.demo.domain;


import com.liaoin.demo.annotation.Convert;
import com.liaoin.demo.entity.RegionCities;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;


/**
* @author mc
* Create date 2020-02-10 10:15:20
* Version 1.0
* Description 城市信息表数据流
*/
@Getter
@Setter
@ApiModel(value = "城市信息表")
public class RegionCitiesDTO implements Serializable {














    public RegionCities convertTo(){
        ConvertImpl impl = new ConvertImpl();
        return impl.doForward(this);
    }

    public RegionCitiesDTO convertFor(RegionCities regionCities){
        ConvertImpl impl = new ConvertImpl();
        return impl.doBackward(regionCities,this);
    }

    private static class ConvertImpl implements Convert<RegionCitiesDTO, RegionCities> {
        @Override
        public RegionCities doForward(RegionCitiesDTO dto) {
            RegionCities regionCities = new RegionCities();
            BeanUtils.copyProperties(dto,regionCities);
            return regionCities;
        }
        @Override
        public RegionCitiesDTO doBackward(RegionCities regionCities) {
            RegionCitiesDTO dto = new RegionCitiesDTO();
            BeanUtils.copyProperties(regionCities, dto);
            return dto;
        }
        public RegionCitiesDTO doBackward(RegionCities regionCities, RegionCitiesDTO dto) {
            BeanUtils.copyProperties(regionCities, dto);
            return dto;
        }
    }
}