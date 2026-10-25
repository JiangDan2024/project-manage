package com.plm.project.dao;

import com.plm.project.entity.Task;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 任务表(Task)表数据库访问层
 *
 * @author makejava
 * @since 2026-10-23 14:34:14
 */
@Mapper
public interface TaskDao extends BaseMapper<Task>{

}

