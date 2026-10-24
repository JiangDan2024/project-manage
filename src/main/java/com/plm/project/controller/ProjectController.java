package com.plm.project.controller;

import com.plm.project.entity.Project;
import com.plm.project.service.ProjectService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.*;
import com.plm.project.common.R;
import com.plm.project.vo.ProjectQueryVo;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 项目表(Project)表控制层
 *
 * @author makejava
 * @since 2026-10-23 15:20:47
 */
@RestController
@RequestMapping("project")
public class ProjectController {
    /**
     * 服务对象
     */
    @Autowired
    private ProjectService projectService;

    /**
     * 分页查询
     *
     * @param project 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list")
    public R<IPage<Project>> queryByPage(ProjectQueryVo project) {
        return R.ok(this.projectService.queryByPage(project));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("info/{id}")
    public R<Project> queryById(@PathVariable("id") Long id) {
        return R.ok(this.projectService.info(id));
    }

    /**
     * 新增数据
     *
     * @param project 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public R add(@RequestBody Project project) {
        return R.ok(this.projectService.add(project));
    }

    /**
     * 编辑数据
     *
     * @param project 实体
     * @return 编辑结果
     */
    @PutMapping("/edit")
    public R edit(@RequestBody Project project) {
        return R.ok(this.projectService.edit(project));
    }

    /**
     * 删除数据
     *
     * @param ids 主键
     * @return 删除是否成功
     */
    @DeleteMapping("delete")
    public R deleteByIds(@RequestParam("ids") List<Long> ids) {
        return R.ok(this.projectService.deleteByIds(ids));
    }

}

