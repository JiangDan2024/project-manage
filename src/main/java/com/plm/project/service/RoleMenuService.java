package com.plm.project.service;

import com.plm.project.entity.RoleMenu;
import com.baomidou.mybatisplus.spring.service.IService;
import com.plm.project.vo.RoleVo;
import com.plm.project.vo.query.RoleMenuQueryVo;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 角色和菜单关联表(RoleMenu)表服务接口
 *
 * @author makejava
 * @since 2026-10-25 15:41:59
 */
public interface RoleMenuService extends IService<RoleMenu> {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    RoleMenu info(Long id);

    /**
     * 分页查询
     *
     * @param roleMenu 筛选条件
     * @return 查询结果
     */
    IPage<RoleMenu> queryByPage(RoleMenuQueryVo roleMenu);

    /**
     * 新增数据
     *
     * @param roleMenu 实例对象
     * @return 实例对象
     */
    boolean add(RoleMenu roleMenu);

    /**
     * 修改数据
     *
     * @param roleMenu 实例对象
     * @return 实例对象
     */
    boolean edit(RoleMenu roleMenu);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteByIds(List<Long> id);

    List<Long> getIdByRoleId(Long roleId);

    void editByRoleId(RoleVo role);

    void deleteByRoleIds(List<Long> ids);
}
