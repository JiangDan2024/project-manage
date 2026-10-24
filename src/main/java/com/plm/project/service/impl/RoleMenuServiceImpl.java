package com.plm.project.service.impl;

import com.plm.project.entity.RoleMenu;
import com.plm.project.dao.RoleMenuDao;
import com.plm.project.service.RoleMenuService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.plm.project.vo.RoleMenuQueryVo;
import java.util.List;
/**
 * 角色和菜单关联表(RoleMenu)表服务实现类
 *
 * @author makejava
 * @since 2026-10-23 15:21:38
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
}
