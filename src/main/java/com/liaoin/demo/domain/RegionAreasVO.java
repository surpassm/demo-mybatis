package com.liaoin.demo.domain;


import com.liaoin.demo.annotation.Convert;
import com.liaoin.demo.entity.RegionAreas;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;


/**
* @author mc
* Create date 2020-02-10 10:15:20
* Version 1.0
* Description 区县信息表VO数据流
*/

@Data
@ApiModel(value = "区县信息表VO")
public class RegionAreasVO implements Serializable {








    public RegionAreas convertTo(){
        ConvertImpl impl = new ConvertImpl();
        return impl.doForward(this);
    }

    public RegionAreasVO convertFor(RegionAreas regionAreas){
        ConvertImpl impl = new ConvertImpl();
        return impl.doBackward(regionAreas,this);
    }

    private static class ConvertImpl implements Convert<RegionAreasVO, RegionAreas> {
        @Override
        public RegionAreas doForward(RegionAreasVO vo) {
            RegionAreas regionAreas = new RegionAreas();
            BeanUtils.copyProperties(vo,regionAreas);
            return regionAreas;
        }
        @Override
        public RegionAreasVO doBackward(RegionAreas regionAreas) {
                RegionAreasVO vo = new RegionAreasVO();
                BeanUtils.copyProperties(regionAreas, vo);
                return vo;
        }
        public RegionAreasVO doBackward(RegionAreas regionAreas, RegionAreasVO vo) {
                BeanUtils.copyProperties(regionAreas, vo);
                return vo;
        }
    }




}
