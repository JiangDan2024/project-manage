package com.plm.project.vo;

import com.plm.project.entity.Menu;
import lombok.Data;

import java.util.List;

@Data
public class MenuVo {
    private Menu menu;
    //是否为外链
    private Boolean isFrame;
    private List<MenuVo> children;
}
