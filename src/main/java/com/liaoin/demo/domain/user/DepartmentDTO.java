package com.liaoin.demo.domain.user;

import com.liaoin.demo.annotation.Convert;
import com.liaoin.demo.entity.user.Department;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotEmpty;

/**
 * @author mc
 * Create date 2019/12/12 12:31
 * Version 1.0
 * Description
 */
@Getter
@Setter
@ApiModel(value = "部门")
public class DepartmentDTO {

    @ApiModelProperty(value = "系统标识",position = 0)
    private Long id;

    @NotEmpty(message = "字段name错误")
    @ApiModelProperty(value = "名称",position = 1)
    private String name ;

    @ApiModelProperty(value = "排序字段",position = 2)
    private Integer departmentIndex ;

    @ApiModelProperty(value = "所属父级{如果自己为父，不传}",position = 3)
    private Long parentId ;

    public Department convertTo(){
        ConvertImpl impl = new ConvertImpl();
        return impl.doForward(this);
    }

    public DepartmentDTO convertFor(Department department){
        ConvertImpl impl = new ConvertImpl();
        return impl.doBackward(department);
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
    }
}