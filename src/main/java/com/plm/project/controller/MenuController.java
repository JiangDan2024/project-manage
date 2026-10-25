package com.plm.project.controller;

import com.plm.project.entity.Menu;
import com.plm.project.service.MenuService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.*;
import com.plm.project.common.R;
import com.plm.project.vo.query.MenuQueryVo;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 菜单表(Menu)表控制层
 *
 * @author makejava
 * @since 2026-10-25 15:41:14
 */
@RestController
@RequestMapping("menu")
public class MenuController {
    /**
     * 服务对象
     */
    @Autowired
    private MenuService menuService;

    /**
     * 分页查询
     *
     * @param menu 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list")
    public R<IPage<Menu>> queryByPage(MenuQueryVo menu) {
        return R.ok(this.menuService.queryByPage(menu));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("info/{id}")
    public R<Menu> queryById(@PathVariable("id") Long id) {
        return R.ok(this.menuService.info(id));
    }

    /**
     * 新增数据
     *
     * @param menu 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public R add(@RequestBody Menu menu) {
        return R.ok(this.menuService.add(menu));
    }

    /**
     * 编辑数据
     *
     * @param menu 实体
     * @return 编辑结果
     */
    @PutMapping("/edit")
    public R edit(@RequestBody Menu menu) {
        return R.ok(this.menuService.edit(menu));
    }

    /**
     * 删除数据
     *
     * @param ids 主键
     * @return 删除是否成功
     */
    @DeleteMapping("delete")
    public R deleteByIds(@RequestParam("ids") List<Long> ids) {
        return R.ok(this.menuService.deleteByIds(ids));
    }

}

