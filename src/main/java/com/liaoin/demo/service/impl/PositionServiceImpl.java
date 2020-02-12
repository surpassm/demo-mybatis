package com.liaoin.demo.service.impl;

import com.github.pagehelper.Page;
import com.liaoin.demo.common.Result;
import com.liaoin.demo.common.ResultCode;
import com.liaoin.demo.domain.PositionDTO;
import com.liaoin.demo.domain.PositionVO;
import com.liaoin.demo.entity.Department;
import com.liaoin.demo.entity.Position;
import com.liaoin.demo.exception.CustomException;
import com.liaoin.demo.mapper.PositionMapper;
import com.liaoin.demo.service.DepartmentService;
import com.liaoin.demo.service.PositionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.liaoin.demo.common.Result.ok;


/**
  * @author mc
  * Create date 2020-02-10 10:15:20
  * Version 1.0
  * Description 职位实现类
  */
@Slf4j
@Service
@Transactional(rollbackFor={RuntimeException.class, Exception.class})
public class PositionServiceImpl extends BaseServiceImpl implements PositionService {
    @Resource
    private PositionMapper positionMapper;
    @Resource
	private DepartmentService departmentService;

    @Override
    public Position insert(Position position) {
        positionMapper.insert(position);
        return position;
    }

    @Override
    public void update(Position position) {
        positionMapper.updateByPrimaryKeySelective(position);
    }

    @Override
    public void deleteById(Long id){
        Optional<Position> byId = this.findById(id);
        if (!byId.isPresent()) {
            throw new CustomException(ResultCode.ERROR.getCode(), ResultCode.RESULT_DATA_NONE.getMsg());
        }
        Position position = byId.get();
        position.setIsDelete(1);
        position.setUpdateTime(LocalDateTime.now());
        this.update(position);
    }


    @Override
    public Optional<Position> findById(Long id) {
        return Optional.ofNullable(positionMapper.selectByPrimaryKey(id));

    }

    @Override
    public Result pageQuery(Integer page, Integer size, String sort, PositionVO positionVO) {
		super.pageQuery(page,size,sort);
        Example.Builder builder = new Example.Builder(Position.class);
        builder.where(WeekendSqls.<Position>custom().andEqualTo(Position::getIsDelete, 0));
        if(positionVO != null){
        }
        Page<Position> all = (Page<Position>) positionMapper.selectByExample(builder.build());
        return ok(all.getTotal(),all.getResult());
    }

    @Override
    public Position insertOrUpdate(PositionVO vo) {
		Position convert = vo.convertTo();
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
	public List<PositionDTO> findAllParent() {
		return positionMapper.findAllParent();
	}

	@Override
	public List<PositionDTO> findAllChild(Long parentId) {
		return positionMapper.findAllChild(parentId);
	}
}

