package com.liaoin.demo.domain.user;


import com.liaoin.demo.annotation.Convert;
import com.liaoin.demo.entity.user.UserInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

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
public class UserInfoDTO implements Serializable {


    @ApiModelProperty(value = "系统标识",position = 0)
    private Long id;
    @ApiModelProperty(value = "昵称",position = 1)
    private String nickName;

    @ApiModelProperty(value = "性别",position = 2)
    private String gender;

    @ApiModelProperty(value = "公司名称",position = 3)
    private String companyName;

    @ApiModelProperty(value = "职位",position = 4)
    private String position;

    @ApiModelProperty(value = "姓名",position = 5)
    private String name;

    @ApiModelProperty(value = "电话",position = 6)
    private String mobile;

    @ApiModelProperty(value = "email",position = 7)
    private String email;




    public UserInfo convertTo(){
        ConvertImpl impl = new ConvertImpl();
        return impl.doForward(this);
    }

    public UserInfoDTO convertFor(UserInfo userInfo){
        ConvertImpl impl = new ConvertImpl();
        return impl.doBackward(userInfo);
    }

    public static class ConvertImpl implements Convert<UserInfoDTO, UserInfo> {
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
    }
}
