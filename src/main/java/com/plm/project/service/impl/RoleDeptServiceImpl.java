package com.plm.project.service.impl;

import com.plm.project.entity.RoleDept;
import com.plm.project.dao.RoleDeptDao;
import com.plm.project.service.RoleDeptService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.plm.project.vo.RoleDeptQueryVo;
import java.util.List;
/**
 * 角色和部门关联表(RoleDept)表服务实现类
 *
 * @author makejava
 * @since 2026-10-23 15:21:31
 */
@Service("roleDeptService")
public class RoleDeptServiceImpl extends ServiceImpl<RoleDeptDao,RoleDept> implements RoleDeptService {
    
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
}
