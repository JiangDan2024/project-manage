package com.plm.project.service;

import com.plm.project.entity.Dept;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.IService;
import com.plm.project.vo.DeptQueryVo;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
/**
 * 部门表(Dept)表服务接口
 *
 * @author makejava
 * @since 2026-10-23 15:21:09
 */
public interface DeptService extends IService<Dept> {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Dept info(Long id);

    /**
     * 分页查询
     *
     * @param dept 筛选条件
     * @return 查询结果
     */
    IPage<Dept> queryByPage(DeptQueryVo dept);

    /**
     * 新增数据
     *
     * @param dept 实例对象
     * @return 实例对象
     */
    boolean add(Dept dept);

    /**
     * 修改数据
     *
     * @param dept 实例对象
     * @return 实例对象
     */
    boolean edit(Dept dept);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteByIds(List<Long> id);
}
