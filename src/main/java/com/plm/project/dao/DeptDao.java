package com.plm.project.dao;

import com.plm.project.entity.Dept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 部门表(Dept)表数据库访问层
 *
 * @author makejava
 * @since 2026-10-23 14:34:48
 */
@Mapper
public interface DeptDao extends BaseMapper<Dept>{

}

