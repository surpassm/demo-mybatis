package com.liaoin.demo.domain;


import com.liaoin.demo.annotation.Convert;
import com.liaoin.demo.entity.Department;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;


/**
* @author mc
* Create date 2020-02-10 10:15:20
* Version 1.0
* Description 部门VO数据流
*/

@Data
@ApiModel(value = "部门VO")
public class DepartmentVO implements Serializable {

	@ApiModelProperty(value = "系统标识",position = 0)
	private Long id;
	@ApiModelProperty(value = "名称",position = 1)
	private String name ;
	@ApiModelProperty(value = "父级ID",position = 2)
	private Long parentId ;
	@ApiModelProperty(value = "排序字段",position = 3)
	private Integer departmentIndex ;






    public Department convertTo(){
        ConvertImpl impl = new ConvertImpl();
        return impl.doForward(this);
    }

    public DepartmentVO convertFor(Department department){
        ConvertImpl impl = new ConvertImpl();
        return impl.doBackward(department,this);
    }

    private static class ConvertImpl implements Convert<DepartmentVO, Department> {
        @Override
        public Department doForward(DepartmentVO vo) {
            Department department = new Department();
            BeanUtils.copyProperties(vo,department);
            return department;
        }
        @Override
        public DepartmentVO doBackward(Department department) {
                DepartmentVO vo = new DepartmentVO();
                BeanUtils.copyProperties(department, vo);
                return vo;
        }
        public DepartmentVO doBackward(Department department, DepartmentVO vo) {
                BeanUtils.copyProperties(department, vo);
                return vo;
        }
    }




}
