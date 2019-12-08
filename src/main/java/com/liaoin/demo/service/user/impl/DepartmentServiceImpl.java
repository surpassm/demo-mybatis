package com.liaoin.demo.service.user.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.surpassm.common.jackson.Result;
import com.github.surpassm.common.jackson.ResultCode;
import com.github.surpassm.common.service.InsertView;
import com.github.surpassm.common.service.UpdateView;
import com.liaoin.demo.entity.user.Department;
import com.liaoin.demo.entity.user.DepartmentUserInfo;
import com.liaoin.demo.entity.user.UserInfo;
import com.liaoin.demo.mapper.user.DepartmentMapper;
import com.liaoin.demo.mapper.user.DepartmentUserInfoMapper;
import com.liaoin.demo.mapper.user.UserInfoMapper;
import com.liaoin.demo.security.BeanConfig;
import com.liaoin.demo.service.user.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

import static com.github.surpassm.common.jackson.Result.fail;
import static com.github.surpassm.common.jackson.Result.ok;


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
    private BeanConfig beanConfig;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private DepartmentUserInfoMapper departmentUserInfoMapper;

    @Override
    public Department insert( Department department) {
        department.setIsDelete(0);
        departmentMapper.insert(department);
        return department;
    }

    @Override
    public Result update(String accessToken, Department department) {
        if (department == null) {
            return fail(ResultCode.PARAM_IS_INVALID.getMsg());
        }
        Department department1 = departmentMapper.selectByPrimaryKey(department.getId());
        if (department1.getIsDelete() == 1) {
            return fail(ResultCode.RESULE_DATA_NONE.getMsg());
        }

        UserInfo loginUserInfo = beanConfig.getAccessToken(accessToken);

        Example.Builder builder = new Example.Builder(Department.class);
        builder.where(WeekendSqls.<Department>custom().andEqualTo(Department::getIsDelete, 0));
        builder.where(WeekendSqls.<Department>custom().andEqualTo(Department::getName, department.getName()));
        builder.where(WeekendSqls.<Department>custom().andNotIn(Department::getId, Collections.singletonList(department.getId())));

        List<Department> selectCount = departmentMapper.selectByExample(builder.build());
        if (selectCount.size() != 0) {
            return fail(ResultCode.RESULE_DATA_NONE.getMsg());
        }
        if (isEnableParent(department)) {
            return fail(ResultCode.RESULE_DATA_NONE.getMsg());
        }

        departmentMapper.updateByPrimaryKeySelective(department);
        return ok();
    }

    private boolean isEnableParent(Department department) {
        if (department.getParentId() != null) {
            Department buildDepartment = Department.builder().id(department.getParentId()).build();
            buildDepartment.setIsDelete(0);
            int buildDepartmentCount = departmentMapper.selectCount(buildDepartment);
            return buildDepartmentCount == 0;
        }
        return false;
    }

    @Override
    public Result deleteGetById(String accessToken, Long id) {
        if (id == null) {
            return fail(ResultCode.PARAM_IS_INVALID.getMsg());
        }
        Department department = departmentMapper.selectByPrimaryKey(id);
        if (department == null) {
            return fail(ResultCode.RESULE_DATA_NONE.getMsg());
        }
        UserInfo loginUserInfo = beanConfig.getAccessToken(accessToken);

        Department build = Department.builder().parentId(department.getId()).build();
        build.setIsDelete(0);
        int selectCount = departmentMapper.selectCount(build);
        if (selectCount != 0) {
            return fail("存在下级关联数据无法删除");
        }
//        int userCount = userInfoMapper.selectCount(UserInfo.builder().departmentId(id).isDelete(0).build());
//        if (userCount != 0) {
//            return fail("存在关联用户，无法删除");
//        }
        department.setIsDelete(1);
        departmentMapper.updateByPrimaryKeySelective(department);
        return ok();
    }


    @Override
    public Result findById(String accessToken, Long id) {
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
    public Result pageQuery(String accessToken, Integer page, Integer size, String sort, Department department) {
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
        return ok(all.toPageInfo());
    }

    @Override
    public Result getParentId(String accessToken, Integer parentId) {
        List<Department> departments = departmentMapper.selectChildByParentId(parentId);
        return ok(departments);
    }

    @Override
    public Result findByOnlyAndChildren(String accessToken, Integer id) {
        List<Department> departments = departmentMapper.selectSelfAndChildByParentId(id);
        return ok(departments);
    }

    @Override
    public Result getDepartmentId(String accessToken, Long id) {
        beanConfig.getAccessToken(accessToken);
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

