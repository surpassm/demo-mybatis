package com.liaoin.demo.service.user.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.liaoin.demo.common.Result;
import com.liaoin.demo.common.ResultCode;
import com.liaoin.demo.domain.user.DepartmentDto;
import com.liaoin.demo.entity.user.Department;
import com.liaoin.demo.entity.user.DepartmentUserInfo;
import com.liaoin.demo.entity.user.UserInfo;
import com.liaoin.demo.mapper.user.DepartmentMapper;
import com.liaoin.demo.mapper.user.DepartmentUserInfoMapper;
import com.liaoin.demo.mapper.user.UserInfoMapper;
import com.liaoin.demo.service.user.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.liaoin.demo.common.Result.fail;
import static com.liaoin.demo.common.Result.ok;


/**
 * @author mc
 * Create date 2019-03-14 20:41:03
 * Version 1.0
 * Description 部门实现类
 */
@Slf4j
@Service
@Transactional(rollbackFor = {RuntimeException.class, Exception.class})
public class DepartmentServiceImpl implements DepartmentService {
    @Resource
    private DepartmentMapper departmentMapper;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private DepartmentUserInfoMapper departmentUserInfoMapper;

    @Override
    public Result insertParent(Long userId, DepartmentDto dto) {
        //查询名称是否存在
        if (isGetName(dto.getName())){
            return fail(ResultCode.DATA_ALREADY_EXISTED.getMsg());
        }
        Department department = dto.convertTo();
        department.setIsDelete(0);
        department.setParentId(null);
        department.setCreateTime(LocalDateTime.now());
        departmentMapper.insert(department);
        return ok(department);
    }

    @Override
    public Result insertChild(Long userId, DepartmentDto dto) {
        //查询名称是否存在
        if (isGetName(dto.getName())){
            return fail(ResultCode.DATA_ALREADY_EXISTED.getMsg());
        }
        //查询父级是否存在
        if (dto.getParentId() == null){
            return fail(ResultCode.PARAM_IS_BLANK.getMsg());
        }
        Department department = departmentMapper.selectByPrimaryKey(dto.getParentId());
        if (department == null){
            return fail(ResultCode.DATA_ALREADY_EXISTED.getMsg());
        }

        return null;
    }
    private Boolean isGetName(String name){
        int i = departmentMapper.selectCount(Department.builder().isDelete(0).name(name).build());
        return i > 0;
    }

    @Override
    public Result update(Long userId, Department department) {
        if (department == null) {
            return fail(ResultCode.PARAM_IS_INVALID.getMsg());
        }
        return ok();
    }
    @Override
    public Result deleteGetById(Long userId, Long id) {
        if (id == null) {
            return fail(ResultCode.PARAM_IS_INVALID.getMsg());
        }
        Department department = departmentMapper.selectByPrimaryKey(id);
        if (department == null) {
            return fail(ResultCode.RESULE_DATA_NONE.getMsg());
        }

        Department build = Department.builder().parentId(department.getId()).build();
        build.setIsDelete(0);
        int selectCount = departmentMapper.selectCount(build);
        if (selectCount != 0) {
            return fail("存在下级关联数据无法删除");
        }
        department.setIsDelete(1);
        departmentMapper.updateByPrimaryKeySelective(department);
        return ok();
    }


    @Override
    public Result findById(Long userId, Long id) {
        if (id == null) {
            return fail(ResultCode.PARAM_IS_INVALID.getMsg());
        }
        Department department = departmentMapper.selectByPrimaryKey(id);
        if (department == null) {
            return fail(ResultCode.RESULE_DATA_NONE.getMsg());
        }
        return ok(department);

    }

    @Override
    public Result pageQuery(Long userId, Integer page, Integer size, String sort, Department department) {
        page = null == page ? 1 : page;
        size = null == size ? 10 : size;
        PageHelper.startPage(page, size);
        Example.Builder builder = new Example.Builder(Department.class);
        builder.where(WeekendSqls.<Department>custom().andEqualTo(Department::getIsDelete, 0));
        if (department != null) {
            if (department.getId() != null) {
                builder.where(WeekendSqls.<Department>custom().andEqualTo(Department::getId, department.getId()));
            }
            if (department.getDepartmentIndex() != null) {
                builder.where(WeekendSqls.<Department>custom().andEqualTo(Department::getDepartmentIndex, department.getDepartmentIndex()));
            }
            if (department.getName() != null && !"".equals(department.getName().trim())) {
                builder.where(WeekendSqls.<Department>custom().andLike(Department::getName, "%" + department.getName() + "%"));
            }
            if (department.getDepartmentIndex() == null) {
                builder.where(WeekendSqls.<Department>custom().andIsNull(Department::getParentId));
            } else {
                builder.where(WeekendSqls.<Department>custom().andEqualTo(Department::getParentId, department.getParentId()));
            }
        } else {
            builder.where(WeekendSqls.<Department>custom().andIsNull(Department::getParentId));
        }
        Page<Department> all = (Page<Department>) departmentMapper.selectByExample(builder.build());

        return ok(all.getTotal(),all.getResult());
    }

    @Override
    public Result getParentId(Long userId, Integer parentId) {
        List<Department> departments = departmentMapper.selectChildByParentId(parentId);
        return ok(departments);
    }

    @Override
    public Result findByOnlyAndChildren(Long userId, Integer id) {
        List<Department> departments = departmentMapper.selectSelfAndChildByParentId(id);
        return ok(departments);
    }

    @Override
    public Result getDepartmentId(Long userId, Long id) {
        //数据效验，当前部门是否存在
        Department department = departmentMapper.selectByPrimaryKey(id);
        if (department == null) {
            return fail(ResultCode.RESULE_DATA_NONE.getMsg());
        }
        //查询所有员工信息
        List<Long> userIds = departmentUserInfoMapper.select(DepartmentUserInfo.builder()
				.departmentId(department.getId())
				.build())
				.stream()
                .map(DepartmentUserInfo::getUserId)
				.collect(Collectors.toList());
        Example.Builder builder = new Example.Builder(UserInfo.class);
        builder.where(WeekendSqls.<UserInfo>custom().andIn(UserInfo::getId, userIds));
        List<UserInfo> select = userInfoMapper.selectByExample(builder.build());
        return ok(select);
    }

    @Override
    public int getDepartmentName(String departmentName) {
        Department build = Department.builder().name(departmentName).isDelete(0).build();
        return departmentMapper.selectCount(build);
    }


}

