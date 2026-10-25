package com.plm.project.service.impl;

import com.plm.project.entity.TableConfig;
import com.plm.project.dao.TableConfigDao;
import com.plm.project.service.TableConfigService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.plm.project.vo.query.TableConfigQueryVo;
import java.util.List;
/**
 * 用户表格列个性化配置(TableConfig)表服务实现类
 *
 * @author makejava
 * @since 2026-10-25 15:42:15
 */
@Service("tableConfigService")
public class TableConfigServiceImpl extends ServiceImpl<TableConfigDao,TableConfig> implements TableConfigService {
    
    @Override
    public TableConfig info(Long id) {

        return this.getById(id);
    }

    @Override
    public IPage<TableConfig> queryByPage(TableConfigQueryVo tableConfig) {
        return null;
    }

    @Override
    public boolean add(TableConfig tableConfig) {
        return this.save(tableConfig);
    }

    @Override
    public boolean edit(TableConfig tableConfig) {
        return this.updateById(tableConfig);
    }

    @Override
    public boolean deleteByIds(List<Long> id) {
        return this.removeByIds(id);
    }
}
