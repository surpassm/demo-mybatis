package com.liaoin.demo.domain.user;


import com.liaoin.demo.annotation.Convert;
import com.liaoin.demo.entity.user.Menu;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
* @author mc
* Create date 2019-12-12 21:02:26
* Version 1.0
* Description 权限数据流
*/
@Getter
@Setter
@ApiModel(value = "权限")
public class MenuDTO implements Serializable {


    public Menu convertTo(){
        ConvertImpl impl = new ConvertImpl();
        return impl.doForward(this);
    }

    public MenuDTO convertFor(Menu menu){
        ConvertImpl impl = new ConvertImpl();
        return impl.doBackward(menu);
    }

    private static class ConvertImpl implements Convert<MenuDTO, Menu> {
        @Override
        public Menu doForward(MenuDTO dto) {
            Menu menu = new Menu();
            BeanUtils.copyProperties(dto,menu);
            return menu;
        }
        @Override
        public MenuDTO doBackward(Menu menu) {
            MenuDTO dto = new MenuDTO();
            BeanUtils.copyProperties(menu, dto);
            return dto;
        }
    }
}
