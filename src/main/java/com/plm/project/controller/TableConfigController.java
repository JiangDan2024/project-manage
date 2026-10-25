package com.plm.project.controller;

import com.plm.project.entity.TableConfig;
import com.plm.project.service.TableConfigService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.*;
import com.plm.project.common.R;
import com.plm.project.vo.query.TableConfigQueryVo;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户表格列个性化配置(TableConfig)表控制层
 *
 * @author makejava
 * @since 2026-10-25 15:42:15
 */
@RestController
@RequestMapping("tableConfig")
public class TableConfigController {
    /**
     * 服务对象
     */
    @Autowired
    private TableConfigService tableConfigService;

    /**
     * 分页查询
     *
     * @param tableConfig 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list")
    public R<IPage<TableConfig>> queryByPage(TableConfigQueryVo tableConfig) {
        return R.ok(this.tableConfigService.queryByPage(tableConfig));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("info/{id}")
    public R<TableConfig> queryById(@PathVariable("id") Long id) {
        return R.ok(this.tableConfigService.info(id));
    }

    /**
     * 新增数据
     *
     * @param tableConfig 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public R add(@RequestBody TableConfig tableConfig) {
        return R.ok(this.tableConfigService.add(tableConfig));
    }

    /**
     * 编辑数据
     *
     * @param tableConfig 实体
     * @return 编辑结果
     */
    @PutMapping("/edit")
    public R edit(@RequestBody TableConfig tableConfig) {
        return R.ok(this.tableConfigService.edit(tableConfig));
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

