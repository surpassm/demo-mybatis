package com.liaoin.demo.domain;


import com.liaoin.demo.annotation.Convert;
import com.liaoin.demo.entity.Groups;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;


/**
* @author mc
* Create date 2020-02-10 10:15:20
* Version 1.0
* Description 权限数据流
*/
@Getter
@Setter
@ApiModel(value = "权限")
public class GroupsDTO implements Serializable {





	@ApiModelProperty(value = "系统标识",position = 0)
	private Long id;
	@ApiModelProperty(value = "名称",position = 1)
	private String name;
	@ApiModelProperty(value = "描述",position = 2)
	private String describes;
	@ApiModelProperty(value = "父级",position = 3)
	private Long parentId;
	private List<GroupsDTO> childes;








    public Groups convertTo(){
        ConvertImpl impl = new ConvertImpl();
        return impl.doForward(this);
    }

    public GroupsDTO convertFor(Groups groups){
        ConvertImpl impl = new ConvertImpl();
        return impl.doBackward(groups,this);
    }

    private static class ConvertImpl implements Convert<GroupsDTO, Groups> {
        @Override
        public Groups doForward(GroupsDTO dto) {
            Groups groups = new Groups();
            BeanUtils.copyProperties(dto, groups);
            return groups;
        }
        @Override
        public GroupsDTO doBackward(Groups groups) {
            GroupsDTO dto = new GroupsDTO();
            BeanUtils.copyProperties(groups, dto);
            return dto;
        }
        public GroupsDTO doBackward(Groups groups, GroupsDTO dto) {
            BeanUtils.copyProperties(groups, dto);
            return dto;
        }
    }
}