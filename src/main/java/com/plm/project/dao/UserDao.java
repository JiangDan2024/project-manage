package com.plm.project.dao;

import com.plm.project.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户信息表(User)表数据库访问层
 *
 * @author makejava
 * @since 2026-10-23 14:36:21
 */
@Mapper
public interface UserDao extends BaseMapper<User>{

}

