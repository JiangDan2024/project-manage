package com.plm.project.service;

import com.plm.project.entity.Role;
import com.baomidou.mybatisplus.spring.service.IService;
import com.plm.project.vo.query.RoleQueryVo;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2026-10-23 15:21:23
 */
public interface RoleService extends IService<Role> {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Role info(Long id);

    /**
     * 分页查询
     *
     * @param role 筛选条件
     * @return 查询结果
     */
    IPage<Role> queryByPage(RoleQueryVo role);

    /**
     * 新增数据
     *
     * @param role 实例对象
     * @return 实例对象
     */
    boolean add(Role role);

    /**
     * 修改数据
     *
     * @param role 实例对象
     * @return 实例对象
     */
    boolean edit(Role role);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteByIds(List<Long> id);
}
