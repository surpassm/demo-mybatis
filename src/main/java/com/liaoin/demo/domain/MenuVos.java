package com.liaoin.demo.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @author mc
 * Create date 2020/10/13 16:13
 * Version 1.0
 * Description
 */
@Data
@ApiModel(value = "菜单批量独立使用")
public class MenuVos {

    private String path;
    private String name;
    private Integer type;
    private List<MenuVos> children;
}
