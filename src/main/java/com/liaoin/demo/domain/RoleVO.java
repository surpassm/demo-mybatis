package com.liaoin.demo.domain;


import com.liaoin.demo.annotation.Convert;
import com.liaoin.demo.entity.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;


/**
* @author mc
* Create date 2020-02-10 10:15:21
* Version 1.0
* Description 角色VO数据流
*/

@Data
@ApiModel(value = "角色VO")
public class RoleVO implements Serializable {

	@ApiModelProperty(value = "系统标识",position = 0)
	private Long id;
	@ApiModelProperty(value = "security标识",position = 1)
	private String securityRoles;
	@ApiModelProperty(value = "名称",position = 2)
	private String name;
	@ApiModelProperty(value = "描述",position = 3)
	private String describes;
	@ApiModelProperty(value = "排序字段",position = 4)
	private Integer roleIndex ;





    public Role convertTo(){
        ConvertImpl impl = new ConvertImpl();
        return impl.doForward(this);
    }

    public RoleVO convertFor(Role role){
        ConvertImpl impl = new ConvertImpl();
        return impl.doBackward(role,this);
    }

    private static class ConvertImpl implements Convert<RoleVO, Role> {
        @Override
        public Role doForward(RoleVO vo) {
            Role role = new Role();
            BeanUtils.copyProperties(vo,role);
            return role;
        }
        @Override
        public RoleVO doBackward(Role role) {
                RoleVO vo = new RoleVO();
                BeanUtils.copyProperties(role, vo);
                return vo;
        }
        public RoleVO doBackward(Role role, RoleVO vo) {
                BeanUtils.copyProperties(role, vo);
                return vo;
        }
    }




}
