package com.plm.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.plm.project.entity.Role;
import com.plm.project.dao.RoleDao;
import com.plm.project.service.RoleService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.plm.project.vo.query.RoleQueryVo;
import java.util.List;
/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2026-10-23 15:21:23
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleDao,Role> implements RoleService {
    
    @Override
    public Role info(Long id) {

        return this.getById(id);
    }

    @Override
    public IPage<Role> queryByPage(RoleQueryVo role) {
        Page<Role> page = new Page(role.getPageNum(),role.getPageSize());
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag","0");
        if(StringUtils.isNotEmpty(role.getKeyword())){
            queryWrapper.and(i->{
                i.like("role_name",role.getKeyword())
                        .or().like("role_key",role.getKeyword());
            });
        }
        if (role.getStatus()!=null) {
            queryWrapper.eq("status",role.getStatus());
        }
        IPage<Role> roleIPage = this.page(page,queryWrapper);
        return roleIPage;
    }

    @Override
    public boolean add(Role role) {
        return this.save(role);
    }

    @Override
    public boolean edit(Role role) {
        return this.updateById(role);
    }

    @Override
    public boolean deleteByIds(List<Long> id) {
        return this.removeByIds(id);
    }
}
