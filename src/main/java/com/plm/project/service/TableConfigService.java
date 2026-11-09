package com.plm.project.service;

import com.plm.project.entity.TableConfig;
import com.baomidou.mybatisplus.spring.service.IService;
import java.util.List;

/**
 * 用户表格列个性化配置(TableConfig)表服务接口
 *
 * @author makejava
 * @since 2026-10-25 15:42:15
 */
public interface TableConfigService extends IService<TableConfig> {

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteByIds(List<Long> id);

    boolean saveWidth(List<TableConfig> tableConfigs);

    boolean saveOther(List<TableConfig> tableConfigs);

    List<TableConfig> search(TableConfig tableConfig);
}
