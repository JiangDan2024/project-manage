package com.plm.project.dao;

import com.plm.project.entity.Project;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 项目表(Project)表数据库访问层
 *
 * @author makejava
 * @since 2026-10-23 14:33:16
 */
@Mapper
public interface ProjectDao extends BaseMapper<Project>{

}

