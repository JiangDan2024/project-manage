package com.plm.project.vo;

import com.plm.project.entity.Menu;
import lombok.Data;

import java.util.List;

@Data
public class MenuVo extends Menu {
    private List<MenuVo> children;
}
