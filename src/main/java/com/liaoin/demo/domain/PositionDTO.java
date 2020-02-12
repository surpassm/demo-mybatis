package com.liaoin.demo.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.liaoin.demo.annotation.Convert;
import com.liaoin.demo.entity.Position;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


/**
* @author mc
* Create date 2020-02-10 10:15:20
* Version 1.0
* Description 职位数据流
*/
@Getter
@Setter
@ApiModel(value = "职位")
public class PositionDTO implements Serializable {


	@ApiModelProperty(value = "系统标识",position = 0)
	private Long id;
	@ApiModelProperty(value = "名称",position = 1)
	@Column(columnDefinition="varchar(255) COMMENT '名称'")
	private String name ;
	@ApiModelProperty(value = "父级ID",position = 2)
	private Long parentId ;
	@ApiModelProperty(value = "创建时间",position = 3)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private LocalDateTime createTime;
	@ApiModelProperty(value = "部门ID",position = 4)
	private Long departmentId;
	@ApiModelProperty(value = "部门名稱",position = 4)
	private Long departmentName;

	private List<PositionDTO> childes;











    public Position convertTo(){
        ConvertImpl impl = new ConvertImpl();
        return impl.doForward(this);
    }

    public PositionDTO convertFor(Position position){
        ConvertImpl impl = new ConvertImpl();
        return impl.doBackward(position,this);
    }

    private static class ConvertImpl implements Convert<PositionDTO, Position> {
        @Override
        public Position doForward(PositionDTO dto) {
            Position position = new Position();
            BeanUtils.copyProperties(dto,position);
            return position;
        }
        @Override
        public PositionDTO doBackward(Position position) {
            PositionDTO dto = new PositionDTO();
            BeanUtils.copyProperties(position, dto);
            return dto;
        }
        public PositionDTO doBackward(Position position, PositionDTO dto) {
            BeanUtils.copyProperties(position, dto);
            return dto;
        }
    }
}