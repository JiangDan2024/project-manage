package com.plm.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.plm.project.entity.Task;
import com.plm.project.dao.TaskDao;
import com.plm.project.service.TaskService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.plm.project.vo.query.TaskQueryVo;

import java.util.Date;
import java.util.List;
/**
 * 任务表(Task)表服务实现类
 *
 * @author makejava
 * @since 2026-10-25 15:42:31
 */
@Service("taskService")
public class TaskServiceImpl extends ServiceImpl<TaskDao,Task> implements TaskService {
    
    @Override
    public Task info(Long id) {
        return this.getById(id);
    }

    @Override
    public IPage<Task> queryByPage(TaskQueryVo task) {
        Page<Task> page = new Page<>(task.getPageNum(), task.getPageSize());
        QueryWrapper<Task> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag","0");
        if (StringUtils.isNotBlank(task.getKeyword())) {
            queryWrapper.and(q->{
               q.like("task_title",task.getKeyword())
                       .or()
                       .eq("task_no",task.getKeyword());
            });
        }
        if (task.getStatus() != null) {
            queryWrapper.eq("status",task.getStatus());
        }
        if (task.getTaskType() != null) {
            queryWrapper.eq("task_type",task.getTaskType());
        }
        if (task.getPriority() != null) {
            queryWrapper.eq("priority",task.getPriority());
        }
        IPage<Task> iPage = this.page(page,queryWrapper);
        return iPage;
    }

    @Override
    public boolean add(Task task) {
        task.setCreatedTime(new Date());
        return this.save(task);
    }

    @Override
    public boolean edit(Task task) {
        task.setUpdatedTime(new Date());
        return this.updateById(task);
    }

    @Override
    public boolean deleteByIds(List<Long> ids) {
        return this.removeByIds(ids);
    }
}
