package com.liaoin.demo.domain;


import com.liaoin.demo.annotation.Convert;
import com.liaoin.demo.entity.UserInfo;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;


/**
* @author mc
* Create date 2020-02-10 10:15:21
* Version 1.0
* Description 用户数据流
*/
@Getter
@Setter
@ApiModel(value = "用户")
public class UserInfoDTO implements Serializable {














    public UserInfo convertTo(){
        ConvertImpl impl = new ConvertImpl();
        return impl.doForward(this);
    }

    public UserInfoDTO convertFor(UserInfo userInfo){
        ConvertImpl impl = new ConvertImpl();
        return impl.doBackward(userInfo,this);
    }

    private static class ConvertImpl implements Convert<UserInfoDTO, UserInfo> {
        @Override
        public UserInfo doForward(UserInfoDTO dto) {
            UserInfo userInfo = new UserInfo();
            BeanUtils.copyProperties(dto,userInfo);
            return userInfo;
        }
        @Override
        public UserInfoDTO doBackward(UserInfo userInfo) {
            UserInfoDTO dto = new UserInfoDTO();
            BeanUtils.copyProperties(userInfo, dto);
            return dto;
        }
        public UserInfoDTO doBackward(UserInfo userInfo, UserInfoDTO dto) {
            BeanUtils.copyProperties(userInfo, dto);
            return dto;
        }
    }
}