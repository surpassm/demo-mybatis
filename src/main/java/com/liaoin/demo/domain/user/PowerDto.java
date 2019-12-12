package com.liaoin.demo.domain.user;


import com.liaoin.demo.annotation.Convert;
import com.liaoin.demo.entity.user.Power;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
* @author mc
* Create date 2019-12-12 21:02:27
* Version 1.0
* Description 权限表数据流
*/
@Getter
@Setter
@ApiModel(value = "权限表")
public class PowerDto implements Serializable {


    public Power convertTo(){
        ConvertImpl impl = new ConvertImpl();
        return impl.doForward(this);
    }

    public PowerDto convertFor(Power power){
        ConvertImpl impl = new ConvertImpl();
        return impl.doBackward(power);
    }

    private static class ConvertImpl implements Convert<PowerDto, Power> {
        @Override
        public Power doForward(PowerDto dto) {
            Power power = new Power();
            BeanUtils.copyProperties(dto,power);
            return power;
        }
        @Override
        public PowerDto doBackward(Power power) {
            PowerDto dto = new PowerDto();
            BeanUtils.copyProperties(power, dto);
            return dto;
        }
    }
}
