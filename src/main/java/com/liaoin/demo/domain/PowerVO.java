package com.liaoin.demo.domain;


import com.liaoin.demo.annotation.Convert;
import com.liaoin.demo.entity.Power;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;


/**
* @author mc
* Create date 2020-02-10 10:15:20
* Version 1.0
* Description 权限表VO数据流
*/

@Data
@ApiModel(value = "权限表VO")
public class PowerVO implements Serializable {

	@ApiModelProperty(value = "系统标识",position = 0)
	private Long id;
	@ApiModelProperty(value = "名称",position = 1)
	private String name ;






    public Power convertTo(){
        ConvertImpl impl = new ConvertImpl();
        return impl.doForward(this);
    }

    public PowerVO convertFor(Power power){
        ConvertImpl impl = new ConvertImpl();
        return impl.doBackward(power,this);
    }

    private static class ConvertImpl implements Convert<PowerVO, Power> {
        @Override
        public Power doForward(PowerVO vo) {
            Power power = new Power();
            BeanUtils.copyProperties(vo,power);
            return power;
        }
        @Override
        public PowerVO doBackward(Power power) {
                PowerVO vo = new PowerVO();
                BeanUtils.copyProperties(power, vo);
                return vo;
        }
        public PowerVO doBackward(Power power, PowerVO vo) {
                BeanUtils.copyProperties(power, vo);
                return vo;
        }
    }




}
