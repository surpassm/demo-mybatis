package com.liaoin.demo.domain;


import com.liaoin.demo.annotation.Convert;
import com.liaoin.demo.entity.RegionProvinces;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;


/**
* @author mc
* Create date 2020-02-10 10:15:20
* Version 1.0
* Description 省数据流
*/
@Getter
@Setter
@ApiModel(value = "省")
public class RegionProvincesDTO implements Serializable {














    public RegionProvinces convertTo(){
        ConvertImpl impl = new ConvertImpl();
        return impl.doForward(this);
    }

    public RegionProvincesDTO convertFor(RegionProvinces regionProvinces){
        ConvertImpl impl = new ConvertImpl();
        return impl.doBackward(regionProvinces,this);
    }

    private static class ConvertImpl implements Convert<RegionProvincesDTO, RegionProvinces> {
        @Override
        public RegionProvinces doForward(RegionProvincesDTO dto) {
            RegionProvinces regionProvinces = new RegionProvinces();
            BeanUtils.copyProperties(dto,regionProvinces);
            return regionProvinces;
        }
        @Override
        public RegionProvincesDTO doBackward(RegionProvinces regionProvinces) {
            RegionProvincesDTO dto = new RegionProvincesDTO();
            BeanUtils.copyProperties(regionProvinces, dto);
            return dto;
        }
        public RegionProvincesDTO doBackward(RegionProvinces regionProvinces, RegionProvincesDTO dto) {
            BeanUtils.copyProperties(regionProvinces, dto);
            return dto;
        }
    }
}