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
public class PowerDTO implements Serializable {


    public Power convertTo(){
        ConvertImpl impl = new ConvertImpl();
        return impl.doForward(this);
    }

    public PowerDTO convertFor(Power power){
        ConvertImpl impl = new ConvertImpl();
        return impl.doBackward(power);
    }

    private static class ConvertImpl implements Convert<PowerDTO, Power> {
        @Override
        public Power doForward(PowerDTO dto) {
            Power power = new Power();
            BeanUtils.copyProperties(dto,power);
            return power;
        }
        @Override
        public PowerDTO doBackward(Power power) {
            PowerDTO dto = new PowerDTO();
            BeanUtils.copyProperties(power, dto);
            return dto;
        }
    }
}
