package com.liaoin.demo.service.impl;

import com.github.pagehelper.Page;
import com.liaoin.demo.common.R;
import com.liaoin.demo.common.ResultCode;
import com.liaoin.demo.domain.PositionsDTO;
import com.liaoin.demo.domain.PositionsVO;
import com.liaoin.demo.entity.Department;
import com.liaoin.demo.entity.Positions;
import com.liaoin.demo.exception.CustomException;
import com.liaoin.demo.mapper.PositionsMapper;
import com.liaoin.demo.service.DepartmentService;
import com.liaoin.demo.service.PositionsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.liaoin.demo.common.R.ok;


/**
  * @author mc
  * Create date 2020-02-10 10:15:20
  * Version 1.0
  * Description 职位实现类
  */
@Slf4j
@Service
@Transactional(rollbackFor={RuntimeException.class, Exception.class})
public class PositionsServiceImpl extends BaseServiceImpl implements PositionsService {
    @Resource
    private PositionsMapper positionsMapper;
    @Resource
	private DepartmentService departmentService;

    @Override
    public Positions insert(Positions positions) {
        positionsMapper.insert(positions);
        return positions;
    }

    @Override
    public void update(Positions positions) {
        positionsMapper.updateByPrimaryKeySelective(positions);
    }

    @Override
    public void deleteById(Long id){
        Optional<Positions> byId = this.findById(id);
        if (!byId.isPresent()) {
            throw new CustomException(ResultCode.ERROR.getCode(), ResultCode.RESULT_DATA_NONE.getMsg());
        }
        Positions positions = byId.get();
        positions.setIsDelete(1);
        positions.setUpdateTime(LocalDateTime.now());
        this.update(positions);
    }


    @Override
    public Optional<Positions> findById(Long id) {
        return Optional.ofNullable(positionsMapper.selectByPrimaryKey(id));

    }

    @Override
    public R pageQuery(Integer page, Integer size, String sort, PositionsVO positionsVO) {
		super.pageQuery(page,size,sort);
        Example.Builder builder = new Example.Builder(Positions.class);
        builder.where(WeekendSqls.<Positions>custom().andEqualTo(Positions::getIsDelete, 0));
        if(positionsVO != null){
        }
        Page<Positions> all = (Page<Positions>) positionsMapper.selectByExample(builder.build());
        return ok(all.getTotal(),all.getResult());
    }

    @Override
    public Positions insertOrUpdate(PositionsVO vo) {
		Positions convert = vo.convertTo();
		//父级效验
		Long parentId = convert.getParentId();
		if (parentId != null){
			if (!findById(parentId).isPresent()){
				throw new CustomException(ResultCode.RESULT_DATA_NONE.getCode(),ResultCode.RESULT_DATA_NONE.getMsg());
			}
		}
		//效验部门是否存在
		if (!departmentService.selectCount(Department.builder().id(convert.getDepartmentId()).build())){
			throw new CustomException(ResultCode.RESULT_DATA_NONE.getCode(),"部门不存在");
		}
		if (convert.getId() == null){
			convert.setIsDelete(0);
			convert.setCreateTime(LocalDateTime.now());
			this.insert(convert);
		}else {
			this.update(convert);
		}
		return convert;
    }


	@Override
	public List<PositionsDTO> findAllParent() {
		return positionsMapper.findAllParent();
	}

	@Override
	public List<PositionsDTO> findAllChild(Long parentId) {
		return positionsMapper.findAllChild(parentId);
	}
}

