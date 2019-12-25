package com.liaoin.demo.service.user.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.liaoin.demo.common.Result;
import com.liaoin.demo.common.ResultCode;
import com.liaoin.demo.domain.user.DepartmentDTO;
import com.liaoin.demo.entity.user.Department;
import com.liaoin.demo.entity.user.DepartmentUserInfo;
import com.liaoin.demo.entity.user.UserInfo;
import com.liaoin.demo.exception.CustomException;
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
import javax.validation.constraints.NotNull;
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


    /**
     * 判断名称是否存在
     *
     * @param name name
     * @return boolean
     */
    @Override
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
    @Override
    public Department getDepartmentId(Long id){
        return departmentMapper.selectByPrimaryKey(id);
    }

    /**
     * 新增
     *
     * @param department 原始对象
     * @return 新对象
     */
    @Override
    public Department insert(Department department) {
        departmentMapper.insert(department);
        return department;
    }

    /**
     * 根据修改数据
     *
     * @param department 待修改对象
     * @return 返回
     */
    public Department update(Department department) {
        departmentMapper.updateByPrimaryKeySelective(department);
        return department;
    }

    /**
     * 根据主键查询
     *
     * @param id 系统标识
     * @return Optional
     */
    public Optional<Department> findById(@NotNull Long id) {
        return Optional.ofNullable(departmentMapper.selectByPrimaryKey(id));

    }

        /**
         * 新增
         *
         * @param dto 原始对象
         * @return 新对象
         */
    @Override
    public Department insert(Long userId,DepartmentDTO dto) {
        //查询名称是否存在
        if (this.isGetName(dto.getName())){
            throw new CustomException(ResultCode.ERROR.getCode(),ResultCode.DATA_ALREADY_EXISTED.getMsg());
        }
        //查询父级是否存在
        if (dto.getParentId() != null){
            Department dep = this.getDepartmentId(dto.getParentId());
            if (dep == null){
                throw new CustomException(ResultCode.ERROR.getCode(),ResultCode.DATA_ALREADY_EXISTED.getMsg());
            }
        }
        //对象转化
        Department department = dto.convertTo();
        //数据初始化
        department.setIsDelete(0);
        department.setCreateTime(LocalDateTime.now());
        return insert(department);
    }

    /**
     * 修改
     *
     * @param userId 当前登录用户
     * @param dto 对象
     * @return 修改后对象
     */
    @Override
    public Department update(Long userId, DepartmentDTO dto) {
        if (dto == null) {
            throw new CustomException(ResultCode.PARAM_IS_INVALID.getMsg());
        }
        //效验当前名称是否重复
        Example.Builder builder = new Example.Builder(Department.class);
        builder.where(WeekendSqls.<Department>custom().andEqualTo(Department::getIsDelete, 0));
        builder.where(WeekendSqls.<Department>custom().andEqualTo(Department::getName, dto.getName()));
        builder.where(WeekendSqls.<Department>custom().andNotIn(Department::getId, Collections.singletonList(dto.getId())));
        int i = departmentMapper.selectCountByExample(builder.build());
        if ( i > 0){
            throw new CustomException(ResultCode.DATA_ALREADY_EXISTED.getMsg());
        }
        //查询父级是否存在
        if (dto.getParentId() != null){
            Department dep = this.getDepartmentId(dto.getParentId());
            if (dep == null){
                throw new CustomException(ResultCode.ERROR.getCode(),ResultCode.DATA_ALREADY_EXISTED.getMsg());
            }
        }
        Department department = dto.convertTo();
        return this.update(department);
    }


    @Override
    public Result deleteGetById(Long userId, Long id) {
        if (id == null) {
            throw new CustomException(ResultCode.PARAM_IS_INVALID.getMsg());
        }
        Optional<Department> byId = this.findById(id);
        if (!byId.isPresent()) {
            throw new CustomException(ResultCode.RESULE_DATA_NONE.getMsg());
        }
        Department department = byId.get();

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

