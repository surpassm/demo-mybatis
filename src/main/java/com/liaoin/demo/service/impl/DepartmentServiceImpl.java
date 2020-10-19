package com.liaoin.demo.service.impl;

import com.github.pagehelper.Page;
import com.liaoin.demo.common.R;
import com.liaoin.demo.common.ResultCode;
import com.liaoin.demo.domain.DepartmentDTO;
import com.liaoin.demo.domain.DepartmentVO;
import com.liaoin.demo.entity.Department;
import com.liaoin.demo.entity.DepartmentUserInfo;
import com.liaoin.demo.entity.UserInfo;
import com.liaoin.demo.exception.CustomException;
import com.liaoin.demo.mapper.DepartmentMapper;
import com.liaoin.demo.mapper.DepartmentUserInfoMapper;
import com.liaoin.demo.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static com.liaoin.demo.common.R.ok;


/**
  * @author mc
  * Create date 2020-02-10 10:15:20
  * Version 1.0
  * Description 部门实现类
  */
@Slf4j
@Service
@Transactional(rollbackFor={RuntimeException.class, Exception.class})
public class DepartmentServiceImpl extends BaseServiceImpl implements DepartmentService  {
    @Resource
    private DepartmentMapper departmentMapper;
    @Resource
	private DepartmentUserInfoMapper departmentUserInfoMapper;

    @Override
    public Department insert(Department department) {
        departmentMapper.insert(department);
        return department;
    }

    @Override
    public void update(Department department) {
        departmentMapper.updateByPrimaryKeySelective(department);
    }

    @Override
    public void deleteById(Long id){
        Optional<Department> byId = this.findById(id);
        if (!byId.isPresent()) {
            throw new CustomException(ResultCode.ERROR.getCode(), ResultCode.RESULT_DATA_NONE.getMsg());
        }
        Department department = byId.get();
        department.setIsDelete(1);
        this.update(department);
    }


    @Override
    public Optional<Department> findById(Long id) {
        return Optional.ofNullable(departmentMapper.selectByPrimaryKey(id));

    }

    @Override
    public R pageQuery(Integer page, Integer size, String sort, DepartmentVO departmentVO) {
		super.pageQuery(page,size,sort);
        Example.Builder builder = new Example.Builder(Department.class);
        builder.where(WeekendSqls.<Department>custom().andEqualTo(Department::getIsDelete, 0));
        if(departmentVO != null){
        	//todo 根据业务编写
        }
		builder.where(WeekendSqls.<Department>custom().andIsNull(Department::getParentId));
        Page<Department> all = (Page<Department>) departmentMapper.selectByExample(builder.build());
        return ok(all.getTotal(),all.getResult());
    }

    @Override
    public Department insertOrUpdate(DepartmentVO vo) {
		Department department = vo.convertTo();
		//父级效验
		Long parentId = department.getParentId();
		if (parentId != null){
			if (!findById(parentId).isPresent()){
				throw new CustomException(ResultCode.RESULT_DATA_NONE.getCode(),ResultCode.RESULT_DATA_NONE.getMsg());
			}
		}
		if (department.getId() != null){
			department.setIsDelete(0);
			this.insert(department);
		}else {
			this.update(department);
		}
		return department;
    }

	@Override
	public List<DepartmentDTO> findAllParent() {
		return departmentMapper.findAllParent();
	}

	@Override
	public List<DepartmentDTO> findAllChild(Long parentId) {
		return departmentMapper.findAllChild(parentId);
	}

	@Override
	public boolean selectCount(Department department) {
		return departmentMapper.selectCount(department) > 0 ;
	}

	@Override
	public void addDepartmentPerson(Long departmentId, Long userInfoId) {
		//效验部门是否存在
		if (!findById(departmentId).isPresent()){
			throw new CustomException(ResultCode.DATA_ALREADY_EXISTED.getCode(), ResultCode.DATA_ALREADY_EXISTED.getMsg());
		}
		DepartmentUserInfo departmentUserInfo = DepartmentUserInfo.builder().departmentId(departmentId).userId(userInfoId).build();
		if (departmentUserInfoMapper.selectCount(departmentUserInfo) == 0 ){
			departmentUserInfoMapper.insert(departmentUserInfo);
		}
	}


	@Override
	public void deleteDepartmentPerson(Long departmentId, Long userInfoId) {
		departmentUserInfoMapper.delete(DepartmentUserInfo.builder().departmentId(departmentId).userId(userInfoId).build());
	}
	@Override
	public R pageQueryDepartmentPerson(Long departmentId, Integer page, Integer size, String sort) {
		super.pageQuery(page,size,sort);
		Page<UserInfo> all = (Page<UserInfo>) departmentMapper.pageQueryDepartmentPerson(departmentId);
		return ok(all.getTotal(),all.getResult());
	}

	@Override
	public List<Department> findByParentId(Long departmentId) {
		List<Department> select = departmentMapper.select(Department.builder().isDelete(0).parentId(departmentId).build());
		if (select.size() > 0){
			for (Department department : select) {
				List<Department> byParentId = findByParentId(department.getId());
				if (byParentId.size() > 0) {
					select.addAll(byParentId);
				}
			}
		}
		return select;
	}
}

