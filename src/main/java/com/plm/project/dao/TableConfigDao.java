package com.plm.project.dao;

import com.plm.project.entity.TableConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表格列个性化配置(TableConfig)表数据库访问层
 *
 * @author makejava
 * @since 2026-10-23 14:36:06
 */
@Mapper
public interface TableConfigDao extends BaseMapper<TableConfig>{

}

