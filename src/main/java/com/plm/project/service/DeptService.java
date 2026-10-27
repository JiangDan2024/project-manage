package com.plm.project.service;

import com.plm.project.dto.DeleteCheckResultDto;
import com.plm.project.entity.Dept;
import com.baomidou.mybatisplus.spring.service.IService;
import com.plm.project.vo.DeptVo;

import java.util.List;

/**
 * 部门表(Dept)表服务接口
 *
 * @author makejava
 * @since 2026-10-25 15:40:42
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
     * 查询树目录
     *
     * @param dept 筛选条件
     * @return 查询结果
     */
    List<DeptVo> getTreeList(Dept dept);

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
     * @param ids 主键
     * @return 是否成功
     */
    DeleteCheckResultDto deleteByIds(List<Long> ids);

    List<DeptVo> queryByExcludeNoed(Long deptId);

    Boolean saveSort(List<Long> deptIds, List<Integer> sorts);

    List<Dept> getList(Dept dept);
}
