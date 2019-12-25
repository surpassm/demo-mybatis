package com.liaoin.demo.domain.user;


import com.liaoin.demo.annotation.Convert;
import com.liaoin.demo.entity.user.Role;
import io.swagger.annotations.ApiModel;
import lombok.Data;
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
@Data
@Getter
@Setter
@ApiModel(value = "角色")
public class RoleDTO implements Serializable {


    public Role convertTo(){
        ConvertImpl impl = new ConvertImpl();
        return impl.doForward(this);
    }

    public RoleDTO convertFor(Role role){
        ConvertImpl impl = new ConvertImpl();
        return impl.doBackward(role);
    }

    private static class ConvertImpl implements Convert<RoleDTO, Role> {
        @Override
        public Role doForward(RoleDTO dto) {
            Role role = new Role();
            BeanUtils.copyProperties(dto,role);
            return role;
        }
        @Override
        public RoleDTO doBackward(Role role) {
            RoleDTO dto = new RoleDTO();
            BeanUtils.copyProperties(role, dto);
            return dto;
        }
    }
}
