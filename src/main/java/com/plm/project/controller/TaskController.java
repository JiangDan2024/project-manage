package com.plm.project.controller;

import com.plm.project.entity.Task;
import com.plm.project.service.TaskService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.*;
import com.plm.project.common.R;
import com.plm.project.vo.TaskQueryVo;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 任务表(Task)表控制层
 *
 * @author makejava
 * @since 2026-10-23 15:20:54
 */
@RestController
@RequestMapping("task")
public class TaskController {
    /**
     * 服务对象
     */
    @Autowired
    private TaskService taskService;

    /**
     * 分页查询
     *
     * @param task 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list")
    public R<IPage<Task>> queryByPage(TaskQueryVo task) {
        return R.ok(this.taskService.queryByPage(task));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("info/{id}")
    public R<Task> queryById(@PathVariable("id") Long id) {
        return R.ok(this.taskService.info(id));
    }

    /**
     * 新增数据
     *
     * @param task 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public R add(@RequestBody Task task) {
        return R.ok(this.taskService.add(task));
    }

    /**
     * 编辑数据
     *
     * @param task 实体
     * @return 编辑结果
     */
    @PutMapping("/edit")
    public R edit(@RequestBody Task task) {
        return R.ok(this.taskService.edit(task));
    }

    /**
     * 删除数据
     *
     * @param ids 主键
     * @return 删除是否成功
     */
    @DeleteMapping("delete")
    public R deleteByIds(@RequestParam("ids") List<Long> ids) {
        return R.ok(this.taskService.deleteByIds(ids));
    }

}

