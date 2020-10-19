package com.liaoin.demo.service.impl;

import com.github.pagehelper.Page;
import com.liaoin.demo.common.Result;
import com.liaoin.demo.common.ResultCode;
import com.liaoin.demo.domain.RoleVO;
import com.liaoin.demo.entity.*;
import com.liaoin.demo.exception.CustomException;
import com.liaoin.demo.mapper.RoleDepartmentMapper;
import com.liaoin.demo.mapper.RoleMapper;
import com.liaoin.demo.mapper.RolePowerMapper;
import com.liaoin.demo.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.liaoin.demo.common.Result.ok;


/**
  * @author mc
  * Create date 2020-02-10 10:15:21
  * Version 1.0
  * Description 角色实现类
  */
@Slf4j
@Service
@Transactional(rollbackFor={RuntimeException.class, Exception.class})
public class RoleServiceImpl extends BaseServiceImpl implements RoleService {
    @Resource
    private RoleMapper roleMapper;
    @Resource
	private RoleDepartmentMapper roleDepartmentMapper;
    @Resource
	private RolePowerMapper rolePowerMapper;

    @Override
    public Role insert(Role role) {
        roleMapper.insert(role);
        return role;
    }

    @Override
    public void update(Role role) {
        roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public void deleteById(Long id){
        Optional<Role> byId = this.findById(id);
        if (!byId.isPresent()) {
            throw new CustomException(ResultCode.ERROR.getCode(), ResultCode.RESULT_DATA_NONE.getMsg());
        }
        Role role = byId.get();
        role.setIsDelete(1);
        role.setDeleteTime(LocalDateTime.now());
        this.update(role);
    }


    @Override
    public Optional<Role> findById(Long id) {
        return Optional.ofNullable(roleMapper.selectByPrimaryKey(id));

    }

    @Override
    public Result pageQuery(Integer page, Integer size, String sort, RoleVO roleVO) {
		super.pageQuery(page,size,sort);
        Example.Builder builder = new Example.Builder(Role.class);
        builder.where(WeekendSqls.<Role>custom().andEqualTo(Role::getIsDelete, 0));
        if(roleVO != null){
        	if (!StringUtils.isEmpty(roleVO.getName().trim())){
				builder.where(WeekendSqls.<Role>custom().andEqualTo(Role::getName, "%"+roleVO.getName()+"%"));
			}
        	if (!StringUtils.isEmpty(roleVO.getDescribes().trim())){
				builder.where(WeekendSqls.<Role>custom().andEqualTo(Role::getDescribes, "%"+roleVO.getDescribes()+"%"));
			}
        }
        Page<Role> all = (Page<Role>) roleMapper.selectByExample(builder.build());
        return ok(all.getTotal(),all.getResult());
    }
    @Override
    public Role insertOrUpdate(RoleVO vo) {
		Role role = vo.convertTo();
		if (role.getId() == null){
			//名称重复效验
			int i = roleMapper.selectCount(Role.builder().name(role.getName()).isDelete(0).build());
			if(i > 0){
				throw new CustomException(ResultCode.DATA_ALREADY_EXISTED.getCode(),ResultCode.DATA_ALREADY_EXISTED.getMsg());
			}
			//初始化数据
			role.setIsDelete(0);
			role.setCreateTime(LocalDateTime.now());
			this.insert(role);
		}else {
			Example.Builder builder = new Example.Builder(Role.class);
			builder.where(WeekendSqls.<Role>custom().andEqualTo(Role::getIsDelete, 0));
			builder.where(WeekendSqls.<Role>custom().andEqualTo(Role::getName, role.getName()));
			builder.where(WeekendSqls.<Role>custom().andNotIn(Role::getId, CollectionUtils.arrayToList(role.getId())));
			int i = roleMapper.selectCountByExample(builder.build());
			if(i > 0){
				throw new CustomException(ResultCode.DATA_ALREADY_EXISTED.getCode(),ResultCode.DATA_ALREADY_EXISTED.getMsg());
			}
			role.setUpdateTime(LocalDateTime.now());
			this.update(role);
		}
        return role;
    }

	@Override
	public void addRoleDepartment(Long roleId, Long departmentId) {
		RoleDepartment build = RoleDepartment.builder().roleId(roleId).departmentId(departmentId).build();
		int i = roleDepartmentMapper.selectCount(build);
		if (i == 0){
			roleDepartmentMapper.insert(build);
		}else {
			throw new CustomException(ResultCode.DATA_ALREADY_EXISTED.getCode(),ResultCode.DATA_ALREADY_EXISTED.getMsg());
		}
	}

	@Override
	public void deleteRoleDepartment(Long roleId, Long departmentId) {
		roleDepartmentMapper.delete(RoleDepartment.builder().roleId(roleId).departmentId(departmentId).build());
	}
	/**
	 * 根据角色ID分页查询部门
	 * @param page 当前页
	 * @param size 大小
	 * @param sort 排序
	 * @param roleId 角色标识
	 * @return Result
	 */
	@Override
	public Result pageQueryDepartment(Integer page, Integer size, String sort, Long roleId) {
		super.pageQuery(page,size,sort);
		Page<Department> all = (Page<Department>) roleMapper.findDepartmentByRoleId(roleId);
		return ok(all.getTotal(),all.getResult());
	}

	@Override
	public void addRolePower(Long roleId, Long powerId) {
		RolePower build = RolePower.builder().roleId(roleId).powerId(powerId).build();
		int i = rolePowerMapper.selectCount(build);
		if (i == 0){
			rolePowerMapper.insert(build);
		}else {
			throw new CustomException(ResultCode.DATA_ALREADY_EXISTED.getCode(),ResultCode.DATA_ALREADY_EXISTED.getMsg());
		}
	}

	@Override
	public void deleteRolePower(Long roleId, Long powerId) {
		rolePowerMapper.delete(RolePower.builder().roleId(roleId).powerId(powerId).build());
	}
	/**
	 * 根据角色ID分页查询权限
	 * @param page 当前页
	 * @param size 大小
	 * @param sort 排序
	 * @param roleId 角色标识
	 * @return Result
	 */
	@Override
	public Result pageQueryPower(Integer page, Integer size, String sort, Long roleId) {
		super.pageQuery(page,size,sort);
		Page<Power> all = (Page<Power>) roleMapper.findPowerByRoleId(roleId);
		return ok(all.getTotal(),all.getResult());
	}

	@Override
	public List<Role> findAll() {
		return roleMapper.select(Role.builder().isDelete(0).isEnable(1).build());
	}

}

