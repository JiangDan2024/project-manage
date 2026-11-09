package com.plm.project.service.impl;

import com.plm.project.service.MenuService;
import com.plm.project.service.PermissionService;
import com.plm.project.service.RoleService;
import com.plm.project.vo.RoleVo;
import com.plm.project.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    RoleService roleService;

    @Autowired
    MenuService menuService;

    @Override
    public Set<String> getRolePermission(Long userId) {
        return roleService.selectRolePermissionByUserId(userId);
    }

    @Override
    public Set<String> getMenuPermission(UserVo user) {
        Set<String> permissions = new HashSet<>();
        //获取目录的权限列表，根据角色id
        List<RoleVo> roleList = user.getRoleList();
        for (RoleVo role : roleList) {
            Set<String> rolePerms = menuService.selectMenuPermsByRoleId(role.getRoleId());
            permissions.addAll(rolePerms);
            role.setPermissions(rolePerms);
        }
        return permissions;
    }
}
