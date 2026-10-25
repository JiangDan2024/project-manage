package com.plm.project.controller;

import com.plm.project.entity.UserRole;
import com.plm.project.service.UserRoleService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.*;
import com.plm.project.common.R;
import com.plm.project.vo.query.UserRoleQueryVo;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户和角色关联表(UserRole)表控制层
 *
 * @author makejava
 * @since 2026-10-25 15:42:46
 */
@RestController
@RequestMapping("userRole")
public class UserRoleController {
    /**
     * 服务对象
     */
    @Autowired
    private UserRoleService userRoleService;

    /**
     * 分页查询
     *
     * @param userRole 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list")
    public R<IPage<UserRole>> queryByPage(UserRoleQueryVo userRole) {
        return R.ok(this.userRoleService.queryByPage(userRole));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("info/{id}")
    public R<UserRole> queryById(@PathVariable("id") Long id) {
        return R.ok(this.userRoleService.info(id));
    }

    /**
     * 新增数据
     *
     * @param userRole 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public R add(@RequestBody UserRole userRole) {
        return R.ok(this.userRoleService.add(userRole));
    }

    /**
     * 编辑数据
     *
     * @param userRole 实体
     * @return 编辑结果
     */
    @PutMapping("/edit")
    public R edit(@RequestBody UserRole userRole) {
        return R.ok(this.userRoleService.edit(userRole));
    }

    /**
     * 删除数据
     *
     * @param ids 主键
     * @return 删除是否成功
     */
    @DeleteMapping("delete")
    public R deleteByIds(@RequestParam("ids") List<Long> ids) {
        return R.ok(this.userRoleService.deleteByIds(ids));
    }

}

