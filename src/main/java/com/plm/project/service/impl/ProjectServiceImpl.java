package com.plm.project.service.impl;

import com.plm.project.entity.Project;
import com.plm.project.dao.ProjectDao;
import com.plm.project.service.ProjectService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.plm.project.vo.ProjectQueryVo;
import java.util.List;
/**
 * 项目表(Project)表服务实现类
 *
 * @author makejava
 * @since 2026-10-23 15:20:47
 */
@Service("projectService")
public class ProjectServiceImpl extends ServiceImpl<ProjectDao,Project> implements ProjectService {
    
    @Override
    public Project info(Long id) {

        return this.getById(id);
    }

    @Override
    public IPage<Project> queryByPage(ProjectQueryVo project) {
        return null;
    }

    @Override
    public boolean add(Project project) {
        return this.save(project);
    }

    @Override
    public boolean edit(Project project) {
        return this.updateById(project);
    }

    @Override
    public boolean deleteByIds(List<Long> id) {
        return this.removeByIds(id);
    }
}
