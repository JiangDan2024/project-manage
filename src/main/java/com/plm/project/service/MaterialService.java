package com.plm.project.service;

import com.plm.project.entity.Material;
import com.baomidou.mybatisplus.spring.service.IService;
import com.plm.project.vo.query.MaterialQueryVo;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 物料主数据表(Material)表服务接口
 *
 * @author makejava
 * @since 2026-10-25 15:40:54
 */
public interface MaterialService extends IService<Material> {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Material info(Long id);

    /**
     * 分页查询
     *
     * @param material 筛选条件
     * @return 查询结果
     */
    IPage<Material> queryByPage(MaterialQueryVo material);

    /**
     * 新增数据
     *
     * @param material 实例对象
     * @return 实例对象
     */
    boolean add(Material material);

    /**
     * 修改数据
     *
     * @param material 实例对象
     * @return 实例对象
     */
    boolean edit(Material material);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteByIds(List<Long> id);
}
