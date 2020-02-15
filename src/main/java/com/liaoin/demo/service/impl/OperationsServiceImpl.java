package com.liaoin.demo.service.impl;

import com.github.pagehelper.Page;
import com.liaoin.demo.common.Result;
import com.liaoin.demo.common.ResultCode;
import com.liaoin.demo.domain.OperationsDTO;
import com.liaoin.demo.domain.OperationsVO;
import com.liaoin.demo.entity.Operations;
import com.liaoin.demo.exception.CustomException;
import com.liaoin.demo.mapper.OperationsMapper;
import com.liaoin.demo.service.OperationsService;
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
  * Description 后台功能接口实现类
  */
@Slf4j
@Service
@Transactional(rollbackFor={RuntimeException.class, Exception.class})
public class OperationsServiceImpl extends BaseServiceImpl implements OperationsService {
    @Resource
    private OperationsMapper operationsMapper;

    @Override
    public Operations insert(Operations operations) {
        operationsMapper.insert(operations);
        return operations;
    }

    @Override
    public void update(Operations operations) {
        operationsMapper.updateByPrimaryKeySelective(operations);
    }

    @Override
    public void deleteById(Long id){
        Optional<Operations> byId = this.findById(id);
        if (!byId.isPresent()) {
            throw new CustomException(ResultCode.ERROR.getCode(), ResultCode.RESULT_DATA_NONE.getMsg());
        }
        Operations operations = byId.get();
        this.update(operations);
    }


    @Override
    public Optional<Operations> findById(Long id) {
        return Optional.ofNullable(operationsMapper.selectByPrimaryKey(id));

    }

    @Override
    public Result pageQuery(Integer page, Integer size, String sort, OperationsVO operationsVO) {
		super.pageQuery(page,size,sort);
        Example.Builder builder = new Example.Builder(Operations.class);
		builder.where(WeekendSqls.<Operations>custom().andEqualTo(Operations::getIsDelete,0));
        if(operationsVO != null){
        }
		builder.where(WeekendSqls.<Operations>custom().andIsNull(Operations::getParentId));
        Page<Operations> all = (Page<Operations>) operationsMapper.selectByExample(builder.build());
        return ok(all.getTotal(),all.getResult());
    }

    @Override
    public Operations insertOrUpdate(OperationsVO vo) {
		Operations convert = vo.convertTo();

		//父级效验
		Long parentId = convert.getParentId();
		if (parentId != null){
			if (!findById(parentId).isPresent()){
				throw new CustomException(ResultCode.RESULT_DATA_NONE.getCode(),ResultCode.RESULT_DATA_NONE.getMsg());
			}
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
	public List<OperationsDTO> findAllParent() {
		return operationsMapper.findAllParent();
	}

	@Override
	public List<OperationsDTO> findAllChild(Long parentId) {
		return operationsMapper.findAllChild(parentId);
	}

	@Override
	public List<Operations> findByUserId(Long userId) {
		return operationsMapper.findByUserId(userId);
	}
}

