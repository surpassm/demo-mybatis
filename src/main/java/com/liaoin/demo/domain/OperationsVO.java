package com.liaoin.demo.domain;


import com.liaoin.demo.annotation.Convert;
import com.liaoin.demo.entity.Operations;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


/**
* @author mc
* Create date 2020-02-10 10:15:20
* Version 1.0
* Description 后台功能接口VO数据流
*/

@Data
@ApiModel(value = "后台功能接口VO")
public class OperationsVO implements Serializable {


	@ApiModelProperty(value = "系统标识",position = 0)
	private Long id;
	@ApiModelProperty(value = "父级ID",position = 1)
	private Long parentId;
	@ApiModelProperty(value = "排序",position = 2)
	private Integer menuIndex;
	@ApiModelProperty(value = "权限分类（0 菜单；1 功能）",position = 3)
	private Integer type;
	@ApiModelProperty(value = "名称",position = 4)
	private String name;
	@ApiModelProperty(value = "描述",position = 5)
	@NotBlank(message = "参数不能为为空或空串")
	private String describes;
	@ApiModelProperty(value = "菜单url后台权限控制",position = 6)
	private String apiUrl;





    public Operations convertTo(){
        ConvertImpl impl = new ConvertImpl();
        return impl.doForward(this);
    }

    public OperationsVO convertFor(Operations operations){
        ConvertImpl impl = new ConvertImpl();
        return impl.doBackward(operations,this);
    }

    private static class ConvertImpl implements Convert<OperationsVO, Operations> {
        @Override
        public Operations doForward(OperationsVO vo) {
            Operations operations = new Operations();
            BeanUtils.copyProperties(vo,operations);
            return operations;
        }
        @Override
        public OperationsVO doBackward(Operations operations) {
                OperationsVO vo = new OperationsVO();
                BeanUtils.copyProperties(operations, vo);
                return vo;
        }
        public OperationsVO doBackward(Operations operations, OperationsVO vo) {
                BeanUtils.copyProperties(operations, vo);
                return vo;
        }
    }




}
