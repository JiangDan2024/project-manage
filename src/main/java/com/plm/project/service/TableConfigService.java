package com.plm.project.service;

import com.plm.project.entity.TableConfig;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.IService;
import com.plm.project.vo.TableConfigQueryVo;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
/**
 * 用户表格列个性化配置(TableConfig)表服务接口
 *
 * @author makejava
 * @since 2026-10-23 15:21:44
 */
public interface TableConfigService extends IService<TableConfig> {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TableConfig info(Long id);

    /**
     * 分页查询
     *
     * @param tableConfig 筛选条件
     * @return 查询结果
     */
    IPage<TableConfig> queryByPage(TableConfigQueryVo tableConfig);

    /**
     * 新增数据
     *
     * @param tableConfig 实例对象
     * @return 实例对象
     */
    boolean add(TableConfig tableConfig);

    /**
     * 修改数据
     *
     * @param tableConfig 实例对象
     * @return 实例对象
     */
    boolean edit(TableConfig tableConfig);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteByIds(List<Long> id);
}
