package com.plm.project.controller;

import com.plm.project.entity.Material;
import com.plm.project.service.MaterialService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.*;
import com.plm.project.common.R;
import com.plm.project.vo.query.MaterialQueryVo;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 物料主数据表(Material)表控制层
 *
 * @author makejava
 * @since 2026-10-25 15:40:54
 */
@RestController
@RequestMapping("material")
public class MaterialController {
    /**
     * 服务对象
     */
    @Autowired
    private MaterialService materialService;

    /**
     * 分页查询
     *
     * @param material 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list")
    public R<IPage<Material>> queryByPage(MaterialQueryVo material) {
        return R.ok(this.materialService.queryByPage(material));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("info/{id}")
    public R<Material> queryById(@PathVariable("id") Long id) {
        return R.ok(this.materialService.info(id));
    }

    /**
     * 新增数据
     *
     * @param material 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public R add(@RequestBody Material material) {
        return R.ok(this.materialService.add(material));
    }

    /**
     * 编辑数据
     *
     * @param material 实体
     * @return 编辑结果
     */
    @PutMapping("/edit")
    public R edit(@RequestBody Material material) {
        return R.ok(this.materialService.edit(material));
    }

    /**
     * 删除数据
     *
     * @param ids 主键
     * @return 删除是否成功
     */
    @DeleteMapping("delete")
    public R deleteByIds(@RequestParam("ids") List<Long> ids) {
        return R.ok(this.materialService.deleteByIds(ids));
    }

}

