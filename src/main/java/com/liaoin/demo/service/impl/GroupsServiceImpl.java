package com.liaoin.demo.service.impl;

import com.github.pagehelper.Page;
import com.liaoin.demo.common.R;
import com.liaoin.demo.common.ResultCode;
import com.liaoin.demo.domain.GroupsDTO;
import com.liaoin.demo.domain.GroupsVO;
import com.liaoin.demo.entity.*;
import com.liaoin.demo.exception.CustomException;
import com.liaoin.demo.mapper.GroupsDepartmentMapper;
import com.liaoin.demo.mapper.GroupsMapper;
import com.liaoin.demo.mapper.GroupsMenuMapper;
import com.liaoin.demo.mapper.GroupsRoleMapper;
import com.liaoin.demo.service.DepartmentService;
import com.liaoin.demo.service.GroupsService;
import com.liaoin.demo.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.liaoin.demo.common.R.ok;


/**
  * @author mc
  * Create date 2020-02-10 10:15:20
  * Version 1.0
  * Description 权限实现类
  */
@Slf4j
@Service
@Transactional(rollbackFor={RuntimeException.class, Exception.class})
public class GroupsServiceImpl extends BaseServiceImpl implements GroupsService {
    @Resource
    private GroupsMapper groupsMapper;
    @Resource
	private GroupsDepartmentMapper groupsDepartmentMapper;
    @Resource
	private GroupsRoleMapper groupsRoleMapper;
    @Resource
	private GroupsMenuMapper groupsMenuMapper;
    @Resource
	private DepartmentService departmentService;
    @Resource
	private MenuService menuService;



    @Override
    public Groups insert(Groups groups) {
        groupsMapper.insert(groups);
        return groups;
    }

    @Override
    public void update(Groups groups) {
        groupsMapper.updateByPrimaryKeySelective(groups);
    }

    @Override
    public void deleteById(Long id){
        Optional<Groups> byId = this.findById(id);
        if (!byId.isPresent()) {
            throw new CustomException(ResultCode.ERROR.getCode(), ResultCode.RESULT_DATA_NONE.getMsg());
        }
        Groups groups = byId.get();
        groups.setIsDelete(1);
        this.update(groups);
    }


    @Override
    public Optional<Groups> findById(Long id) {
        return Optional.ofNullable(groupsMapper.selectByPrimaryKey(id));

    }

    @Override
    public R pageQuery(Integer page, Integer size, String sort, GroupsVO groupsVO) {
		super.pageQuery(page,size,sort);
        Example.Builder builder = new Example.Builder(Groups.class);
        builder.where(WeekendSqls.<Groups>custom().andEqualTo(Groups::getIsDelete, 0));
        if(groupsVO != null){
        }
		builder.where(WeekendSqls.<Groups>custom().andIsNull(Groups::getParentId));
        Page<Groups> all = (Page<Groups>) groupsMapper.selectByExample(builder.build());
        return ok(all.getTotal(),all.getResult());
    }

    @Override
    public Groups insertOrUpdate(GroupsVO vo) {
		Groups groups = vo.convertTo();
		//父级效验
		Long parentId = groups.getParentId();
		if (parentId != null){
			if (!findById(parentId).isPresent()){
				throw new CustomException(ResultCode.RESULT_DATA_NONE.getCode(),ResultCode.RESULT_DATA_NONE.getMsg());
			}
		}
		if (groups.getId() == null){
			groups.setIsDelete(0);
			this.insert(groups);
		}else {
			this.update(groups);
		}
		return groups;
    }

	@Override
	public List<GroupsDTO> findAllParent() {
		return groupsMapper.findAllParent();
	}

	@Override
	public List<GroupsDTO> findAllChild(Long parentId) {
		return groupsMapper.findAllChild(parentId);
	}

	@Override
	public void addGroupDepartment(Long groupId, Long departmentId) {
		GroupsDepartment groupsDepartment = GroupsDepartment.builder().groupId(groupId).departmentId(departmentId).build();
		int i = groupsDepartmentMapper.selectCount(groupsDepartment);
		if ( i == 0){
			List<GroupsDepartment> groupsDepartments = new ArrayList<>();
			List<Department> departments = departmentService.findByParentId(departmentId);
			for (Department department : departments) {
				groupsDepartments.add(GroupsDepartment.builder().groupId(groupId).departmentId(department.getId()).build());
			}
			groupsDepartments.add(groupsDepartment);
			groupsDepartmentMapper.insertList(groupsDepartments);
		}else {
			throw new CustomException(ResultCode.DATA_ALREADY_EXISTED.getCode(),ResultCode.DATA_ALREADY_EXISTED.getMsg());
		}
	}

	@Override
	public void deleteGroupDepartment(Long groupId, Long departmentId) {
		List<Department> departments = departmentService.findByParentId(departmentId);
		for (Department department : departments) {
			groupsDepartmentMapper.delete(GroupsDepartment.builder().departmentId(department.getId()).groupId(groupId).build());
		}
		groupsDepartmentMapper.delete(GroupsDepartment.builder().departmentId(departmentId).groupId(groupId).build());
	}
	/**
	 * 根据组ID分页查询部门
	 * @param page
	 * @param size
	 * @param sort
	 * @param groupId
	 * @return
	 */
	@Override
	public R pageQueryDepartment(Integer page, Integer size, String sort, Long groupId) {
		super.pageQuery(page,size,sort);
		Page<Department> all = (Page<Department>) groupsMapper.findDepartmentByGroupId(groupId);
		return ok(all.getTotal(),all.getResult());
	}
	/**
	 * 添加组菜单
	 * @param groupId
	 * @param menuId
	 */
	@Override
	public void addGroupMenu(Long groupId, Long menuId) {
		GroupsMenu build = GroupsMenu.builder().groupId(groupId).menuId(menuId).build();
		int i = groupsMenuMapper.selectCount(build);
		if ( i == 0){
			List<GroupsMenu> groupsMenus = new ArrayList<>();
			List<Menu> menus = menuService.findByParentId(menuId);
			for (Menu menu : menus) {
				groupsMenus.add(GroupsMenu.builder().groupId(groupId).menuId(menu.getId()).build());
			}
			groupsMenus.add(build);
			groupsMenuMapper.insertList(groupsMenus);
		}else {
			throw new CustomException(ResultCode.DATA_ALREADY_EXISTED.getCode(),ResultCode.DATA_ALREADY_EXISTED.getMsg());
		}
	}
	/**
	 * 删除组菜單
	 * @param groupId
	 * @param menuId
	 */
	@Override
	public void deleteGroupMenu(Long groupId, Long menuId) {
		List<Menu> menus = menuService.findByParentId(menuId);
		for (Menu menu : menus) {
			groupsMenuMapper.delete(GroupsMenu.builder().groupId(groupId).menuId(menu.getId()).build());
		}
		groupsMenuMapper.delete(GroupsMenu.builder().groupId(groupId).menuId(menuId).build());
	}
	/**
	 * 根据组ID分页查询菜单
	 * @param page
	 * @param size
	 * @param sort
	 * @param groupId
	 * @return
	 */
	@Override
	public R pageQueryMenu(Integer page, Integer size, String sort, Long groupId) {
		super.pageQuery(page,size,sort);
		Page<Menu> all = (Page<Menu>) groupsMapper.findMenuByGroupId(groupId);
		return ok(all.getTotal(),all.getResult());
	}

	/**
	 * 添加组角色
	 * @param groupId
	 * @param roleId
	 */
	@Override
	public void addGroupRole(Long groupId, Long roleId) {
		GroupsRole build = GroupsRole.builder().groupId(groupId).roleId(roleId).build();
		int i = groupsRoleMapper.selectCount(build);
		if ( i == 0){
			groupsRoleMapper.insert(build);
		}else {
			throw new CustomException(ResultCode.DATA_ALREADY_EXISTED.getCode(),ResultCode.DATA_ALREADY_EXISTED.getMsg());
		}
	}
	/**
	 * 添加组角色
	 * @param groupId
	 * @param roleId
	 */
	@Override
	public void deleteGroupRole(Long groupId, Long roleId) {
		groupsRoleMapper.delete(GroupsRole.builder().groupId(groupId).roleId(roleId).build());
	}
	/**
	 * 根据组ID分页查询角色
	 * @param page
	 * @param size
	 * @param sort
	 * @param groupId
	 * @return
	 */
	@Override
	public R pageQueryRole(Integer page, Integer size, String sort, Long groupId) {
		super.pageQuery(page,size,sort);
		Page<Role> all = (Page<Role>) groupsMapper.findRoleByGroupId(groupId);
		return ok(all.getTotal(),all.getResult());
	}
}

