package com.liaoin.demo.domain.user;


import com.liaoin.demo.annotation.Convert;
import com.liaoin.demo.entity.user.PageElement;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
* @author mc
* Create date 2019-12-12 21:02:27
* Version 1.0
* Description 页面元素数据流
*/
@Getter
@Setter
@ApiModel(value = "页面元素")
public class PageElementDTO implements Serializable {


    public PageElement convertTo(){
        ConvertImpl impl = new ConvertImpl();
        return impl.doForward(this);
    }

    public PageElementDTO convertFor(PageElement pageElement){
        ConvertImpl impl = new ConvertImpl();
        return impl.doBackward(pageElement);
    }

    private static class ConvertImpl implements Convert<PageElementDTO, PageElement> {
        @Override
        public PageElement doForward(PageElementDTO dto) {
            PageElement pageElement = new PageElement();
            BeanUtils.copyProperties(dto,pageElement);
            return pageElement;
        }
        @Override
        public PageElementDTO doBackward(PageElement pageElement) {
            PageElementDTO dto = new PageElementDTO();
            BeanUtils.copyProperties(pageElement, dto);
            return dto;
        }
    }
}