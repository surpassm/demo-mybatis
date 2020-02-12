package com.liaoin.demo.domain;


import com.liaoin.demo.annotation.Convert;
import com.liaoin.demo.entity.RegionProvinces;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;


/**
* @author mc
* Create date 2020-02-10 10:15:20
* Version 1.0
* Description 省VO数据流
*/

@Data
@ApiModel(value = "省VO")
public class RegionProvincesVO implements Serializable {








    public RegionProvinces convertTo(){
        ConvertImpl impl = new ConvertImpl();
        return impl.doForward(this);
    }

    public RegionProvincesVO convertFor(RegionProvinces regionProvinces){
        ConvertImpl impl = new ConvertImpl();
        return impl.doBackward(regionProvinces,this);
    }

    private static class ConvertImpl implements Convert<RegionProvincesVO, RegionProvinces> {
        @Override
        public RegionProvinces doForward(RegionProvincesVO vo) {
            RegionProvinces regionProvinces = new RegionProvinces();
            BeanUtils.copyProperties(vo,regionProvinces);
            return regionProvinces;
        }
        @Override
        public RegionProvincesVO doBackward(RegionProvinces regionProvinces) {
                RegionProvincesVO vo = new RegionProvincesVO();
                BeanUtils.copyProperties(regionProvinces, vo);
                return vo;
        }
        public RegionProvincesVO doBackward(RegionProvinces regionProvinces, RegionProvincesVO vo) {
                BeanUtils.copyProperties(regionProvinces, vo);
                return vo;
        }
    }




}
