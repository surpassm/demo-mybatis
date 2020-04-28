package com.liaoin.demo.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.liaoin.demo.annotation.Convert;
import com.liaoin.demo.entity.Positions;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
* @author mc
* Create date 2020-02-10 10:15:20
* Version 1.0
* Description 职位VO数据流
*/

@Data
@ApiModel(value = "职位VO")
public class PositionsVO implements Serializable {

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





    public Positions convertTo(){
        ConvertImpl impl = new ConvertImpl();
        return impl.doForward(this);
    }

    public PositionsVO convertFor(Positions positions){
        ConvertImpl impl = new ConvertImpl();
        return impl.doBackward(positions,this);
    }

    private static class ConvertImpl implements Convert<PositionsVO, Positions> {
        @Override
        public Positions doForward(PositionsVO vo) {
            Positions positions = new Positions();
            BeanUtils.copyProperties(vo, positions);
            return positions;
        }
        @Override
        public PositionsVO doBackward(Positions positions) {
                PositionsVO vo = new PositionsVO();
                BeanUtils.copyProperties(positions, vo);
                return vo;
        }
        public PositionsVO doBackward(Positions positions, PositionsVO vo) {
                BeanUtils.copyProperties(positions, vo);
                return vo;
        }
    }




}
