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
    public R<IPage<ChangeLog>> queryByPage(ChangeLogQueryVo changeLog) {
        return R.ok(this.changeLogService.queryByPage(changeLog));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("info/{id}")
    public R<ChangeLog> queryById(@PathVariable("id") Long id) {
        return R.ok(this.changeLogService.info(id));
    }

    /**
     * 新增数据
     *
     * @param changeLog 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public R add(@RequestBody ChangeLog changeLog) {
        return R.ok(this.changeLogService.add(changeLog));
    }

    /**
     * 编辑数据
     *
     * @param changeLog 实体
     * @return 编辑结果
     */
    @PutMapping("/edit")
    public R edit(@RequestBody ChangeLog changeLog) {
        return R.ok(this.changeLogService.edit(changeLog));
    }

    /**
     * 删除数据
     *
     * @param ids 主键
     * @return 删除是否成功
     */
    @DeleteMapping("delete")
    public R deleteByIds(@RequestParam("ids") List<Long> ids) {
        return R.ok(this.changeLogService.deleteByIds(ids));
    }

}

