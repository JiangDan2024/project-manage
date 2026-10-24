package com.plm.project.service;

import com.plm.project.entity.Task;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.IService;
import com.plm.project.vo.TaskQueryVo;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
/**
 * 任务表(Task)表服务接口
 *
 * @author makejava
 * @since 2026-10-23 15:20:54
 */
public interface TaskService extends IService<Task> {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Task info(Long id);

    /**
     * 分页查询
     *
     * @param task 筛选条件
     * @return 查询结果
     */
    IPage<Task> queryByPage(TaskQueryVo task);

    /**
     * 新增数据
     *
     * @param task 实例对象
     * @return 实例对象
     */
    boolean add(Task task);

    /**
     * 修改数据
     *
     * @param task 实例对象
     * @return 实例对象
     */
    boolean edit(Task task);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteByIds(List<Long> id);
}
