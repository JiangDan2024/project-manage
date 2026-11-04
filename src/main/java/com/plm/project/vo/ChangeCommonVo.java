package com.plm.project.vo;

import com.plm.project.entity.ChangeLog;
import lombok.Data;

import java.util.List;

@Data
public class ChangeCommonVo {
    private List<ChangeLog> changeLogs;
}
