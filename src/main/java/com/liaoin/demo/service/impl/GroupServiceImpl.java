package com.liaoin.demo.service.impl;

import com.github.pagehelper.Page;
import com.liaoin.demo.common.Result;
import com.liaoin.demo.common.ResultCode;
import com.liaoin.demo.domain.GroupDTO;
import com.liaoin.demo.domain.GroupVO;
import com.liaoin.demo.entity.*;
import com.liaoin.demo.exception.CustomException;
import com.liaoin.demo.mapper.GroupDepartmentMapper;
import com.liaoin.demo.mapper.GroupMapper;
import com.liaoin.demo.mapper.GroupMenuMapper;
import com.liaoin.demo.mapper.GroupRoleMapper;
import com.liaoin.demo.service.DepartmentService;
import com.liaoin.demo.service.GroupService;
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

import static com.liaoin.demo.common.Result.ok;


/**
  * @author mc
  * Create date 2020-02-10 10:15:20
  * Version 1.0
  * Description 权限实现类
  */
@Slf4j
@Service
@Transactional(rollbackFor={RuntimeException.class, Exception.class})
public class GroupServiceImpl extends BaseServiceImpl implements GroupService {
    @Resource
    private GroupMapper groupMapper;
    @Resource
	private GroupDepartmentMapper groupDepartmentMapper;
    @Resource
	private GroupRoleMapper groupRoleMapper;
    @Resource
	private GroupMenuMapper groupMenuMapper;
    @Resource
	private DepartmentService departmentService;
    @Resource
	private MenuService menuService;



    @Override
    public Group insert(Group group) {
        groupMapper.insert(group);
        return group;
    }

    @Override
    public void update(Group group) {
        groupMapper.updateByPrimaryKeySelective(group);
    }

    @Override
    public void deleteById(Long id){
        Optional<Group> byId = this.findById(id);
        if (!byId.isPresent()) {
            throw new CustomException(ResultCode.ERROR.getCode(), ResultCode.RESULT_DATA_NONE.getMsg());
        }
        Group group = byId.get();
        group.setIsDelete(1);
        this.update(group);
    }


    @Override
    public Optional<Group> findById(Long id) {
        return Optional.ofNullable(groupMapper.selectByPrimaryKey(id));

    }

    @Override
    public Result pageQuery(Integer page, Integer size, String sort, GroupVO groupVO) {
		super.pageQuery(page,size,sort);
        Example.Builder builder = new Example.Builder(Group.class);
        builder.where(WeekendSqls.<Group>custom().andEqualTo(Group::getIsDelete, 0));
        if(groupVO != null){
        }
		builder.where(WeekendSqls.<Group>custom().andIsNull(Group::getParentId));
        Page<Group> all = (Page<Group>) groupMapper.selectByExample(builder.build());
        return ok(all.getTotal(),all.getResult());
    }

    @Override
    public Group insertOrUpdate(GroupVO vo) {
		Group group = vo.convertTo();
		//父级效验
		Long parentId = group.getParentId();
		if (parentId != null){
			if (!findById(parentId).isPresent()){
				throw new CustomException(ResultCode.RESULT_DATA_NONE.getCode(),ResultCode.RESULT_DATA_NONE.getMsg());
			}
		}
		if (group.getId() == null){
			group.setIsDelete(0);
			this.insert(group);
		}else {
			this.update(group);
		}
		return group;
    }

	@Override
	public List<GroupDTO> findAllParent() {
		return groupMapper.findAllParent();
	}

	@Override
	public List<GroupDTO> findAllChild(Long parentId) {
		return groupMapper.findAllChild(parentId);
	}

	@Override
	public void addGroupDepartment(Long groupId, Long departmentId) {
		GroupDepartment groupDepartment = GroupDepartment.builder().groupId(groupId).departmentId(departmentId).build();
		int i = groupDepartmentMapper.selectCount(groupDepartment);
		if ( i == 0){
			List<GroupDepartment> groupDepartments = new ArrayList<>();
			List<Department> departments = departmentService.findByParentId(departmentId);
			for (Department department : departments) {
				groupDepartments.add(GroupDepartment.builder().groupId(groupId).departmentId(department.getId()).build());
			}
			groupDepartments.add(groupDepartment);
			groupDepartmentMapper.insertList(groupDepartments);
		}else {
			throw new CustomException(ResultCode.DATA_ALREADY_EXISTED.getCode(),ResultCode.DATA_ALREADY_EXISTED.getMsg());
		}
	}

	@Override
	public void deleteGroupDepartment(Long groupId, Long departmentId) {
		List<Department> departments = departmentService.findByParentId(departmentId);
		for (Department department : departments) {
			groupDepartmentMapper.delete(GroupDepartment.builder().departmentId(department.getId()).groupId(groupId).build());
		}
		groupDepartmentMapper.delete(GroupDepartment.builder().departmentId(departmentId).groupId(groupId).build());
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
	public Result pageQueryDepartment(Integer page, Integer size, String sort, Long groupId) {
		super.pageQuery(page,size,sort);
		Page<Department> all = (Page<Department>) groupMapper.findDepartmentByGroupId(groupId);
		return ok(all.getTotal(),all.getResult());
	}
	/**
	 * 添加组菜单
	 * @param groupId
	 * @param menuId
	 */
	@Override
	public void addGroupMenu(Long groupId, Long menuId) {
		GroupMenu build = GroupMenu.builder().groupId(groupId).menuId(menuId).build();
		int i = groupMenuMapper.selectCount(build);
		if ( i == 0){
			List<GroupMenu> groupMenus = new ArrayList<>();
			List<Menu> menus = menuService.findByParentId(menuId);
			for (Menu menu : menus) {
				groupMenus.add(GroupMenu.builder().groupId(groupId).menuId(menu.getId()).build());
			}
			groupMenus.add(build);
			groupMenuMapper.insertList(groupMenus);
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
			groupMenuMapper.delete(GroupMenu.builder().groupId(groupId).menuId(menu.getId()).build());
		}
		groupMenuMapper.delete(GroupMenu.builder().groupId(groupId).menuId(menuId).build());
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
	public Result pageQueryMenu(Integer page, Integer size, String sort, Long groupId) {
		super.pageQuery(page,size,sort);
		Page<Menu> all = (Page<Menu>) groupMapper.findMenuByGroupId(groupId);
		return ok(all.getTotal(),all.getResult());
	}

	/**
	 * 添加组角色
	 * @param groupId
	 * @param roleId
	 */
	@Override
	public void addGroupRole(Long groupId, Long roleId) {
		GroupRole build = GroupRole.builder().groupId(groupId).roleId(roleId).build();
		int i = groupRoleMapper.selectCount(build);
		if ( i == 0){
			groupRoleMapper.insert(build);
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
		groupRoleMapper.delete(GroupRole.builder().groupId(groupId).roleId(roleId).build());
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
	public Result pageQueryRole(Integer page, Integer size, String sort, Long groupId) {
		super.pageQuery(page,size,sort);
		Page<Role> all = (Page<Role>) groupMapper.findRoleByGroupId(groupId);
		return ok(all.getTotal(),all.getResult());
	}
}

