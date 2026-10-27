package com.plm.project.controller;

import com.plm.project.common.BizCodeEnume;
import com.plm.project.dto.DeleteCheckResultDto;
import com.plm.project.entity.Menu;
import com.plm.project.exception.MenuExecption;
import com.plm.project.service.MenuService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.plm.project.vo.MenuRoleVo;
import com.plm.project.vo.MenuVo;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.plm.project.common.R;
import com.plm.project.vo.query.MenuQueryVo;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 菜单表(Menu)表控制层
 *
 * @author makejava
 * @since 2026-10-25 15:41:14
 */
@RestController
@RequestMapping("menu")
public class MenuController {
    /**
     * 服务对象
     */
    @Autowired
    private MenuService menuService;

    /**
     * 查询list
     *
     * @param menu 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list")
    public R<List<Menu>> getList(MenuQueryVo menu) {
        return R.ok(this.menuService.getList(menu));
    }

    /**
     * 查询树目录
     * @param menu
     * @return
     */
    @GetMapping("/treeselect")
    public R<List<MenuVo>> treeSelect(MenuQueryVo menu){
        return R.ok(this.menuService.treeSelect(menu));
    }

    /**
     * 加载对应角色菜单列表树
     * @param roleId
     * @return
     */
    @GetMapping("/roleMenuTreeselect/{roleId}")
    public R<MenuRoleVo> roleMenuTreeselect(@PathVariable("roleId") Long roleId){
        return R.ok(this.menuService.roleMenuTreeselect(roleId));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("info/{id}")
    public R<Menu> queryById(@PathVariable("id") Long id) {
        return R.ok(this.menuService.info(id));
    }

    /**
     * 新增数据
     *
     * @param menu 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public R add(@Valid @RequestBody MenuVo menu) {
        try{
            return R.ok(this.menuService.add(menu));
        }catch (MenuExecption e){
            return R.error(BizCodeEnume.MENU_EXCEPTION.getCode(),BizCodeEnume.MENU_EXCEPTION.getMessage(),e);
        }
    }

    /**
     * 编辑数据
     *
     * @param menu 实体
     * @return 编辑结果
     */
    @PutMapping("/edit")
    public R edit(@Valid @RequestBody MenuVo menu) {
        try{
            return R.ok(this.menuService.edit(menu));
        }catch (MenuExecption e){
            return R.error(BizCodeEnume.MENU_EXCEPTION.getCode(),BizCodeEnume.MENU_EXCEPTION.getMessage(),e);
        }
    }

    /**
     * 批量更新菜单顺序
     * @param menuIds
     * @param orderNums
     * @return
     */
    @PutMapping("/updateSort")
    public R<Boolean> updateSort(@RequestBody List<Long> menuIds,List<Integer> orderNums) {
        return R.ok(this.menuService.updateSort(menuIds,orderNums));
    }

    /**
     * 删除单条数据
     * @param id
     * @return
     */

    @DeleteMapping("delete")
    public R<DeleteCheckResultDto> deleteById(@RequestBody Long id){
        return R.ok(this.menuService.deleteById(id));
    }

    /**
     * 批量删除数据
     *
     * @param ids 主键
     * @return 删除是否成功
     */
    @DeleteMapping("deleteBatch")
    public R<DeleteCheckResultDto> deleteByIds(@RequestParam("ids") List<Long> ids) {
        return R.ok(this.menuService.deleteByIds(ids));
    }

}

