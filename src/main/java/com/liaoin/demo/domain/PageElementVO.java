package com.liaoin.demo.domain;


import com.liaoin.demo.annotation.Convert;
import com.liaoin.demo.entity.PageElement;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;


/**
* @author mc
* Create date 2020-02-10 10:15:20
* Version 1.0
* Description 页面元素VO数据流
*/

@Data
@ApiModel(value = "页面元素VO")
public class PageElementVO implements Serializable {



	private Long id;
	private String name;




    public PageElement convertTo(){
        ConvertImpl impl = new ConvertImpl();
        return impl.doForward(this);
    }

    public PageElementVO convertFor(PageElement pageElement){
        ConvertImpl impl = new ConvertImpl();
        return impl.doBackward(pageElement,this);
    }

    private static class ConvertImpl implements Convert<PageElementVO, PageElement> {
        @Override
        public PageElement doForward(PageElementVO vo) {
            PageElement pageElement = new PageElement();
            BeanUtils.copyProperties(vo,pageElement);
            return pageElement;
        }
        @Override
        public PageElementVO doBackward(PageElement pageElement) {
                PageElementVO vo = new PageElementVO();
                BeanUtils.copyProperties(pageElement, vo);
                return vo;
        }
        public PageElementVO doBackward(PageElement pageElement, PageElementVO vo) {
                BeanUtils.copyProperties(pageElement, vo);
                return vo;
        }
    }




}
