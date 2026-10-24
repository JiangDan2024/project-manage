package com.plm.project.controller;

import com.plm.project.entity.RoleMenu;
import com.plm.project.service.RoleMenuService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.*;
import com.plm.project.common.R;
import com.plm.project.vo.RoleMenuQueryVo;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 角色和菜单关联表(RoleMenu)表控制层
 *
 * @author makejava
 * @since 2026-10-23 15:21:38
 */
@RestController
@RequestMapping("roleMenu")
public class RoleMenuController {
    /**
     * 服务对象
     */
    @Autowired
    private RoleMenuService roleMenuService;

    /**
     * 分页查询
     *
     * @param roleMenu 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list")
    public R<IPage<RoleMenu>> queryByPage(RoleMenuQueryVo roleMenu) {
        return R.ok(this.roleMenuService.queryByPage(roleMenu));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("info/{id}")
    public R<RoleMenu> queryById(@PathVariable("id") Long id) {
        return R.ok(this.roleMenuService.info(id));
    }

    /**
     * 新增数据
     *
     * @param roleMenu 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public R add(@RequestBody RoleMenu roleMenu) {
        return R.ok(this.roleMenuService.add(roleMenu));
    }

    /**
     * 编辑数据
     *
     * @param roleMenu 实体
     * @return 编辑结果
     */
    @PutMapping("/edit")
    public R edit(@RequestBody RoleMenu roleMenu) {
        return R.ok(this.roleMenuService.edit(roleMenu));
    }

    /**
     * 删除数据
     *
     * @param ids 主键
     * @return 删除是否成功
     */
    @DeleteMapping("delete")
    public R deleteByIds(@RequestParam("ids") List<Long> ids) {
        return R.ok(this.roleMenuService.deleteByIds(ids));
    }

}

