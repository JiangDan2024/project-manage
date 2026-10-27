package com.plm.project.vo;

import com.plm.project.entity.Dept;
import lombok.Data;

import java.util.List;

@Data
public class DeptVo {
    private Dept dept;
    private List<DeptVo> children;
}
