package com.liaoin.demo.service.user.impl;

import com.liaoin.demo.entity.user.Department;
import com.liaoin.demo.mapper.user.DepartmentMapper;
import com.liaoin.demo.mapper.user.UserInfoMapper;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * @author mc
 * Create date 2019/12/13 10:18
 * Version 1.0
 * Description
 */
@Component
public class CommonImpl<T> {
    @Resource
    private DepartmentMapper departmentMapper;
    @Resource
    private UserInfoMapper userInfoMapper;

    /**
     * 判断名称是否存在
     * @param name name
     * @return boolean
     */
    public Boolean isGetName(String name){
        int i = departmentMapper.selectCount(Department.builder().isDelete(0).name(name).build());
        return i > 0;
    }

    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return department
     */
    public Department getDepartmentId(Long id){
        return departmentMapper.selectByPrimaryKey(id);
    }

}
