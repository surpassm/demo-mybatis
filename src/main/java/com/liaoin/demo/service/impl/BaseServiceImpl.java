package com.liaoin.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.liaoin.demo.common.ResultCode;
import com.liaoin.demo.exception.CustomException;

/**
 * @author mc
 * Create date 2020/2/12 8:30
 * Version 1.0
 * Description
 */

public class BaseServiceImpl {

	public void pageQuery(Integer page, Integer size, String sort){
		page = null  == page ? 1 : page;
		size = null  == size ? 10 : size;
		if (size > 101){
			throw new CustomException(ResultCode.DATA_IS_WRONG.getCode(),"num must not be greater than 100");
		}
		if (sort != null && !"".equals(sort.trim())){
			PageHelper.startPage(page, size,sort);
		}else {
			PageHelper.startPage(page, size);
		}
	}
}
