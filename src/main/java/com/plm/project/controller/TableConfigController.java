package com.plm.project.controller;

import com.plm.project.entity.TableConfig;
import com.plm.project.service.TableConfigService;
import org.springframework.web.bind.annotation.*;
import com.plm.project.common.R;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户表格列个性化配置(TableConfig)表控制层
 *
 * @author makejava
 * @since 2026-10-25 15:42:15
 */
@RestController
@RequestMapping("system/tableConfig")
public class TableConfigController {
    /**
     * 服务对象
     */
    @Autowired
    private TableConfigService tableConfigService;

    /**
     * 保存列宽
     * @param tableConfigs
     * @return
     */
    @PostMapping("/saveWidth")
    public R<Boolean> saveWidth(@RequestBody List<TableConfig> tableConfigs) {
        return R.ok(tableConfigService.saveWidth(tableConfigs));
    }

    /**
     * 保存列是否展示、列的顺序、列名
     * @param tableConfigs
     * @return
     */
    @PostMapping("/saveOther")
    public R<Boolean> saveOther(@RequestBody List<TableConfig> tableConfigs) {
        return R.ok(tableConfigService.saveOther(tableConfigs));
    }

    /**
     * 根据用户id与表code查询对应列配置
     * @param tableConfig
     * @return
     */
    @GetMapping("/search")
    public R<List<TableConfig>> search(TableConfig tableConfig) {
        return R.ok(tableConfigService.search(tableConfig));
    }

    /**
     * 删除数据
     *
     * @param ids 主键
     * @return 删除是否成功
     */
    @DeleteMapping("delete")
    public R deleteByIds(@RequestParam("ids") List<Long> ids) {
        return R.ok(this.tableConfigService.deleteByIds(ids));
    }

}

