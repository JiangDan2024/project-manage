package com.plm.project.service;

import com.plm.project.entity.RoleDept;
import com.baomidou.mybatisplus.spring.service.IService;
import com.plm.project.vo.RoleVo;
import com.plm.project.vo.query.RoleDeptQueryVo;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 角色和部门关联表(RoleDept)表服务接口
 *
 * @author makejava
 * @since 2026-10-25 15:41:48
 */
public interface RoleDeptService extends IService<RoleDept> {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    RoleDept info(Long id);

    /**
     * 分页查询
     *
     * @param roleDept 筛选条件
     * @return 查询结果
     */
    IPage<RoleDept> queryByPage(RoleDeptQueryVo roleDept);

    /**
     * 新增数据
     *
     * @param roleDept 实例对象
     * @return 实例对象
     */
    boolean add(RoleDept roleDept);

    /**
     * 修改数据
     *
     * @param roleDept 实例对象
     * @return 实例对象
     */
    boolean edit(RoleDept roleDept);

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
