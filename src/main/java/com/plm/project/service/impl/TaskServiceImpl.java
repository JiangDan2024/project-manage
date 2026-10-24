package com.plm.project.service.impl;

import com.plm.project.entity.Task;
import com.plm.project.dao.TaskDao;
import com.plm.project.service.TaskService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.plm.project.vo.TaskQueryVo;
import java.util.List;
/**
 * 任务表(Task)表服务实现类
 *
 * @author makejava
 * @since 2026-10-23 15:20:54
 */
@Service("taskService")
public class TaskServiceImpl extends ServiceImpl<TaskDao,Task> implements TaskService {
    
    @Override
    public Task info(Long id) {

        return this.getById(id);
    }

    @Override
    public IPage<Task> queryByPage(TaskQueryVo task) {
        return null;
    }

    @Override
    public boolean add(Task task) {
        return this.save(task);
    }

    @Override
    public boolean edit(Task task) {
        return this.updateById(task);
    }

    @Override
    public boolean deleteByIds(List<Long> id) {
        return this.removeByIds(id);
    }
}
