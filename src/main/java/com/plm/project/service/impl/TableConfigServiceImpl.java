package com.plm.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.plm.project.entity.TableConfig;
import com.plm.project.dao.TableConfigDao;
import com.plm.project.service.TableConfigService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;

import java.util.Date;
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
    public boolean deleteByIds(List<Long> id) {
        return this.removeByIds(id);
    }

    @Override
    public boolean saveWidth(List<TableConfig> tableConfigs) {
        List<TableConfig> list = tableConfigs.stream().map(config -> {
            if (config.getId() == null) {
                String s = "12345";
                s.length();
                for (int i = 0; i < s.length(); i++) {
                    char c = s.charAt(i);

                }
                //新增
                //保证用户id-表code-字段名唯一
                TableConfig tableConfig = existOtherUseridWithCodeWithColumnname(config);
                if (tableConfig != null) {
                    tableConfig.setColumnWidth(config.getColumnWidth());
                    config.setUpdateTime(new Date());
                    return tableConfig;
                }
                config.setCreateTime(new Date());
            } else {
                //更新
                config.setUpdateTime(new Date());
            }
            return config;
        }).toList();
        return this.saveOrUpdateBatch(list);
    }

    @Override
    public boolean saveOther(List<TableConfig> tableConfigs) {
        List<TableConfig> list = tableConfigs.stream().map(config -> {
            if (config.getId() == null) {
                //新增
                //保证用户id-表code-字段名唯一
                TableConfig tableConfig = existOtherUseridWithCodeWithColumnname(config);
                if (tableConfig != null) {
                    tableConfig.setColumnSort(config.getColumnSort());
                    tableConfig.setColumnShowFlag(config.getColumnShowFlag());
                    tableConfig.setColumnName(config.getColumnName());
                    config.setUpdateTime(new Date());
                    return tableConfig;
                }
                config.setCreateTime(new Date());
            } else {
                //更新
                config.setUpdateTime(new Date());
            }
            return config;
        }).toList();
        return this.saveOrUpdateBatch(list);
    }

    @Override
    public List<TableConfig> search(TableConfig tableConfig) {
        QueryWrapper<TableConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", tableConfig.getUserId());
        queryWrapper.eq("table_code",tableConfig.getTableCode());
        return this.list(queryWrapper);
    }

    /**
     * 判断用户id-code-列名唯一索引，如果已存在则返回对象按id更新，否则新增记录
     * @param config
     * @return
     */
    private TableConfig existOtherUseridWithCodeWithColumnname(TableConfig config) {
        QueryWrapper<TableConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", config.getUserId())
                .eq("table_code", config.getTableCode())
                .eq("column_field", config.getColumnField());
        TableConfig one = this.getOne(queryWrapper);
        return one;
    }
}
