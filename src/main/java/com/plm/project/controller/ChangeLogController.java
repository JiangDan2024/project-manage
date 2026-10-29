package com.plm.project.controller;

import com.plm.project.entity.ChangeLog;
import com.plm.project.service.ChangeLogService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.*;
import com.plm.project.common.R;
import com.plm.project.vo.query.ChangeLogQueryVo;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 变更历史记录表(ChangeLog)表控制层
 *
 * @author makejava
 * @since 2026-10-25 15:40:28
 */
@RestController
@RequestMapping("changeLog")
public class ChangeLogController {
    /**
     * 服务对象
     */
    @Autowired
    private ChangeLogService changeLogService;

    /**
     * 分页查询
     *
     * @param changeLog 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list")
    public R<List<ChangeLog>> getList(ChangeLog changeLog) {
        return R.ok(this.changeLogService.getList(changeLog));
    }

    /**
     * 批量新增数据
     * 这个不太可能收到请求把。。。
     * @param changeLogs 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public R<Boolean> add(@RequestBody List<ChangeLog> changeLogs) {
        return R.ok(this.changeLogService.batchAdd(changeLogs));
    }


}

