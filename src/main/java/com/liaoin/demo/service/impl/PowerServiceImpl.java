package com.liaoin.demo.service.impl;

import com.github.pagehelper.Page;
import com.liaoin.demo.common.Result;
import com.liaoin.demo.common.ResultCode;
import com.liaoin.demo.domain.PowerVO;
import com.liaoin.demo.entity.*;
import com.liaoin.demo.exception.CustomException;
import com.liaoin.demo.mapper.PowerMapper;
import com.liaoin.demo.mapper.PowerMenuMapper;
import com.liaoin.demo.mapper.PowerOperationsMapper;
import com.liaoin.demo.service.PowerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.liaoin.demo.common.Result.ok;


/**
  * @author mc
  * Create date 2020-02-10 10:15:20
  * Version 1.0
  * Description 权限表实现类
  */
@Slf4j
@Service
@Transactional(rollbackFor={RuntimeException.class, Exception.class})
public class PowerServiceImpl extends BaseServiceImpl implements PowerService {
    @Resource
    private PowerMapper powerMapper;
    @Resource
	private PowerMenuMapper powerMenuMapper;
    @Resource
	private PowerOperationsMapper powerOperationsMapper;

    @Override
    public Power insert(Power power) {
        powerMapper.insert(power);
        return power;
    }

    @Override
    public void update(Power power) {
        powerMapper.updateByPrimaryKeySelective(power);
    }

    @Override
    public void deleteById(Long id){
        Optional<Power> byId = this.findById(id);
        if (!byId.isPresent()) {
            throw new CustomException(ResultCode.ERROR.getCode(), ResultCode.RESULT_DATA_NONE.getMsg());
        }
        Power power = byId.get();
        power.setIsDelete(1);
        power.setUpdateTime(LocalDateTime.now());
        this.update(power);
    }


    @Override
    public Optional<Power> findById(Long id) {
        return Optional.ofNullable(powerMapper.selectByPrimaryKey(id));

    }

    public boolean selectCount(Power power){
    	return powerMapper.selectCount(power) > 0 ;
	}

    @Override
    public Result pageQuery(Integer page, Integer size, String sort, PowerVO powerVO) {
		super.pageQuery(page,size,sort);
        Example.Builder builder = new Example.Builder(Power.class);
        builder.where(WeekendSqls.<Power>custom().andEqualTo(Power::getIsDelete, 0));
        if(powerVO != null){
        }
        Page<Power> all = (Page<Power>) powerMapper.selectByExample(builder.build());
        return ok(all.getTotal(),all.getResult());
    }
    @Override
    public Power insertOrUpdate(PowerVO vo,Long userId) {
		Power convert = vo.convertTo();
		if (convert.getId() == null) {
			//查看当前名称是否存在
			if (!this.selectCount(Power.builder().name(convert.getName()).build())) {
				throw new CustomException(ResultCode.DATA_ALREADY_EXISTED.getCode(), ResultCode.DATA_ALREADY_EXISTED.getMsg());
			}
			convert.setIsDelete(0);
			convert.setCreateTime(LocalDateTime.now());
			convert.setCreateUserId(userId);
			this.insert(convert);
		}else {
			convert.setUpdateTime(LocalDateTime.now());
			convert.setUpdateUserId(userId);
			this.update(convert);
		}
		return convert;
    }
	/**
	 * 添加权限菜单
	 * @param powerId
	 * @param menuId
	 */
	@Override
	public void addPowerMenu(Long powerId, Long menuId) {
		PowerMenu build = PowerMenu.builder().menuId(menuId).powerId(powerId).build();
		if (powerMenuMapper.selectCount(build) == 0){
			powerMenuMapper.insert(build);
		}else {
			throw new CustomException(ResultCode.DATA_ALREADY_EXISTED.getCode(),ResultCode.DATA_ALREADY_EXISTED.getMsg());
		}
	}
	/**
	 * 删除权限菜單
	 * @param powerId
	 * @param menuId
	 */
	@Override
	public void deletePowerMenu(Long powerId, Long menuId) {
		powerMenuMapper.delete(PowerMenu.builder().menuId(menuId).powerId(powerId).build());
	}

	@Override
	public Result pageQueryMenu(Integer page, Integer size, String sort, Long powerId) {
		super.pageQuery(page,size,sort);
		Page<Menu> all = (Page<Menu>) powerMapper.findMenuByPowerId(powerId);
		return ok(all.getTotal(),all.getResult());
	}

	@Override
	public void addPowerOperations(Long powerId, Long operationsId) {
		PowerOperations build = PowerOperations.builder().powerId(powerId).operationsId(operationsId).build();
		if(powerOperationsMapper.selectCount(build) == 0){
			powerOperationsMapper.insert(build);
		}else {
			throw new CustomException(ResultCode.DATA_ALREADY_EXISTED.getCode(),ResultCode.DATA_ALREADY_EXISTED.getMsg());
		}
	}

	@Override
	public void deletePowerOperations(Long powerId, Long operationsId) {
		powerOperationsMapper.delete(PowerOperations.builder().powerId(powerId).operationsId(operationsId).build());
	}

	@Override
	public Result pageQueryOperations(Integer page, Integer size, String sort, Long powerId) {
		super.pageQuery(page,size,sort);
		Page<Operations> all = (Page<Operations>) powerMapper.findOperationsByPowerId(powerId);
		return ok(all.getTotal(),all.getResult());
	}

}

