package com.plm.project.dao;

import com.plm.project.entity.Material;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 物料主数据表(Material)表数据库访问层
 *
 * @author makejava
 * @since 2026-10-23 11:43:17
 */
@Mapper
public interface MaterialDao extends BaseMapper<Material>{

}

