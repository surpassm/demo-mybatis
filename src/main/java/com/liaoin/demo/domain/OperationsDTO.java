package com.liaoin.demo.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.liaoin.demo.annotation.Convert;
import com.liaoin.demo.entity.Operations;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


/**
* @author mc
* Create date 2020-02-10 10:15:20
* Version 1.0
* Description 后台功能接口数据流
*/
@Getter
@Setter
@ApiModel(value = "后台功能接口")
public class OperationsDTO implements Serializable {


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
	@ApiModelProperty(value = "创建时间",position = 7)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime createTime;

	private List<OperationsDTO> childes;










    public Operations convertTo(){
        ConvertImpl impl = new ConvertImpl();
        return impl.doForward(this);
    }

    public OperationsDTO convertFor(Operations operations){
        ConvertImpl impl = new ConvertImpl();
        return impl.doBackward(operations,this);
    }

    private static class ConvertImpl implements Convert<OperationsDTO, Operations> {
        @Override
        public Operations doForward(OperationsDTO dto) {
            Operations operations = new Operations();
            BeanUtils.copyProperties(dto,operations);
            return operations;
        }
        @Override
        public OperationsDTO doBackward(Operations operations) {
            OperationsDTO dto = new OperationsDTO();
            BeanUtils.copyProperties(operations, dto);
            return dto;
        }
        public OperationsDTO doBackward(Operations operations, OperationsDTO dto) {
            BeanUtils.copyProperties(operations, dto);
            return dto;
        }
    }
}