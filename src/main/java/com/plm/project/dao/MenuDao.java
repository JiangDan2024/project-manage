package com.plm.project.dao;

import com.plm.project.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2026-10-23 14:35:05
 */
@Mapper
public interface MenuDao extends BaseMapper<Menu>{

    List<String> selectMenuPermsByRoleId(@Param("roleId") Long roleId);

    List<Menu> selectMenusByUserId(@Param("userId") Long userId);
}

