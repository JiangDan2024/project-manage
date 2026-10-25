package com.plm.project.dao;

import com.plm.project.entity.ChangeLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 变更历史记录表(ChangeLog)表数据库访问层
 *
 * @author makejava
 * @since 2026-10-23 14:34:33
 */
@Mapper
public interface ChangeLogDao extends BaseMapper<ChangeLog>{

}

