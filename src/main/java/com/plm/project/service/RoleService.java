package com.plm.project.service;

import com.plm.project.entity.Role;
import com.baomidou.mybatisplus.spring.service.IService;
import com.plm.project.entity.UserRole;
import com.plm.project.vo.RoleVo;
import com.plm.project.vo.UserVo;
import com.plm.project.vo.query.RoleQueryVo;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2026-10-25 15:41:36
 */
public interface RoleService extends IService<Role> {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    RoleVo info(Long id);

    /**
     * 分页查询
     *
     * @param role 筛选条件
     * @return 查询结果
     */
    IPage<RoleVo> queryByPage(RoleQueryVo role);

    /**
     * 新增数据
     *
     * @param role 实例对象
     * @return 实例对象
     */
    boolean add(RoleVo role);

    /**
     * 修改数据
     *
     * @param role 实例对象
     * @return 实例对象
     */
    boolean edit(RoleVo role);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteByIds(List<Long> id);

    Boolean changeStatus(RoleVo role);

    IPage<UserVo> allocatedList(RoleQueryVo role);

    IPage<UserVo> unallocatedList(RoleQueryVo role);

    Boolean selectAll(RoleVo role);

    Boolean cancel(UserRole userRole);

    Boolean cancelAll(RoleVo role);
}
