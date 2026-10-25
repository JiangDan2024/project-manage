package com.plm.project.dao;

import com.plm.project.entity.RoleDept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色和部门关联表(RoleDept)表数据库访问层
 *
 * @author makejava
 * @since 2026-10-23 14:35:35
 */
@Mapper
public interface RoleDeptDao extends BaseMapper<RoleDept>{

}

