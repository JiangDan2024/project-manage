package com.plm.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.plm.project.entity.Project;
import com.plm.project.dao.ProjectDao;
import com.plm.project.service.ProjectService;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.plm.project.vo.query.ProjectQueryVo;

import java.util.Date;
import java.util.List;
/**
 * 项目表(Project)表服务实现类
 *
 * @author makejava
 * @since 2026-10-25 15:40:18
 */
@Service("projectService")
public class ProjectServiceImpl extends ServiceImpl<ProjectDao,Project> implements ProjectService {
    
    @Override
    public Project info(Long id) {

        return this.getById(id);
    }

    @Override
    public IPage<Project> queryByPage(ProjectQueryVo project) {
        Page<Project> page = new Page<Project>(project.getPageNum(), project.getPageSize());
        QueryWrapper<Project> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag","0");
        if (StringUtils.isNotEmpty(project.getKeyword())){
            queryWrapper.and(q->{
                    q.like("project_name",project.getKeyword())
                            .or()
                            .eq("project_no",project.getKeyword());
            });
        }
        if (project.getStatus()!=null){
            queryWrapper.eq("status",project.getStatus());
        }
        if (project.getPriority()!=null){
            queryWrapper.eq("priority",project.getPriority());
        }
        IPage<Project> iPage = this.page(page,queryWrapper);
        return iPage;
    }

    @Override
    public boolean add(Project project) {
        project.setCreatedTime(new Date());
        return this.save(project);
    }

    @Override
    public boolean edit(Project project) {
        project.setUpdatedTime(new Date());
        return this.updateById(project);
    }

    @Override
    public boolean deleteByIds(List<Long> id) {
        return this.removeByIds(id);
    }
}
