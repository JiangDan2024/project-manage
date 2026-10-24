package com.plm.project.controller;

import com.plm.project.entity.Dept;
import com.plm.project.service.DeptService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.*;
import com.plm.project.common.R;
import com.plm.project.vo.DeptQueryVo;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 部门表(Dept)表控制层
 *
 * @author makejava
 * @since 2026-10-23 15:21:09
 */
@RestController
@RequestMapping("dept")
public class DeptController {
    /**
     * 服务对象
     */
    @Autowired
    private DeptService deptService;

    /**
     * 分页查询
     *
     * @param dept 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list")
    public R<IPage<Dept>> queryByPage(DeptQueryVo dept) {
        return R.ok(this.deptService.queryByPage(dept));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("info/{id}")
    public R<Dept> queryById(@PathVariable("id") Long id) {
        return R.ok(this.deptService.info(id));
    }

    /**
     * 新增数据
     *
     * @param dept 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public R add(@RequestBody Dept dept) {
        return R.ok(this.deptService.add(dept));
    }

    /**
     * 编辑数据
     *
     * @param dept 实体
     * @return 编辑结果
     */
    @PutMapping("/edit")
    public R edit(@RequestBody Dept dept) {
        return R.ok(this.deptService.edit(dept));
    }

    /**
     * 删除数据
     *
     * @param ids 主键
     * @return 删除是否成功
     */
    @DeleteMapping("delete")
    public R deleteByIds(@RequestParam("ids") List<Long> ids) {
        return R.ok(this.deptService.deleteByIds(ids));
    }

}

