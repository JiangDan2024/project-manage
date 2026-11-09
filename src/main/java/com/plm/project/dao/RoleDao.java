package com.plm.project.dao;

import com.plm.project.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2026-10-23 14:35:20
 */
@Mapper
public interface RoleDao extends BaseMapper<Role>{

    List<Role> selectRolePermissionByUserId(@Param("userId") Long userId);
}

