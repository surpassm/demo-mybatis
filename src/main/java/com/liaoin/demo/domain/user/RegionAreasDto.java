package com.liaoin.demo.domain.user;


import com.liaoin.demo.annotation.Convert;
import com.liaoin.demo.entity.user.RegionAreas;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
* @author mc
* Create date 2019-12-12 21:02:27
* Version 1.0
* Description 区县信息表数据流
*/
@Getter
@Setter
@ApiModel(value = "区县信息表")
public class RegionAreasDto implements Serializable {


    public RegionAreas convertTo(){
        ConvertImpl impl = new ConvertImpl();
        return impl.doForward(this);
    }

    public RegionAreasDto convertFor(RegionAreas regionAreas){
        ConvertImpl impl = new ConvertImpl();
        return impl.doBackward(regionAreas);
    }

    private static class ConvertImpl implements Convert<RegionAreasDto, RegionAreas> {
        @Override
        public RegionAreas doForward(RegionAreasDto dto) {
            RegionAreas regionAreas = new RegionAreas();
            BeanUtils.copyProperties(dto,regionAreas);
            return regionAreas;
        }
        @Override
        public RegionAreasDto doBackward(RegionAreas regionAreas) {
            RegionAreasDto dto = new RegionAreasDto();
            BeanUtils.copyProperties(regionAreas, dto);
            return dto;
        }
    }
}
