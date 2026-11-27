package com.plm.project.vo;

import lombok.Data;

import java.util.List;

@Data
public class MenuTreeVo {
    private Long id;
    private String label;
    private Boolean disabled;
    private List<MenuTreeVo> children;
}
