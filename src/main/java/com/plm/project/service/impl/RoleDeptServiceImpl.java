package com.plm.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.plm.project.entity.RoleDept;
import com.plm.project.dao.RoleDeptDao;
import com.plm.project.service.RoleDeptService;
import com.plm.project.vo.RoleVo;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.plm.project.vo.query.RoleDeptQueryVo;
import java.util.List;
/**
 * 角色和部门关联表(RoleDept)表服务实现类
 *
 * @author makejava
 * @since 2026-10-25 15:41:48
 */
@Service("roleDeptService")
public class RoleDeptServiceImpl extends ServiceImpl<RoleDeptDao,RoleDept> implements RoleDeptService {

    public RoleDeptServiceImpl(DeptServiceImpl deptService) {
    }

    @Override
    public RoleDept info(Long id) {

        return this.getById(id);
    }

    @Override
    public IPage<RoleDept> queryByPage(RoleDeptQueryVo roleDept) {
        return null;
    }

    @Override
    public boolean add(RoleDept roleDept) {
        return this.save(roleDept);
    }

    @Override
    public boolean edit(RoleDept roleDept) {
        return this.updateById(roleDept);
    }

    @Override
    public boolean deleteByIds(List<Long> id) {
        return this.removeByIds(id);
    }

    @Override
    public List<Long> getIdByRoleId(Long roleId) {
        List<RoleDept> list = this.list(new QueryWrapper<RoleDept>().eq("role_id", roleId));
        List<Long> deptIds = list.stream().map(RoleDept::getDeptId).toList();
        return deptIds;
    }

    @Override
    public void editByRoleId(RoleVo role) {
        //根据roleid删除所有关联
        boolean b = this.remove(new QueryWrapper<RoleDept>().eq("role_id", role.getRoleId()));
        //重新新增
        if (b){
            List<RoleDept> roleDepts = role.getDeptIds().stream().map(rd -> {
                RoleDept roleDept = new RoleDept();
                roleDept.setDeptId(rd);
                roleDept.setRoleId(role.getRoleId());
                return roleDept;
            }).toList();
            this.saveBatch(roleDepts);
        }
    }

    @Override
    public void deleteByRoleIds(List<Long> ids) {
        this.remove(new QueryWrapper<RoleDept>().in("role_id", ids));
    }

}
