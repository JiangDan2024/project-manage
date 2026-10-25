package com.plm.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.plm.project.common.R;
import com.plm.project.entity.User;
import com.baomidou.mybatisplus.spring.service.IService;
import com.plm.project.vo.RoleVo;
import com.plm.project.vo.UserVo;
import com.plm.project.vo.query.UserQueryVo;
import java.util.List;
/**
 * 用户信息表(User)表服务接口
 *
 * @author makejava
 * @since 2026-10-23 14:42:10
 */
public interface UserService extends IService<User> {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    User info(Long id);

    /**
     * 分页查询
     *
     * @param user 筛选条件
     * @return 查询结果
     */
    IPage<RoleVo> getAuthRole(UserQueryVo user);

    IPage<UserVo> queryByPage(UserQueryVo user);

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    boolean add(UserVo user);

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    boolean edit(UserVo user);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteByIds(List<Long> id);

    Boolean authRoles(UserVo user);

    Boolean changeStatus(User user);

    IPage<UserVo> allocatedList(List<Long> userIds, Integer pageNum, Integer pageSize);

    IPage<UserVo> unallocatedList(List<Long> userIds, Integer pageNum, Integer pageSize);
}
