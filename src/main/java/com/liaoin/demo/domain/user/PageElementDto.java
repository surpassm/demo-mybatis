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
public class PageElementDto implements Serializable {


    public PageElement convertTo(){
        ConvertImpl impl = new ConvertImpl();
        return impl.doForward(this);
    }

    public PageElementDto convertFor(PageElement pageElement){
        ConvertImpl impl = new ConvertImpl();
        return impl.doBackward(pageElement);
    }

    private static class ConvertImpl implements Convert<PageElementDto, PageElement> {
        @Override
        public PageElement doForward(PageElementDto dto) {
            PageElement pageElement = new PageElement();
            BeanUtils.copyProperties(dto,pageElement);
            return pageElement;
        }
        @Override
        public PageElementDto doBackward(PageElement pageElement) {
            PageElementDto dto = new PageElementDto();
            BeanUtils.copyProperties(pageElement, dto);
            return dto;
        }
    }
}
