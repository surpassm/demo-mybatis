package com.liaoin.demo.domain.user;


import com.liaoin.demo.annotation.Convert;
import com.liaoin.demo.entity.user.Role;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
* @author mc
* Create date 2019-12-12 21:02:27
* Version 1.0
* Description 角色数据流
*/
@Getter
@Setter
@ApiModel(value = "角色")
public class RoleDto implements Serializable {


    public Role convertTo(){
        ConvertImpl impl = new ConvertImpl();
        return impl.doForward(this);
    }

    public RoleDto convertFor(Role role){
        ConvertImpl impl = new ConvertImpl();
        return impl.doBackward(role);
    }

    private static class ConvertImpl implements Convert<RoleDto, Role> {
        @Override
        public Role doForward(RoleDto dto) {
            Role role = new Role();
            BeanUtils.copyProperties(dto,role);
            return role;
        }
        @Override
        public RoleDto doBackward(Role role) {
            RoleDto dto = new RoleDto();
            BeanUtils.copyProperties(role, dto);
            return dto;
        }
    }
}
