package com.plm.project.dao;

import com.plm.project.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2026-10-23 14:35:20
 */
@Mapper
public interface RoleDao extends BaseMapper<Role>{

}

