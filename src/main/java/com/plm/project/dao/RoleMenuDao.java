package com.plm.project.dao;

import com.plm.project.entity.RoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色和菜单关联表(RoleMenu)表数据库访问层
 *
 * @author makejava
 * @since 2026-10-23 14:35:51
 */
@Mapper
public interface RoleMenuDao extends BaseMapper<RoleMenu>{

}

