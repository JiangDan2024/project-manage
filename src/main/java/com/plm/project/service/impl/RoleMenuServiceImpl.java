package com.plm.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.plm.project.entity.RoleMenu;
import com.plm.project.dao.RoleMenuDao;
import com.plm.project.service.RoleMenuService;
import com.plm.project.vo.RoleVo;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.plm.project.vo.query.RoleMenuQueryVo;
import java.util.List;
/**
 * 角色和菜单关联表(RoleMenu)表服务实现类
 *
 * @author makejava
 * @since 2026-10-25 15:41:59
 */
@Service("roleMenuService")
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuDao,RoleMenu> implements RoleMenuService {
    
    @Override
    public RoleMenu info(Long id) {

        return this.getById(id);
    }

    @Override
    public IPage<RoleMenu> queryByPage(RoleMenuQueryVo roleMenu) {
        return null;
    }

    @Override
    public boolean add(RoleMenu roleMenu) {
        return this.save(roleMenu);
    }

    @Override
    public boolean edit(RoleMenu roleMenu) {
        return this.updateById(roleMenu);
    }

    @Override
    public boolean deleteByIds(List<Long> id) {
        return this.removeByIds(id);
    }

    @Override
    public List<Long> getIdByRoleId(Long roleId) {
        List<RoleMenu> list = this.list(new QueryWrapper<RoleMenu>().eq("role_id", roleId));
        List<Long> menuIds = list.stream().map(RoleMenu::getMenuId).toList();
        return menuIds;
    }

    @Override
    public void editByRoleId(RoleVo role) {
        //根据roleid删除所有关联
        boolean b = this.remove(new QueryWrapper<RoleMenu>().eq("role_id", role.getRoleId()));
        //重新新增
        if (b){
            List<RoleMenu> roleMenus = role.getMenuIds().stream().map(rm -> {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setMenuId(rm);
                roleMenu.setRoleId(role.getRoleId());
                return roleMenu;
            }).toList();
            this.saveBatch(roleMenus);
        }
    }

    @Override
    public void deleteByRoleIds(List<Long> ids) {
        this.remove(new QueryWrapper<RoleMenu>().in("role_id", ids));
    }

}
