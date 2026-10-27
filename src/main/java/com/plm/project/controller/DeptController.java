package com.plm.project.controller;

import com.plm.project.dto.DeleteCheckResultDto;
import com.plm.project.entity.Dept;
import com.plm.project.service.DeptService;
import com.plm.project.vo.DeptVo;
import org.springframework.web.bind.annotation.*;
import com.plm.project.common.R;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 部门表(Dept)表控制层
 *
 * @author makejava
 * @since 2026-10-25 15:40:42
 */
@RestController
@RequestMapping("dept")
public class DeptController {
    /**
     * 服务对象
     */
    @Autowired
    private DeptService deptService;

    /**
     * 查询树目录
     *
     * @param dept 筛选条件
     * @return 查询结果
     */
    @GetMapping("/treeList")
    public R<List<DeptVo>> getTreeList(Dept dept) {
        return R.ok(this.deptService.getTreeList(dept));
    }

    /**
     * 平铺查询list
     * @param dept
     * @return
     */
    @GetMapping("/list")
    public R<List<Dept>> list(Dept dept) {
        return R.ok(this.deptService.getList(dept));
    }

    /**
     * 查询除当前节点以外的其他节点，不使用树目录（但为什么呢）
     * @param deptId
     * @return
     */
    @GetMapping("/list/exclude/{deptId}")
    public R<List<DeptVo>> queryByExcludeNoed(@PathVariable("deptId") Long deptId) {
        return R.ok(this.deptService.queryByExcludeNoed(deptId));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("info/{id}")
    public R<Dept> queryById(@PathVariable("id") Long id) {
        return R.ok(this.deptService.info(id));
    }

    /**
     * 新增数据
     *
     * @param dept 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public R add(@RequestBody Dept dept) {
        return R.ok(this.deptService.add(dept));
    }

    /**
     * 编辑数据
     *
     * @param dept 实体
     * @return 编辑结果
     */
    @PutMapping("/edit")
    public R<Boolean> edit(@RequestBody Dept dept) {
        return R.ok(this.deptService.edit(dept));
    }


    @PutMapping("/save/sort")
    public R<Boolean> saveSort(@RequestBody List<Long> deptIds,@RequestBody List<Integer> sorts) {
        return R.ok(this.deptService.saveSort(deptIds,sorts));
    }
    /**
     * 删除数据
     *
     * @param ids 主键
     * @return 删除是否成功
     */
    @DeleteMapping("delete")
    public R<DeleteCheckResultDto> deleteByIds(@RequestParam("ids") List<Long> ids) {
        return R.ok(this.deptService.deleteByIds(ids));
    }

}

