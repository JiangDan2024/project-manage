package com.plm.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.plm.project.common.UserContext;
import com.plm.project.dto.DeleteCheckResultDto;
import com.plm.project.entity.Menu;
import com.plm.project.dao.MenuDao;
import com.plm.project.entity.UserRole;
import com.plm.project.exception.MenuExecption;
import com.plm.project.service.MenuService;
import com.plm.project.service.RoleMenuService;
import com.plm.project.service.UserRoleService;
import com.plm.project.vo.MenuRoleVo;
import com.plm.project.vo.MenuVo;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.plm.project.vo.query.MenuQueryVo;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2026-10-25 15:41:14
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuDao,Menu> implements MenuService {
    
    @Autowired
    RoleMenuService roleMenuService;

    @Autowired
    UserRoleService userRoleService;
    
    @Override
    public Menu info(Long id) {
        return this.getById(id);
    }

    @Override
    public List<Menu> getList(MenuQueryVo menu) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(menu.getMenuName())) {
            queryWrapper.like("menu_name", menu.getMenuName());
        }
        if(menu.getStatus()!=null){
            queryWrapper.eq("visible",menu.getStatus());
        }
        return this.list(queryWrapper);
    }

    @Override
    public boolean add(MenuVo menu) {
        //校验菜单名称是否唯一
        checkMenuNameUnique(menu.getMenu().getMenuName());
        //校验外链是否是http(s)开头
        //如果不是外链，路由地址和名称应唯一
        checkUrl(menu.getIsFrame(),menu.getMenu().getUrl());
        Menu menu1 = menu.getMenu();
        menu1.setCreateBy(UserContext.get().getLoginName());
        menu1.setCreateTime(new Date());
        this.save(menu1);
        return true;
    }

    private void checkUrl(Boolean isFrame, String url) {
        if(isFrame){
            //判断外链格式
            if(url.startsWith("http")){
                throw new MenuExecption("外链格式错误，应以http(s)开头");
            }
        }else{
            //判断路由地址是否唯一
            long count = this.count(new QueryWrapper<Menu>().eq("url", url));
            if(count > 0){
                throw new MenuExecption("路由地址已存在");
            }
        }
    }

    private void checkMenuNameUnique(String menuName) {
        long count = this.count(new QueryWrapper<Menu>().eq("menu_name", menuName));
        if (count > 0) {
            throw new MenuExecption("菜单名称已存在");
        }
    }

    @Override
    public boolean edit(MenuVo menu) {
        //校验菜单名称唯一
        checkMenuNameUnique(menu.getMenu().getMenuName());
        //校验路由地址
        checkUrl(menu.getIsFrame(),menu.getMenu().getUrl());
        //父菜单不能是自己
        if(menu.getMenu().getParentId()==menu.getMenu().getMenuId()){
            throw new MenuExecption("上级菜单不能选择自己");
        }
        Menu menu1 = menu.getMenu();
        menu1.setUpdateBy(UserContext.get().getLoginName());
        menu1.setUpdateTime(new Date());
        return this.updateById(menu1);
    }

    @Override
    public DeleteCheckResultDto deleteByIds(List<Long> id) {
        DeleteCheckResultDto deleteCheckResultDto = new DeleteCheckResultDto();
        List<DeleteCheckResultDto.FailDetail> failList = new ArrayList<>();
        List<Menu> menuList = this.list(new QueryWrapper<Menu>().eq("visible", 0).in("menu_id", id));
        //查出分配的用户
        List<UserRole> userRoles = userRoleService.getUserByRoleIds(id);
        Set<Long> hasUsers = new HashSet<>();
        if (userRoles!=null&&userRoles.size()>0){
            hasUsers = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toSet());
        }
        //查出子菜单
        List<Menu> parentId = this.list(new QueryWrapper<Menu>().in("parent_id", id));
        Set<Long> hasSubMenu = new HashSet<>();
        if (parentId!=null&&parentId.size()>0){
            hasSubMenu = parentId.stream().map(Menu::getParentId).collect(Collectors.toSet());
        }
        List<Long> successList = new ArrayList<>();
        //遍历
        for (Menu menu : menuList) {
            boolean isFail = false;
            if(hasUsers.contains(menu.getMenuId())){
                DeleteCheckResultDto.FailDetail failDetail = new DeleteCheckResultDto.FailDetail();
                failDetail.setId(menu.getMenuId());
                failDetail.setName(menu.getMenuName());
                failDetail.setMsg("该菜单仍存在绑定的用户");
                failList.add(failDetail);
                isFail = true;
            }
            if(hasSubMenu.contains(menu.getMenuId())){
                DeleteCheckResultDto.FailDetail failDetail = new DeleteCheckResultDto.FailDetail();
                failDetail.setId(menu.getMenuId());
                failDetail.setName(menu.getMenuName());
                failDetail.setMsg("该菜单仍存在子菜单");
                failList.add(failDetail);
                isFail = true;
            }
            if(!isFail){
                deleteCheckResultDto.setSuccess(true);
                successList.add(menu.getMenuId());
            }
        }
        deleteCheckResultDto.setFailList(failList);
        this.removeByIds(successList);
        return deleteCheckResultDto;
    }

    @Override
    public List<MenuVo> treeSelect(MenuQueryVo menu) {
        List<Menu> list = getList(menu);
        List<MenuVo> menuVos = list.stream().map(m -> {
            MenuVo menuVo = new MenuVo();
            menuVo.setMenu(m);
            List<MenuVo> children = computeChildren(m, list);
            menuVo.setChildren(children);
            return menuVo;
        }).toList();
        return menuVos;
    }

    @Override
    public MenuRoleVo roleMenuTreeselect(Long roleId) {
        List<Long> menuIds = roleMenuService.getIdByRoleId(roleId);
        if (menuIds!=null && menuIds.size()>0){
            MenuRoleVo menuRoleVo = new MenuRoleVo();
            List<Menu> menus = this.listByIds(menuIds);
            List<MenuRoleVo.MenuSampleVo> list = menus.stream().map(m -> {
                MenuRoleVo.MenuSampleVo sampleVo = new MenuRoleVo.MenuSampleVo();
                sampleVo.setId(m.getMenuId());
                sampleVo.setDisabled(m.getVisible() == "0");
                sampleVo.setLabel(m.getMenuName());
                List<MenuRoleVo.MenuSampleVo> children = computeSampleChildren(m, menus);
                sampleVo.setChildren(children);
                return sampleVo;
            }).toList();
            menuRoleVo.setMenus(list);
            menuRoleVo.setCheckedKeys(menuIds);
        }
        return null;
    }

    @Override
    public Boolean updateSort(List<Long> menuIds, List<Integer> orderNums) {
        List<Menu> menus = new ArrayList<>();
        for (int i = 0;i<menuIds.size();i++){
            Menu menu = new Menu();
            menu.setMenuId(menuIds.get(i));
            menu.setOrderNum(orderNums.get(i));
            menus.add(menu);
        }
        return this.updateBatchById(menus);
    }

    @Override
    public DeleteCheckResultDto deleteById(Long id) {
        Menu menu = this.getById(id);
        DeleteCheckResultDto deleteCheckResultDto = new DeleteCheckResultDto();
        //是否存在子目录
        List<Menu> subList = this.list(new QueryWrapper<Menu>().eq("visible", "0").eq("parent_id", id));
        List<Long> userByRoleId = userRoleService.getUserByRoleId(id);
        List<DeleteCheckResultDto.FailDetail> failList = new ArrayList<>();
        if (userByRoleId!=null&&userByRoleId.size()>0){
            DeleteCheckResultDto.FailDetail failDetail = new DeleteCheckResultDto.FailDetail();
            failDetail.setId(menu.getMenuId());
            failDetail.setName(menu.getMenuName());
            failDetail.setMsg("该菜单仍存在绑定的用户");
            failList.add(failDetail);
        }
        if (subList!=null&&subList.size()>0){
            DeleteCheckResultDto.FailDetail failDetail = new DeleteCheckResultDto.FailDetail();
            failDetail.setId(menu.getMenuId());
            failDetail.setName(menu.getMenuName());
            failDetail.setMsg("该菜单仍存在子目录");
            failList.add(failDetail);
        }
        if (failList==null||failList.size()==0){
            deleteCheckResultDto.setSuccess(true);
        }
        deleteCheckResultDto.setFailList(failList);
        return deleteCheckResultDto;
    }

    private List<MenuRoleVo.MenuSampleVo> computeSampleChildren(Menu m, List<Menu> menus) {
        List<Menu> menuList = menus.stream().filter(menu -> menu.getParentId() == m.getMenuId()).toList();
        if(menuList!=null && menuList.size()>0){
            List<MenuRoleVo.MenuSampleVo> list = menuList.stream().map(menu -> {
                MenuRoleVo.MenuSampleVo sampleVo = new MenuRoleVo.MenuSampleVo();
                sampleVo.setId(menu.getMenuId());
                sampleVo.setDisabled(menu.getVisible() == "0");
                sampleVo.setLabel(menu.getMenuName());
                List<MenuRoleVo.MenuSampleVo> children = computeSampleChildren(menu, menus);
                sampleVo.setChildren(children);
                return sampleVo;
            }).toList();
            return list;
        }
        return null;
    }

    private List<MenuVo> computeChildren(Menu menu,List<Menu> list) {
        List<Menu> menuList = list.stream().filter(m -> m.getParentId() == menu.getMenuId()).toList();
        if(menuList!=null && menuList.size()>0){
            List<MenuVo> childrenList = menuList.stream().map(m -> {
                MenuVo menuVo = new MenuVo();
                menuVo.setMenu(m);
                List<MenuVo> children = computeChildren(m, list);
                menuVo.setChildren(children);
                return menuVo;
            }).toList();
            return childrenList;
        }
        return null;
    }
}
