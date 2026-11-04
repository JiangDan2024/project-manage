package com.plm.project.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.plm.project.entity.ChangeLog;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class BizPage<T> extends Page<T> {

    private Map<Long,List<ChangeLog>> changeLogs;

    public BizPage(Map<Long,List<ChangeLog>> changeLogs) {
        this.changeLogs = changeLogs;
    }

    public BizPage(long current, long size) {
        super(current, size);
    }

    // 构造器：从原始 Page 复制分页元数据
    public BizPage(IPage<T> original, Map<Long,List<ChangeLog>> changeLogs) {
        super(original.getCurrent(), original.getSize(), original.getTotal());
        this.setRecords(original.getRecords()); // 复制数据
        this.changeLogs = changeLogs;
    }
}
