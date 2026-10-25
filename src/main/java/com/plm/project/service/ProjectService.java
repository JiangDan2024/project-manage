package com.plm.project.service;

import com.plm.project.entity.Project;
import com.baomidou.mybatisplus.spring.service.IService;
import com.plm.project.vo.query.ProjectQueryVo;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 项目表(Project)表服务接口
 *
 * @author makejava
 * @since 2026-10-25 15:40:18
 */
public interface ProjectService extends IService<Project> {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Project info(Long id);

    /**
     * 分页查询
     *
     * @param project 筛选条件
     * @return 查询结果
     */
    IPage<Project> queryByPage(ProjectQueryVo project);

    /**
     * 新增数据
     *
     * @param project 实例对象
     * @return 实例对象
     */
    boolean add(Project project);

    /**
     * 修改数据
     *
     * @param project 实例对象
     * @return 实例对象
     */
    boolean edit(Project project);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteByIds(List<Long> id);
}
