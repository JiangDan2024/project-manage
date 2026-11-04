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

