package com.plm.project.vo;

import lombok.Data;

import java.util.List;

@Data
public class MenuRoleVo {
    private List<MenuSampleVo> menus;
    private List<Long> checkedKeys;

    @Data
    public static class MenuSampleVo{
        private Long id;
        private boolean disabled;
        private String label;
        private List<MenuSampleVo> children;
    }
}
