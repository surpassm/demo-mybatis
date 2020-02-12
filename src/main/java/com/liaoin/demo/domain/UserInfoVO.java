package com.liaoin.demo.domain;


import com.liaoin.demo.annotation.Convert;
import com.liaoin.demo.entity.UserInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


/**
* @author mc
* Create date 2020-02-10 10:15:21
* Version 1.0
* Description 用户VO数据流
*/

@Data
@ApiModel(value = "用户VO")
public class UserInfoVO implements Serializable {

	@ApiModelProperty(value = "系统标识",position = 0)
	private Long id;

	@NotEmpty
	@ApiModelProperty(value = "账号",position = 1)
	private String username;

	@ApiModelProperty(value = "密码",position = 2)
	private String password;

	@ApiModelProperty(value = "头像URL",position = 3)
	private String headUrl;

	@Min(0)
	@Max(1)
	@ApiModelProperty(value = "是否启用0=否,1=是",position = 4)
	private Integer isEnable;

	@Column(columnDefinition = "bigint COMMENT '排序字段'")
	private Long userInfoIndex;

    public UserInfo convertTo(){
        ConvertImpl impl = new ConvertImpl();
        return impl.doForward(this);
    }

    public UserInfoVO convertFor(UserInfo userInfo){
        ConvertImpl impl = new ConvertImpl();
        return impl.doBackward(userInfo,this);
    }

    private static class ConvertImpl implements Convert<UserInfoVO, UserInfo> {
        @Override
        public UserInfo doForward(UserInfoVO vo) {
            UserInfo userInfo = new UserInfo();
            BeanUtils.copyProperties(vo,userInfo);
            return userInfo;
        }
        @Override
        public UserInfoVO doBackward(UserInfo userInfo) {
                UserInfoVO vo = new UserInfoVO();
                BeanUtils.copyProperties(userInfo, vo);
                return vo;
        }
        public UserInfoVO doBackward(UserInfo userInfo, UserInfoVO vo) {
                BeanUtils.copyProperties(userInfo, vo);
                return vo;
        }
    }




}
