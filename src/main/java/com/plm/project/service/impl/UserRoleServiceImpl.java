package com.plm.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.plm.project.entity.UserRole;
import com.plm.project.dao.UserRoleDao;
import com.plm.project.service.UserRoleService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.plm.project.vo.UserRoleQueryVo;
import java.util.List;
import java.util.stream.Stream;

/**
 * 用户和角色关联表(UserRole)表服务实现类
 *
 * @author makejava
 * @since 2026-10-23 15:21:55
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleDao,UserRole> implements UserRoleService {
    
    @Override
    public UserRole info(Long id) {

        return this.getById(id);
    }

    @Override
    public IPage<UserRole> queryByPage(UserRoleQueryVo userRole) {
        return null;
    }

    @Override
    public boolean add(UserRole userRole) {
        return this.save(userRole);
    }

    @Override
    public boolean edit(UserRole userRole) {
        return this.updateById(userRole);
    }

    @Override
    public boolean deleteByIds(List<Long> id) {
        return this.removeByIds(id);
    }

    @Override
    public void deleteByUserIds(List<Long> userIds) {
        this.remove(new QueryWrapper<UserRole>().in("user_id",userIds));
    }

    @Override
    public List<Long> getRolesByUserId(Long userId) {
        List<UserRole> userRoles = this.list(new QueryWrapper<UserRole>().eq("user_id", userId));
        List<Long> roleIds = userRoles.stream().map(UserRole::getRoleId).toList();
        return roleIds;
    }
}
