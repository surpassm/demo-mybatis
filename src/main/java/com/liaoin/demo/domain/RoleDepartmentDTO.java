package com.liaoin.demo.domain;


import com.liaoin.demo.annotation.Convert;
import com.liaoin.demo.entity.RoleDepartment;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;


/**
* @author mc
* Create date 2020-02-10 10:15:20
* Version 1.0
* Description 角色部门数据流
*/
@Getter
@Setter
@ApiModel(value = "角色部门")
public class RoleDepartmentDTO implements Serializable {














    public RoleDepartment convertTo(){
        ConvertImpl impl = new ConvertImpl();
        return impl.doForward(this);
    }

    public RoleDepartmentDTO convertFor(RoleDepartment roleDepartment){
        ConvertImpl impl = new ConvertImpl();
        return impl.doBackward(roleDepartment,this);
    }

    private static class ConvertImpl implements Convert<RoleDepartmentDTO, RoleDepartment> {
        @Override
        public RoleDepartment doForward(RoleDepartmentDTO dto) {
            RoleDepartment roleDepartment = new RoleDepartment();
            BeanUtils.copyProperties(dto,roleDepartment);
            return roleDepartment;
        }
        @Override
        public RoleDepartmentDTO doBackward(RoleDepartment roleDepartment) {
            RoleDepartmentDTO dto = new RoleDepartmentDTO();
            BeanUtils.copyProperties(roleDepartment, dto);
            return dto;
        }
        public RoleDepartmentDTO doBackward(RoleDepartment roleDepartment, RoleDepartmentDTO dto) {
            BeanUtils.copyProperties(roleDepartment, dto);
            return dto;
        }
    }
}