package com.plm.project.service;

import com.plm.project.dto.DeleteCheckResultDto;
import com.plm.project.entity.Menu;
import com.baomidou.mybatisplus.spring.service.IService;
import com.plm.project.vo.MenuRoleVo;
import com.plm.project.vo.MenuTreeVo;
import com.plm.project.vo.MenuVo;
import com.plm.project.vo.RouterVo;
import com.plm.project.vo.query.MenuQueryVo;
import java.util.List;
import java.util.Set;

/**
 * 菜单表(Menu)表服务接口
 *
 * @author makejava
 * @since 2026-10-25 15:41:14
 */
public interface MenuService extends IService<Menu> {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Menu info(Long id);

    /**
     * 分页查询
     *
     * @param menu 筛选条件
     * @return 查询结果
     */
    List<MenuVo> getList(MenuQueryVo menu);

    /**
     * 新增数据
     *
     * @param menu 实例对象
     * @return 实例对象
     */
    boolean add(MenuVo menu);

    /**
     * 修改数据
     *
     * @param menu 实例对象
     * @return 实例对象
     */
    boolean edit(MenuVo menu);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    DeleteCheckResultDto deleteByIds(List<Long> id);

    List<MenuTreeVo> treeSelect(MenuQueryVo menu);

    MenuRoleVo roleMenuTreeselect(Long roleId);

    Boolean updateSort(List<Menu> menus);

    DeleteCheckResultDto deleteById(Long id);

    Set<String> selectMenuPermsByRoleId(Long roleId);

    List<MenuVo> selectMenuTreeByUserId(Long userId);

    List<RouterVo> buildMenus(List<MenuVo> menuVoList);
}
