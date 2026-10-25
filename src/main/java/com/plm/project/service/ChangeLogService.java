package com.plm.project.service;

import com.plm.project.entity.ChangeLog;
import com.baomidou.mybatisplus.spring.service.IService;
import com.plm.project.vo.query.ChangeLogQueryVo;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 变更历史记录表(ChangeLog)表服务接口
 *
 * @author makejava
 * @since 2026-10-25 15:40:28
 */
public interface ChangeLogService extends IService<ChangeLog> {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ChangeLog info(Long id);

    /**
     * 分页查询
     *
     * @param changeLog 筛选条件
     * @return 查询结果
     */
    IPage<ChangeLog> queryByPage(ChangeLogQueryVo changeLog);

    /**
     * 新增数据
     *
     * @param changeLog 实例对象
     * @return 实例对象
     */
    boolean add(ChangeLog changeLog);

    /**
     * 修改数据
     *
     * @param changeLog 实例对象
     * @return 实例对象
     */
    boolean edit(ChangeLog changeLog);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteByIds(List<Long> id);
}
