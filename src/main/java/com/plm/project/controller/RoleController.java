package com.plm.project.controller;

import com.plm.project.entity.Role;
import com.plm.project.service.RoleService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.*;
import com.plm.project.common.R;
import com.plm.project.vo.RoleQueryVo;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 角色信息表(Role)表控制层
 *
 * @author makejava
 * @since 2026-10-23 15:21:23
 */
@RestController
@RequestMapping("role")
public class RoleController {
    /**
     * 服务对象
     */
    @Autowired
    private RoleService roleService;

    /**
     * 分页查询
     *
     * @param role 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list")
    public R<IPage<Role>> queryByPage(RoleQueryVo role) {
        return R.ok(this.roleService.queryByPage(role));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("info/{id}")
    public R<Role> queryById(@PathVariable("id") Long id) {
        return R.ok(this.roleService.info(id));
    }

    /**
     * 新增数据
     *
     * @param role 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public R add(@RequestBody Role role) {
        return R.ok(this.roleService.add(role));
    }

    /**
     * 编辑数据
     *
     * @param role 实体
     * @return 编辑结果
     */
    @PutMapping("/edit")
    public R edit(@RequestBody Role role) {
        return R.ok(this.roleService.edit(role));
    }

    /**
     * 删除数据
     *
     * @param ids 主键
     * @return 删除是否成功
     */
    @DeleteMapping("delete")
    public R deleteByIds(@RequestParam("ids") List<Long> ids) {
        return R.ok(this.roleService.deleteByIds(ids));
    }

}

