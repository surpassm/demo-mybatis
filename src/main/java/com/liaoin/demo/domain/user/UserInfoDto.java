package com.liaoin.demo.domain.user;


import com.liaoin.demo.annotation.Convert;
import com.liaoin.demo.entity.user.UserInfo;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
* @author mc
* Create date 2019-12-12 21:02:27
* Version 1.0
* Description 用户数据流
*/
@Getter
@Setter
@ApiModel(value = "用户")
public class UserInfoDto implements Serializable {








    public UserInfo convertTo(){
        ConvertImpl impl = new ConvertImpl();
        return impl.doForward(this);
    }

    public UserInfoDto convertFor(UserInfo userInfo){
        ConvertImpl impl = new ConvertImpl();
        return impl.doBackward(userInfo);
    }

    private static class ConvertImpl implements Convert<UserInfoDto, UserInfo> {
        @Override
        public UserInfo doForward(UserInfoDto dto) {
            UserInfo userInfo = new UserInfo();
            BeanUtils.copyProperties(dto,userInfo);
            return userInfo;
        }
        @Override
        public UserInfoDto doBackward(UserInfo userInfo) {
            UserInfoDto dto = new UserInfoDto();
            BeanUtils.copyProperties(userInfo, dto);
            return dto;
        }
    }
}
