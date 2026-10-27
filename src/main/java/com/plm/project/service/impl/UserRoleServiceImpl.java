package com.plm.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.plm.project.entity.UserRole;
import com.plm.project.dao.UserRoleDao;
import com.plm.project.service.UserRoleService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.plm.project.vo.query.UserRoleQueryVo;
import java.util.List;

/**
 * 用户和角色关联表(UserRole)表服务实现类
 *
 * @author makejava
 * @since 2026-10-25 15:42:46
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

    @Override
    public List<Long> getUserByRoleId(Long roleId) {
        List<UserRole> roleUserList = this.list(new QueryWrapper<UserRole>().eq("role_id", roleId));
        List<Long> list = roleUserList.stream().map(UserRole::getRoleId).toList();
        return list;
    }

    @Override
    public List<UserRole> getUserByRoleIds(List<Long> roleIds) {
        //todo 是否再过滤一下用户是不是未删除和启用的
        List<UserRole> userRoles = this.list(new QueryWrapper<UserRole>().eq("role_id", roleIds));
        return userRoles;
    }

    @Override
    public boolean batchRelation(Long roleId, List<Long> userIds) {
        //删除之前的
        boolean b = this.remove(new QueryWrapper<UserRole>().eq("role_id", roleId));
        //重新保存
        if (b){
            if (userIds!=null&&userIds.size()>0){
                List<UserRole> userRoleList = userIds.stream().map(u -> {
                    UserRole userRole = new UserRole();
                    userRole.setUserId(u);
                    userRole.setRoleId(roleId);
                    return userRole;
                }).toList();
                this.saveBatch(userRoleList);
            }
        }
        return true;
    }

    @Override
    public boolean deleteOne(UserRole userRole) {
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userRole.getUserId());
        queryWrapper.eq("role_id", userRole.getRoleId());

        return this.remove(queryWrapper);
    }

    @Override
    public Boolean cancelAll(Long roleId, List<Long> userIds) {
        if (userIds!=null&&userIds.size()>0){
            QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("role_id",roleId);
            queryWrapper.in("user_id",userIds);
            return this.remove(queryWrapper);
        }
        return false;
    }
}
