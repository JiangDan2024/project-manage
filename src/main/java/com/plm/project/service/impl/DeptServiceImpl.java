package com.plm.project.service.impl;

import com.plm.project.entity.Dept;
import com.plm.project.dao.DeptDao;
import com.plm.project.service.DeptService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.plm.project.vo.DeptQueryVo;
import java.util.List;
/**
 * 部门表(Dept)表服务实现类
 *
 * @author makejava
 * @since 2026-10-23 15:21:09
 */
@Service("deptService")
public class DeptServiceImpl extends ServiceImpl<DeptDao,Dept> implements DeptService {
    
    @Override
    public Dept info(Long id) {

        return this.getById(id);
    }

    @Override
    public IPage<Dept> queryByPage(DeptQueryVo dept) {
        return null;
    }

    @Override
    public boolean add(Dept dept) {
        return this.save(dept);
    }

    @Override
    public boolean edit(Dept dept) {
        return this.updateById(dept);
    }

    @Override
    public boolean deleteByIds(List<Long> id) {
        return this.removeByIds(id);
    }
}
