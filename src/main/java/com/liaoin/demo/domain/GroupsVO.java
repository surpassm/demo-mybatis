package com.liaoin.demo.domain;


import com.liaoin.demo.annotation.Convert;
import com.liaoin.demo.entity.Groups;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;


/**
* @author mc
* Create date 2020-02-10 10:15:20
* Version 1.0
* Description 权限VO数据流
*/

@Data
@ApiModel(value = "权限VO")
public class GroupsVO implements Serializable {


	@ApiModelProperty(value = "系统标识",position = 0)
	private Long id;
	@ApiModelProperty(value = "名称",position = 1)
	private String name;
	@ApiModelProperty(value = "描述",position = 2)
	private String describes;

	@ApiModelProperty(value = "父级",position = 3)
	private Long parentId;




    public Groups convertTo(){
        ConvertImpl impl = new ConvertImpl();
        return impl.doForward(this);
    }

    public GroupsVO convertFor(Groups groups){
        ConvertImpl impl = new ConvertImpl();
        return impl.doBackward(groups,this);
    }

    private static class ConvertImpl implements Convert<GroupsVO, Groups> {
        @Override
        public Groups doForward(GroupsVO vo) {
            Groups groups = new Groups();
            BeanUtils.copyProperties(vo, groups);
            return groups;
        }
        @Override
        public GroupsVO doBackward(Groups groups) {
                GroupsVO vo = new GroupsVO();
                BeanUtils.copyProperties(groups, vo);
                return vo;
        }
        public GroupsVO doBackward(Groups groups, GroupsVO vo) {
                BeanUtils.copyProperties(groups, vo);
                return vo;
        }
    }




}
