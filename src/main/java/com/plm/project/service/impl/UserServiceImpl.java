package com.plm.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.plm.project.dao.RoleDao;
import com.plm.project.entity.Dept;
import com.plm.project.entity.Role;
import com.plm.project.entity.User;
import com.plm.project.dao.UserDao;
import com.plm.project.entity.UserRole;
import com.plm.project.exception.LoginNameExistException;
import com.plm.project.service.*;
import com.plm.project.vo.RoleVo;
import com.plm.project.vo.UserVo;
import com.plm.project.vo.query.RoleQueryVo;
import com.plm.project.vo.snapshot.UserSnapshotVo;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.plm.project.vo.query.UserQueryVo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
/**
 * 用户信息表(User)表服务实现类
 *
 * @author makejava
 * @since 2026-10-23 14:42:10
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao,User> implements UserService {

    @Autowired
    DeptService deptService;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RoleDeptService roleDeptService;

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public UserVo info(Long id) {
        UserVo vo = new UserVo();
        //查出用户信息
        User user = this.getById(id);
        BeanUtils.copyProperties(user,vo);
        //查出对应的role信息
        List<Long> roleIds = userRoleService.getRolesByUserId(id);
        List<Role> roleList = new ArrayList<>();
        if (roleIds != null && !roleIds.isEmpty()) {
            roleList = roleDao.selectList(new QueryWrapper<Role>().in("role_id", roleIds));
        }
        if(roleList!=null&&roleList.size()>0){
            List<RoleVo> roleVos = roleList.stream().map(r -> {
                RoleVo roleVo = new RoleVo();
                BeanUtils.copyProperties(r, roleVo);
                return roleVo;
            }).toList();
            //封装vo
            vo.setRoleList(roleVos);
        }
        return vo;
    }

    @Override
    public IPage<UserVo> queryByPage(UserQueryVo user) {
        Page page = new Page(user.getPageNum(), user.getPageSize());
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        wrapper.eq("del_flag",0);
        if(StringUtils.isNotEmpty(user.getKeyword())){
            wrapper.and(w->{
                w.like("login_name",user.getKeyword())
                        .or().like("user_name",user.getKeyword());
            });
        }
        if(StringUtils.isNotEmpty(user.getPhoneNumber())){
            wrapper.like("phone_number",user.getPhoneNumber());
        }
        //查出用户信息
        IPage<User> userIPage = this.page(page,wrapper);

        IPage<UserVo> convert = getConvert(userIPage);
        return convert;
    }

    private IPage<UserVo> getConvert(IPage<User> userIPage) {
        IPage<UserVo> convert = userIPage.convert(u -> {
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(u, userVo);
            Dept dept = null;
            if (userVo.getDeptId() != null) {
                //查出对应的部门信息
                dept = deptService.info(userVo.getDeptId());
            }
            userVo.setDept(dept);
            return userVo;
        });
        return convert;
    }

    @Override
    public boolean add(UserVo vo) throws LoginNameExistException{
        //判断用户名是否存在，存在抛出异常
        String loginName = vo.getLoginName();
        long count = this.count(new QueryWrapper<User>().eq("login_name", loginName));
        if(count > 0){
            throw new LoginNameExistException();
        }
        //新增用户信息
        User user = new User();
        BeanUtils.copyProperties(vo,user);
        //TODO 获取当前用户信息，登录成功要存储用户信息
        //user.setCreateBy();
        user.setCreateTime(new Date());
        this.save(user);
        //新增用户和角色关联表
        if(vo.getRoleIds()!=null&&vo.getRoleIds().size()>0){
            List<UserRole> userRoleList = vo.getRoleIds().stream().map(r -> {
                UserRole userRole = new UserRole();
                userRole.setRoleId(r);
                userRole.setUserId(user.getUserId());
                return userRole;
            }).toList();
            userRoleService.saveBatch(userRoleList);
        }
        return true;
    }

    @Override
    public boolean edit(UserVo vo) {
        //更新用户信息
        User user = new User();
        BeanUtils.copyProperties(vo,user);
        //TODO更新人信息
        //user.setUpdateBy();
        user.setUpdateTime(new Date());
        this.updateById(user);
        //更新用户和角色关联表
        //先删除
        userRoleService.deleteByUserIds(Arrays.asList(vo.getUserId()));
        if(vo.getRoleIds()!=null&&vo.getRoleIds().size()>0){
            List<UserRole> userRoleList = vo.getRoleIds().stream().map(r -> {
                UserRole userRole = new UserRole();
                userRole.setUserId(vo.getUserId());
                userRole.setRoleId(r);
                return userRole;
            }).toList();
            userRoleService.saveBatch(userRoleList);
        }
        return true;
    }

    @Override
    public boolean deleteByIds(List<Long> ids) {
        userRoleService.deleteByUserIds(ids);
        return true;
    }

    @Override
    public Boolean authRoles(UserVo user) {
        //删除所有关联
        userRoleService.deleteByUserIds(Arrays.asList(user.getUserId()));
        //给角色用户关联表中新增
        List<Long> roleIds = user.getRoleIds();
        if(roleIds!=null&&roleIds.size()>0){
            List<UserRole> userRoleList = roleIds.stream().map(r -> {
                UserRole userRole = new UserRole();
                userRole.setUserId(user.getUserId());
                userRole.setRoleId(r);
                return userRole;
            }).toList();
            userRoleService.saveBatch(userRoleList);
        }
        return true;
    }

    @Override
    public Boolean changeStatus(User user) {
        //查出用户信息
        User byId = this.getById(user.getUserId());
        byId.setStatus(user.getStatus());
        return this.updateById(byId);
    }

    @Override
    public IPage<UserVo> allocatedList(List<Long> userIds, Integer pageNum, Integer pageSize) {
        Page<User> page = new Page(pageNum, pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("del_flag",0);
        if (userIds!=null&&userIds.size()>0) {
            queryWrapper.in("user_id",userIds);
        }
        IPage<User> userIPage = this.page(page,queryWrapper);
        IPage<UserVo> convert = getConvert(userIPage);
        return convert;
    }

    @Override
    public IPage<UserVo> unallocatedList(List<Long> userIds, Integer pageNum, Integer pageSize) {
        Page<User> page = new Page(pageNum, pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("del_flag",0);
        if (userIds!=null&&userIds.size()>0) {
            queryWrapper.notIn("user_id",userIds);
        }
        IPage<User> userIPage = this.page(page,queryWrapper);
        IPage<UserVo> convert = getConvert(userIPage);
        return convert;
    }

    @Override
    public IPage<RoleVo> getAuthRole(UserQueryVo queryVo) {
        //分页查询所有角色
        RoleQueryVo roleQueryVo = new RoleQueryVo();
        roleQueryVo.setPageNum(queryVo.getPageNum());
        roleQueryVo.setPageSize(queryVo.getPageSize());
        IPage<RoleVo> roleIPage = queryByPage(roleQueryVo);
        //查询关联的角色id
        List<Long> roleIds = userRoleService.getRolesByUserId(queryVo.getUserId());
        //遍历或者find，把关联角色置为flag=true，并封装成roleVo
        IPage<RoleVo> roleVoIPage = roleIPage.convert(r -> {
            if (roleIds.contains(r.getRoleId())) {
                r.setFlag(true);
            }
            return r;
        });
        return roleVoIPage;
    }

    public IPage<RoleVo> queryByPage(RoleQueryVo role) {
        Page page = new Page(role.getPageNum(),role.getPageSize());
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",role.getStatus());
        if (StringUtils.isNotEmpty(role.getKeyword())){
            queryWrapper.and(q->{
                q.like("role_name",role.getKeyword())
                        .or().like("role_key",role.getKeyword());
            });
        }
        IPage<Role> roleIPage = roleDao.selectPage(page,queryWrapper);
        IPage<RoleVo> roleVoIPage = roleIPage.convert(r -> {
            RoleVo roleVo = new RoleVo();
            BeanUtils.copyProperties(role, roleVo);
            //获取关联的部门id
            List<Long> deptIds = roleDeptService.getIdByRoleId(r.getRoleId());
            roleVo.setDeptIds(deptIds);
            //获取关联的目录id
            List<Long> menuIds = roleMenuService.getIdByRoleId(r.getRoleId());
            roleVo.setMenuIds(menuIds);
            return roleVo;
        });
        return roleVoIPage;
    }

    @Override
    public UserSnapshotVo getSnapshotById(Serializable id) {
        UserSnapshotVo snapshot = new UserSnapshotVo();
        //查询user信息
        //查询user和role关联表信息
        return snapshot;
    }
}
