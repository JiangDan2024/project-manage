package com.plm.project.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.plm.project.common.UserContext;
import com.plm.project.entity.*;
import com.plm.project.dao.RoleDao;
import com.plm.project.exception.RoleException;
import com.plm.project.service.*;
import com.plm.project.vo.RoleVo;
import com.plm.project.vo.UserVo;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.plm.project.vo.query.RoleQueryVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2026-10-25 15:41:36
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleDao,Role> implements RoleService {
    @Autowired
    RoleDeptService roleDeptService;

    @Autowired
    RoleMenuService roleMenuService;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    UserService userService;

    @Override
    public RoleVo info(Long id) {
        Role role = this.getById(id);
        RoleVo roleVo = new RoleVo();
        BeanUtils.copyProperties(role,roleVo);
        List<Long> menuIds = roleMenuService.getIdByRoleId(id);
        List<Long> deptIds = roleDeptService.getIdByRoleId(id);
        roleVo.setMenuIds(menuIds);
        roleVo.setDeptIds(deptIds);
        return roleVo;
    }

    @Override
    public IPage<RoleVo> queryByPage(RoleQueryVo role) {
        Page page = new Page(role.getPageNum(),role.getPageSize());
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",role.getStatus());
        if (StringUtils.isNotEmpty(role.getKeyword())){
            queryWrapper.and(q->{
                q.like("role_name",role.getKeyword())
                        .or().like("role_key",role.getKeyword());
            });
        }
        IPage<Role> roleIPage = this.page(page,queryWrapper);
        IPage<RoleVo> roleVoIPage = roleIPage.convert(r -> {
            RoleVo roleVo = new RoleVo();
            BeanUtils.copyProperties(role, roleVo);
            //获取关联的部门id
            List<Long> deptIds = roleDeptService.getIdByRoleId(r.getRoleId());
            roleVo.setDeptIds(deptIds);
            //获取关联的目录id
            List<Long> menuIds = roleMenuService.getIdByRoleId(r.getRoleId());
            roleVo.setMenuIds(menuIds);
            return roleVo;
        });
        return roleVoIPage;
    }

    @Transactional
    @Override
    public boolean add(RoleVo role) {
        //判断role_name、role_key不能重复
        checkRoleNameUnique(role.getRoleName());
        checkRoleKeyUnique(role.getRoleKey());
        //新增角色
        Role roleEntity = new Role();
        User user = UserContext.get();
        roleEntity.setCreateBy(user.getUserId().toString());
        roleEntity.setUpdateBy(user.getUserId().toString());
        roleEntity.setCreateTime(new Date());
        roleEntity.setUpdateTime(new Date());
        this.save(roleEntity);
        //新增角色-部门关联
        List<RoleDept> roleDepts = role.getDeptIds().stream().map(id -> {
            RoleDept roleDept = new RoleDept();
            roleDept.setRoleId(roleEntity.getRoleId());
            roleDept.setDeptId(id);
            return roleDept;
        }).toList();
        roleDeptService.saveBatch(roleDepts);
        //新增角色-目录关联
        List<RoleMenu> roleMenus = role.getMenuIds().stream().map(id -> {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleEntity.getRoleId());
            roleMenu.setMenuId(id);
            return roleMenu;
        }).toList();
        roleMenuService.saveBatch(roleMenus);
        return true;
    }

    private void checkRoleKeyUnique(String roleKey) {
        long count = this.count(new QueryWrapper<Role>().eq("role_key", roleKey));
        if (count > 0) {
            throw new RoleException("角色权限字符串已存在");
        }
    }

    private void checkRoleNameUnique(String roleName) {
        long count = this.count(new QueryWrapper<Role>().eq("role_name", roleName));
        if (count > 0) {
            throw new RoleException("角色名已存在");
        }
    }

    @Override
    public boolean edit(RoleVo role) {
        //更新角色
        Role vo = new Role();
        BeanUtils.copyProperties(role,vo);
        //更新角色-部门关联
        roleDeptService.editByRoleId(role);
        //更新角色-目录关联
        roleMenuService.editByRoleId(role);
        return this.updateById(role);
    }

    @Override
    public boolean deleteByIds(List<Long> ids) {
        this.removeByIds(ids);
        //删除关联表
        roleMenuService.deleteByRoleIds(ids);
        roleDeptService.deleteByRoleIds(ids);
        return true;
    }

    @Override
    public Boolean changeStatus(RoleVo vo) {
        Long roleId = vo.getRoleId();
        Role role = this.getById(roleId);
        role.setStatus(vo.getStatus());
        return this.updateById(role);
    }

    @Override
    public IPage<UserVo> allocatedList(RoleQueryVo role) {
        //去关联表查询用户ids
        List<Long> userIds = userRoleService.getUserByRoleId(role.getRoleId());
        //查询用户信息，传入分页信息
        if (userIds!=null&&userIds.size()>0) {
            IPage<UserVo> userVoIPage = userService.allocatedList(userIds,role.getPageNum(),role.getPageSize());
            return userVoIPage;
        }
        return null;
    }

    @Override
    public IPage<UserVo> unallocatedList(RoleQueryVo role) {
        //去关联表查询用户ids
        List<Long> userIds = userRoleService.getUserByRoleId(role.getRoleId());
        //查询用户信息，传入分页信息
        if (userIds!=null&&userIds.size()>0) {
            IPage<UserVo> userVoIPage = userService.unallocatedList(userIds,role.getPageNum(),role.getPageSize());
            return userVoIPage;
        }
        return null;
    }

    @Override
    public Boolean selectAll(RoleVo role) {
        boolean b = userRoleService.batchRelation(role.getRoleId(),role.getUserIds());
        return b;
    }

    @Override
    public Boolean cancel(UserRole userRole) {
        return userRoleService.deleteOne(userRole);
    }

    @Override
    public Boolean cancelAll(RoleVo role) {
        return userRoleService.cancelAll(role.getRoleId(),role.getUserIds());
    }
}
