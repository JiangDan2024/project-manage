package com.plm.project.service;

import com.plm.project.entity.User;
import com.plm.project.vo.UserVo;

import java.util.Set;

/**
 * 用户权限或角色信息获取
 */
public interface PermissionService {

    Set<String> getRolePermission(Long userId);

    Set<String> getMenuPermission(UserVo user);
}
