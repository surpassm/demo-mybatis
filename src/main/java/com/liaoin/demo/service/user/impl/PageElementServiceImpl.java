package com.liaoin.demo.service.user.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.surpassm.common.jackson.Result;
import com.github.surpassm.common.jackson.ResultCode;
import com.liaoin.demo.entity.user.PageElement;
import com.liaoin.demo.entity.user.UserInfo;
import com.liaoin.demo.mapper.user.PageElementMapper;
import com.liaoin.demo.security.BeanConfig;
import com.liaoin.demo.service.user.PageElementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import javax.annotation.Resource;

import static com.github.surpassm.common.jackson.Result.fail;
import static com.github.surpassm.common.jackson.Result.ok;


/**
  * @author mc
  * Create date 2019-11-28 10:47:51
  * Version 1.0
  * Description 页面元素实现类
  */
@Slf4j
@Service
@Transactional(rollbackFor={RuntimeException.class, Exception.class})
public class PageElementServiceImpl implements PageElementService {
    @Resource
    private PageElementMapper pageElementMapper;
    @Resource
	private BeanConfig beanConfig;

    @Override
    public Result insert(String token,PageElement pageElement) {
        if (pageElement == null){
            return fail(ResultCode.PARAM_IS_BLANK.getMsg());
        }
        UserInfo loginUser = beanConfig.getAccessToken(token);
        pageElementMapper.insert(pageElement);
        return ok();
    }

    @Override
    public Result update(String token,PageElement pageElement) {
        if (pageElement == null){
            return fail(ResultCode.PARAM_IS_BLANK.getMsg());
        }
        UserInfo loginUser = beanConfig.getAccessToken(token);
        pageElementMapper.updateByPrimaryKeySelective(pageElement);
        return ok();
    }

    @Override
    public Result deleteGetById(String token,Long id){
        if (id == null){
            return fail(ResultCode.PARAM_IS_BLANK.getMsg());
        }
        PageElement pageElement = pageElementMapper.selectByPrimaryKey(id);
        if(pageElement == null){
            return fail(ResultCode.RESULE_DATA_NONE.getMsg());
        }
        UserInfo loginUser = beanConfig.getAccessToken(token);
        pageElementMapper.updateByPrimaryKeySelective(pageElement);
        return ok();
    }


    @Override
    public Result findById(String token,Long id) {
        if (id == null){
            return fail(ResultCode.PARAM_IS_BLANK.getMsg());
        }
		PageElement pageElement = pageElementMapper.selectByPrimaryKey(id);
        if (pageElement == null){
			return fail(ResultCode.RESULE_DATA_NONE.getMsg());
		}
        return ok(pageElement);

    }

    @Override
    public Result pageQuery(String token,Integer page, Integer size, String sort, PageElement pageElement) {
        page = null  == page ? 1 : page;
        size = null  == size ? 10 : size;
        if (size > 101){
            return fail("num must not be greater than 100");
        }
        if (sort != null && !"".equals(sort.trim())){
			PageHelper.startPage(page, size,sort);
		}else {
			PageHelper.startPage(page, size,"create_time desc");
		}
        Example.Builder builder = new Example.Builder(PageElement.class);
        if(pageElement != null){
        if (pageElement.getId() != null){
            builder.where(WeekendSqls.<PageElement>custom().andEqualTo(PageElement::getId,pageElement.getId()));
        }
        if (pageElement.getName() != null && !"".equals(pageElement.getName().trim())){
            builder.where(WeekendSqls.<PageElement>custom().andLike(PageElement::getName,"%"+pageElement.getName()+"%"));
        }
        }
        Page<PageElement> all = (Page<PageElement>) pageElementMapper.selectByExample(builder.build());
        return ok(all.toPageInfo());
    }
}

