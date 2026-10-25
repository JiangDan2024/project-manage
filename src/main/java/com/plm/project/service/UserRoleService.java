package com.plm.project.service;

import com.plm.project.entity.UserRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.IService;
import com.plm.project.vo.query.UserRoleQueryVo;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
/**
 * 用户和角色关联表(UserRole)表服务接口
 *
 * @author makejava
 * @since 2026-10-23 15:21:55
 */
public interface UserRoleService extends IService<UserRole> {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UserRole info(Long id);

    /**
     * 分页查询
     *
     * @param userRole 筛选条件
     * @return 查询结果
     */
    IPage<UserRole> queryByPage(UserRoleQueryVo userRole);

    /**
     * 新增数据
     *
     * @param userRole 实例对象
     * @return 实例对象
     */
    boolean add(UserRole userRole);

    /**
     * 修改数据
     *
     * @param userRole 实例对象
     * @return 实例对象
     */
    boolean edit(UserRole userRole);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteByIds(List<Long> id);

    void deleteByUserIds(List<Long> userIds);

    List<Long> getRolesByUserId(Long userId);

    List<Long> getUserByRoleId(Long roleId);

    boolean batchRelation(Long roleId, List<Long> userIds);

    boolean deleteOne(UserRole userRole);

    Boolean cancelAll(Long roleId, List<Long> userIds);
}
