package com.liaoin.demo.domain.user;

import com.liaoin.demo.annotation.Convert;
import com.liaoin.demo.entity.user.Department;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author mc
 * Create date 2019/12/12 12:31
 * Version 1.0
 * Description
 */
@Getter
@Setter
@ApiModel(value = "部门")
public class DepartmentDto {

    @NotEmpty(message = "字段name错误")
    @ApiModelProperty(value = "名称")
    private String name ;
    @ApiModelProperty(value = "排序字段")
    private Integer departmentIndex ;
    private Long parentId ;

    public Department convertTo(){
        ConvertImpl impl = new ConvertImpl();
        return impl.doForward(this);
    }

    public DepartmentDto convertFor(Department department){
        ConvertImpl impl = new ConvertImpl();
        return impl.doBackward(department);
    }

    private static class ConvertImpl implements Convert<DepartmentDto, Department> {
        @Override
        public Department doForward(DepartmentDto dto) {
            Department department = new Department();
            BeanUtils.copyProperties(dto,department);
            return department;
        }
        @Override
        public DepartmentDto doBackward(Department department) {
            DepartmentDto dto = new DepartmentDto();
            BeanUtils.copyProperties(department, dto);
            return dto;
        }
    }
}
