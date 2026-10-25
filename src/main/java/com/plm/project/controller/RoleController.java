package com.plm.project.controller;

import com.plm.project.entity.Role;
import com.plm.project.entity.UserRole;
import com.plm.project.service.RoleService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.plm.project.vo.RoleVo;
import com.plm.project.vo.UserVo;
import org.springframework.web.bind.annotation.*;
import com.plm.project.common.R;
import com.plm.project.vo.query.RoleQueryVo;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 角色信息表(Role)表控制层
 *
 * @author makejava
 * @since 2026-10-25 15:41:36
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
    public R<IPage<RoleVo>> queryByPage(RoleQueryVo role) {
        return R.ok(this.roleService.queryByPage(role));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("info/{id}")
    public R<RoleVo> queryById(@PathVariable("id") Long id) {
        return R.ok(this.roleService.info(id));
    }

    /**
     * 新增数据
     *
     * @param role 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public R<Boolean> add(@RequestBody RoleVo role) {
        return R.ok(this.roleService.add(role));
    }

    /**
     * 编辑数据
     *
     * @param role 实体
     * @return 编辑结果
     */
    @PutMapping("/edit")
    public R edit(@RequestBody RoleVo role) {
        return R.ok(this.roleService.edit(role));
    }

    /**
     * 修改角色状态
     * @param role
     * @return
     */
    @PutMapping("/changeStatus")
    public R<Boolean> changeStatus(@RequestBody RoleVo role) {

        return R.ok(this.roleService.changeStatus(role));
    }

    /**
     * 查询该角色已分配的用户
     * @param role
     * @return
     */
    @GetMapping("/authUser/allocatedList")
    public R<IPage<UserVo>> allocatedList(@RequestParam RoleQueryVo role) {

        return R.ok(this.roleService.allocatedList(role));
    }

    /**
     * 查询未分配用户角色列表
     * @param role
     * @return
     */
    @GetMapping("/authUser/unallocatedList")
    public R<IPage<UserVo>> unallocatedList(@RequestParam RoleQueryVo role) {
        return R.ok(this.roleService.unallocatedList(role));
    }

    /**
     * 批量授权用户
     * @param role
     * @return
     */
    @PostMapping("/authUser/selectAll")
    public R<Boolean> selectAll(@RequestBody RoleVo role) {
        return R.ok(this.roleService.selectAll(role));
    }

    /**
     * 取消授权
     * @param userRole
     * @return
     */
    @PutMapping("/authUser/cancel")
    public R<Boolean> cancel(@RequestBody UserRole userRole) {
        return R.ok(this.roleService.cancel(userRole));
    }

    /**
     * 批量取消授权
     * @param role
     * @return
     */
    @PutMapping("/authUser/cancelAll")
    public R<Boolean> cancelAll(@RequestBody RoleVo role) {
        return R.ok(this.roleService.cancelAll(role));
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

