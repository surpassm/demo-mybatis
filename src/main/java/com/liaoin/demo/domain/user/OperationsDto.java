package com.liaoin.demo.domain.user;


import com.liaoin.demo.annotation.Convert;
import com.liaoin.demo.entity.user.Operations;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
* @author mc
* Create date 2019-12-12 21:02:27
* Version 1.0
* Description 后台功能接口数据流
*/
@Getter
@Setter
@ApiModel(value = "后台功能接口")
public class OperationsDto implements Serializable {


    public Operations convertTo(){
        ConvertImpl impl = new ConvertImpl();
        return impl.doForward(this);
    }

    public OperationsDto convertFor(Operations operations){
        ConvertImpl impl = new ConvertImpl();
        return impl.doBackward(operations);
    }

    private static class ConvertImpl implements Convert<OperationsDto, Operations> {
        @Override
        public Operations doForward(OperationsDto dto) {
            Operations operations = new Operations();
            BeanUtils.copyProperties(dto,operations);
            return operations;
        }
        @Override
        public OperationsDto doBackward(Operations operations) {
            OperationsDto dto = new OperationsDto();
            BeanUtils.copyProperties(operations, dto);
            return dto;
        }
    }
}
