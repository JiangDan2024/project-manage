package com.plm.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.plm.project.common.BizCodeEnume;
import com.plm.project.entity.User;
import com.plm.project.exception.LoginNameExistException;
import com.plm.project.service.UserService;
import com.plm.project.vo.RoleVo;
import com.plm.project.vo.UserVo;
import org.springframework.web.bind.annotation.*;
import com.plm.project.common.R;
import com.plm.project.vo.query.UserQueryVo;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户信息表(User)表控制层
 *
 * @author makejava
 * @since 2026-10-23 14:36:21
 */
@RestController
@RequestMapping("/sys/user")
public class UserController {
    /**
     * 服务对象
     */
    @Autowired
    private UserService userService;

    /**
     * 分页查询
     *
     * @param user 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list")
    public R<IPage<UserVo>> queryByPage(UserQueryVo user) {
        return R.ok(this.userService.queryByPage(user));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("info/{id}")
    public R<User> queryById(@PathVariable("id") Long id) {
        return R.ok(this.userService.info(id));
    }

    /**
     * 新增数据
     *
     * @param user 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public R add(@RequestBody UserVo user) {
        try {
            return R.ok(this.userService.add(user));
        }catch (LoginNameExistException error){
            return R.error(BizCodeEnume.USER_EXIST_EXCEPTION.getCode(),BizCodeEnume.USER_EXIST_EXCEPTION.getMessage(),error.getMessage());
        }
    }

    /**
     * 编辑数据
     *
     * @param user 实体
     * @return 编辑结果
     */
    @PutMapping("/edit")
    public R edit(@RequestBody UserVo user) {
        return R.ok(this.userService.edit(user));
    }

    /**
     * 根据用户ID获取关联的角色，返回角色分页
     * @param user
     * @return
     */
    @GetMapping("/getAuthRole")
    public R<IPage<RoleVo>> getAuthRole(UserQueryVo user) {
        return R.ok(this.userService.getAuthRole(user));
    }

    /**
     *
     * @param user
     * @return
     */
    @PostMapping("/authRoles")
    public R<Boolean> authRoles(@RequestBody UserVo user) {

        return R.ok(this.userService.authRoles(user));
    }
    /**
     * 删除数据
     *
     * @param ids 主键
     * @return 删除是否成功
     */
    @DeleteMapping("delete")
    public R deleteByIds(@RequestParam("ids") List<Long> ids) {
        return R.ok(this.userService.deleteByIds(ids));
    }

}

