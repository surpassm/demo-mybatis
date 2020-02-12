package com.liaoin.demo.domain;


import com.liaoin.demo.annotation.Convert;
import com.liaoin.demo.entity.Department;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;


/**
* @author mc
* Create date 2020-02-10 10:15:20
* Version 1.0
* Description 部门数据流
*/
@Getter
@Setter
@ApiModel(value = "部门")
public class DepartmentDTO implements Serializable {

	@ApiModelProperty(value = "系统标识",position = 0)
	private Long id;
	@ApiModelProperty(value = "名称",position = 1)
	private String name ;
	@ApiModelProperty(value = "父级ID",position = 2)
	private Long parentId ;
	@ApiModelProperty(value = "排序字段",position = 3)
	private Integer departmentIndex ;
	private List<DepartmentDTO> childes;












    public Department convertTo(){
        ConvertImpl impl = new ConvertImpl();
        return impl.doForward(this);
    }

    public DepartmentDTO convertFor(Department department){
        ConvertImpl impl = new ConvertImpl();
        return impl.doBackward(department,this);
    }

    private static class ConvertImpl implements Convert<DepartmentDTO, Department> {
        @Override
        public Department doForward(DepartmentDTO dto) {
            Department department = new Department();
            BeanUtils.copyProperties(dto,department);
            return department;
        }
        @Override
        public DepartmentDTO doBackward(Department department) {
            DepartmentDTO dto = new DepartmentDTO();
            BeanUtils.copyProperties(department, dto);
            return dto;
        }
        public DepartmentDTO doBackward(Department department, DepartmentDTO dto) {
            BeanUtils.copyProperties(department, dto);
            return dto;
        }
    }
}