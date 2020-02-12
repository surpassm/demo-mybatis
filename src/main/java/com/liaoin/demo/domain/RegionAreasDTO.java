package com.liaoin.demo.domain;


import com.liaoin.demo.annotation.Convert;
import com.liaoin.demo.entity.RegionAreas;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;


/**
* @author mc
* Create date 2020-02-10 10:15:20
* Version 1.0
* Description 区县信息表数据流
*/
@Getter
@Setter
@ApiModel(value = "区县信息表")
public class RegionAreasDTO implements Serializable {














    public RegionAreas convertTo(){
        ConvertImpl impl = new ConvertImpl();
        return impl.doForward(this);
    }

    public RegionAreasDTO convertFor(RegionAreas regionAreas){
        ConvertImpl impl = new ConvertImpl();
        return impl.doBackward(regionAreas,this);
    }

    private static class ConvertImpl implements Convert<RegionAreasDTO, RegionAreas> {
        @Override
        public RegionAreas doForward(RegionAreasDTO dto) {
            RegionAreas regionAreas = new RegionAreas();
            BeanUtils.copyProperties(dto,regionAreas);
            return regionAreas;
        }
        @Override
        public RegionAreasDTO doBackward(RegionAreas regionAreas) {
            RegionAreasDTO dto = new RegionAreasDTO();
            BeanUtils.copyProperties(regionAreas, dto);
            return dto;
        }
        public RegionAreasDTO doBackward(RegionAreas regionAreas, RegionAreasDTO dto) {
            BeanUtils.copyProperties(regionAreas, dto);
            return dto;
        }
    }
}