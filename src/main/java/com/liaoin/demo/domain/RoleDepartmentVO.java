package com.liaoin.demo.domain;


import com.liaoin.demo.annotation.Convert;
import com.liaoin.demo.entity.RoleDepartment;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;


/**
* @author mc
* Create date 2020-02-10 10:15:20
* Version 1.0
* Description 角色部门VO数据流
*/

@Data
@ApiModel(value = "角色部门VO")
public class RoleDepartmentVO implements Serializable {








    public RoleDepartment convertTo(){
        ConvertImpl impl = new ConvertImpl();
        return impl.doForward(this);
    }

    public RoleDepartmentVO convertFor(RoleDepartment roleDepartment){
        ConvertImpl impl = new ConvertImpl();
        return impl.doBackward(roleDepartment,this);
    }

    private static class ConvertImpl implements Convert<RoleDepartmentVO, RoleDepartment> {
        @Override
        public RoleDepartment doForward(RoleDepartmentVO vo) {
            RoleDepartment roleDepartment = new RoleDepartment();
            BeanUtils.copyProperties(vo,roleDepartment);
            return roleDepartment;
        }
        @Override
        public RoleDepartmentVO doBackward(RoleDepartment roleDepartment) {
                RoleDepartmentVO vo = new RoleDepartmentVO();
                BeanUtils.copyProperties(roleDepartment, vo);
                return vo;
        }
        public RoleDepartmentVO doBackward(RoleDepartment roleDepartment, RoleDepartmentVO vo) {
                BeanUtils.copyProperties(roleDepartment, vo);
                return vo;
        }
    }




}
