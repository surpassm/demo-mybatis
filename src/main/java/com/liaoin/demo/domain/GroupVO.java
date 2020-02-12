package com.liaoin.demo.domain;


import com.liaoin.demo.annotation.Convert;
import com.liaoin.demo.entity.Group;
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
public class GroupVO implements Serializable {


	@ApiModelProperty(value = "系统标识",position = 0)
	private Long id;
	@ApiModelProperty(value = "名称",position = 1)
	private String name;
	@ApiModelProperty(value = "描述",position = 2)
	private String describes;

	@ApiModelProperty(value = "父级",position = 3)
	private Long parentId;




    public Group convertTo(){
        ConvertImpl impl = new ConvertImpl();
        return impl.doForward(this);
    }

    public GroupVO convertFor(Group group){
        ConvertImpl impl = new ConvertImpl();
        return impl.doBackward(group,this);
    }

    private static class ConvertImpl implements Convert<GroupVO, Group> {
        @Override
        public Group doForward(GroupVO vo) {
            Group group = new Group();
            BeanUtils.copyProperties(vo,group);
            return group;
        }
        @Override
        public GroupVO doBackward(Group group) {
                GroupVO vo = new GroupVO();
                BeanUtils.copyProperties(group, vo);
                return vo;
        }
        public GroupVO doBackward(Group group, GroupVO vo) {
                BeanUtils.copyProperties(group, vo);
                return vo;
        }
    }




}
