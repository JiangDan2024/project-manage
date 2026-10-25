package com.plm.project.controller;

import com.plm.project.entity.RoleDept;
import com.plm.project.service.RoleDeptService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.*;
import com.plm.project.common.R;
import com.plm.project.vo.query.RoleDeptQueryVo;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 角色和部门关联表(RoleDept)表控制层
 *
 * @author makejava
 * @since 2026-10-25 15:41:48
 */
@RestController
@RequestMapping("roleDept")
public class RoleDeptController {
    /**
     * 服务对象
     */
    @Autowired
    private RoleDeptService roleDeptService;

    /**
     * 分页查询
     *
     * @param roleDept 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list")
    public R<IPage<RoleDept>> queryByPage(RoleDeptQueryVo roleDept) {
        return R.ok(this.roleDeptService.queryByPage(roleDept));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("info/{id}")
    public R<RoleDept> queryById(@PathVariable("id") Long id) {
        return R.ok(this.roleDeptService.info(id));
    }

    /**
     * 新增数据
     *
     * @param roleDept 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public R add(@RequestBody RoleDept roleDept) {
        return R.ok(this.roleDeptService.add(roleDept));
    }

    /**
     * 编辑数据
     *
     * @param roleDept 实体
     * @return 编辑结果
     */
    @PutMapping("/edit")
    public R edit(@RequestBody RoleDept roleDept) {
        return R.ok(this.roleDeptService.edit(roleDept));
    }

    /**
     * 删除数据
     *
     * @param ids 主键
     * @return 删除是否成功
     */
    @DeleteMapping("delete")
    public R deleteByIds(@RequestParam("ids") List<Long> ids) {
        return R.ok(this.roleDeptService.deleteByIds(ids));
    }

}

