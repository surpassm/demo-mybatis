package com.liaoin.demo.domain.user;


import com.liaoin.demo.annotation.Convert;
import com.liaoin.demo.entity.user.UserInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
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


    @ApiModelProperty(value = "系统标识",position = 0)
    private Long id;
    @NotEmpty
    @ApiModelProperty(value = "账号{用户名具有唯一约束，字母、中文、点组成2-20位}",position = 1)
    private String username;
    @NotEmpty
    @ApiModelProperty(value = "密码{匹配小写字母、大写字母、数字、特殊符号的两种及两种以上【非中文】}",position = 2)
    private String password;
    @ApiModelProperty(value = "姓名",position = 3)
    private String name;
    @ApiModelProperty(value = "手机号码",position = 4)
    private String mobile;
    @ApiModelProperty(value = "头像URL",position = 5)
    private String headUrl;




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
