package com.liaoin.demo.domain.user;


import com.liaoin.demo.annotation.Convert;
import com.liaoin.demo.entity.user.RegionProvinces;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
* @author mc
* Create date 2019-12-12 21:02:27
* Version 1.0
* Description 省数据流
*/
@Getter
@Setter
@ApiModel(value = "省")
public class RegionProvincesDto implements Serializable {


    public RegionProvinces convertTo(){
        ConvertImpl impl = new ConvertImpl();
        return impl.doForward(this);
    }

    public RegionProvincesDto convertFor(RegionProvinces regionProvinces){
        ConvertImpl impl = new ConvertImpl();
        return impl.doBackward(regionProvinces);
    }

    private static class ConvertImpl implements Convert<RegionProvincesDto, RegionProvinces> {
        @Override
        public RegionProvinces doForward(RegionProvincesDto dto) {
            RegionProvinces regionProvinces = new RegionProvinces();
            BeanUtils.copyProperties(dto,regionProvinces);
            return regionProvinces;
        }
        @Override
        public RegionProvincesDto doBackward(RegionProvinces regionProvinces) {
            RegionProvincesDto dto = new RegionProvincesDto();
            BeanUtils.copyProperties(regionProvinces, dto);
            return dto;
        }
    }
}
