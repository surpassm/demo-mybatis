package com.liaoin.demo.domain.user;


import com.liaoin.demo.annotation.Convert;
import com.liaoin.demo.entity.user.RegionCities;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
* @author mc
* Create date 2019-12-12 21:02:27
* Version 1.0
* Description 城市信息表数据流
*/
@Getter
@Setter
@ApiModel(value = "城市信息表")
public class RegionCitiesDto implements Serializable {


    public RegionCities convertTo(){
        ConvertImpl impl = new ConvertImpl();
        return impl.doForward(this);
    }

    public RegionCitiesDto convertFor(RegionCities regionCities){
        ConvertImpl impl = new ConvertImpl();
        return impl.doBackward(regionCities);
    }

    private static class ConvertImpl implements Convert<RegionCitiesDto, RegionCities> {
        @Override
        public RegionCities doForward(RegionCitiesDto dto) {
            RegionCities regionCities = new RegionCities();
            BeanUtils.copyProperties(dto,regionCities);
            return regionCities;
        }
        @Override
        public RegionCitiesDto doBackward(RegionCities regionCities) {
            RegionCitiesDto dto = new RegionCitiesDto();
            BeanUtils.copyProperties(regionCities, dto);
            return dto;
        }
    }
}
