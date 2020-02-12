package com.liaoin.demo.domain;


import com.liaoin.demo.annotation.Convert;
import com.liaoin.demo.entity.Group;
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
public class GroupDTO implements Serializable {





	@ApiModelProperty(value = "系统标识",position = 0)
	private Long id;
	@ApiModelProperty(value = "名称",position = 1)
	private String name;
	@ApiModelProperty(value = "描述",position = 2)
	private String describes;
	@ApiModelProperty(value = "父级",position = 3)
	private Long parentId;
	private List<GroupDTO> childes;








    public Group convertTo(){
        ConvertImpl impl = new ConvertImpl();
        return impl.doForward(this);
    }

    public GroupDTO convertFor(Group group){
        ConvertImpl impl = new ConvertImpl();
        return impl.doBackward(group,this);
    }

    private static class ConvertImpl implements Convert<GroupDTO, Group> {
        @Override
        public Group doForward(GroupDTO dto) {
            Group group = new Group();
            BeanUtils.copyProperties(dto,group);
            return group;
        }
        @Override
        public GroupDTO doBackward(Group group) {
            GroupDTO dto = new GroupDTO();
            BeanUtils.copyProperties(group, dto);
            return dto;
        }
        public GroupDTO doBackward(Group group, GroupDTO dto) {
            BeanUtils.copyProperties(group, dto);
            return dto;
        }
    }
}