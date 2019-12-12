package com.liaoin.demo.domain.user;


import com.liaoin.demo.annotation.Convert;
import com.liaoin.demo.entity.user.Group;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
* @author mc
* Create date 2019-12-12 21:02:26
* Version 1.0
* Description 权限数据流
*/
@Getter
@Setter
@ApiModel(value = "权限")
public class GroupDto implements Serializable {


    public Group convertTo(){
        ConvertImpl impl = new ConvertImpl();
        return impl.doForward(this);
    }

    public GroupDto convertFor(Group group){
        ConvertImpl impl = new ConvertImpl();
        return impl.doBackward(group);
    }

    private static class ConvertImpl implements Convert<GroupDto, Group> {
        @Override
        public Group doForward(GroupDto dto) {
            Group group = new Group();
            BeanUtils.copyProperties(dto,group);
            return group;
        }
        @Override
        public GroupDto doBackward(Group group) {
            GroupDto dto = new GroupDto();
            BeanUtils.copyProperties(group, dto);
            return dto;
        }
    }
}
