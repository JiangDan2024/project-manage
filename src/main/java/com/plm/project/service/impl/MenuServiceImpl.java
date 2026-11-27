package com.plm.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.plm.project.common.UserConstants;
import com.plm.project.common.UserContext;
import com.plm.project.dto.DeleteCheckResultDto;
import com.plm.project.entity.Menu;
import com.plm.project.dao.MenuDao;
import com.plm.project.entity.UserRole;
import com.plm.project.exception.MenuExecption;
import com.plm.project.service.MenuService;
import com.plm.project.service.RoleMenuService;
import com.plm.project.service.UserRoleService;
import com.plm.project.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    @Autowired
    private MenuDao menuDao;

    @Override
    public Menu info(Long id) {
        return this.getById(id);
    }

    @Override
    public List<MenuVo> getList(MenuQueryVo menu) {
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(menu.getMenuName())) {
            wrapper.like("menu_name", menu.getMenuName());
        }
        if(StringUtils.isNotBlank(menu.getStatus())) {
            wrapper.eq("status", menu.getStatus());
        }
        List<Menu> list = this.list(wrapper);
        //遍历构建vo
        //滤出一级菜单及父节点不存在的子节点
        Set<Long> menuIds = list.stream().map(Menu::getMenuId).collect(Collectors.toSet());
        List<MenuVo> menuVos = list.stream()
                .filter(m -> m.getParentId().equals(0L) || !menuIds.contains(m.getParentId()))
                .sorted(new Comparator<Menu>() {

                    @Override
                    public int compare(Menu m1, Menu m2) {
                        return m1.getOrderNum() - m2.getOrderNum();
                    }
                })
                .map(m -> {
                    MenuVo menuVo = new MenuVo();
                    BeanUtils.copyProperties(m, menuVo);
                    List<MenuVo> children = computeChildren(m, list);
                    menuVo.setChildren(children);
                    return menuVo;
                }).toList();
        return menuVos;
    }

    @Override
    public boolean add(MenuVo menu) {
        //校验菜单名称是否唯一
        checkMenuNameUnique(menu.getMenuName(),null);
        //校验外链是否是http(s)开头
        //如果不是外链，路由地址和名称应唯一
        checkUrl(menu.getIsFrame(),menu.getPath(),null);
        Menu menu1 = new Menu();
        BeanUtils.copyProperties(menu,menu1);
        menu1.setCreateBy(UserContext.get().getLoginName());
        menu1.setCreateTime(new Date());
        this.save(menu1);
        return true;
    }

    private void checkUrl(String isFrame, String url,Long selfId) {
        if("0".equals(isFrame)){
            //判断外链格式
            if(url.startsWith("http")){
                throw new MenuExecption("外链格式错误，应以http(s)开头");
            }
        }else{
            //判断路由地址是否唯一
            QueryWrapper<Menu> path = new QueryWrapper<Menu>().eq("path", url);
            if(selfId!=null){
                path.ne("menu_id",selfId);
            }
            long count = this.count(path);
            if(count > 0){
                throw new MenuExecption("路由地址已存在");
            }
        }
    }

    private void checkMenuNameUnique(String menuName,Long selfId) {
        //当selfId不为空时，排除本身再检查
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.eq("menu_name",menuName);
        if(selfId!=null){
            wrapper.ne("menu_id",selfId);
        }
        long count = this.count(wrapper);
        if (count > 0) {
            throw new MenuExecption("菜单名称已存在");
        }
    }

    @Override
    public boolean edit(MenuVo menu) {
        //校验菜单名称唯一
        checkMenuNameUnique(menu.getMenuName(),menu.getMenuId());
        //校验路由地址
        checkUrl(menu.getIsFrame(),menu.getPath(),menu.getMenuId());
        //父菜单不能是自己
        if(menu.getParentId()==menu.getMenuId()){
            throw new MenuExecption("上级菜单不能选择自己");
        }
        Menu menu1 = new Menu();
        BeanUtils.copyProperties(menu,menu1);
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
    public List<MenuTreeVo> treeSelect(MenuQueryVo menu) {
        List<Menu> list =this.list();
        Set<Long> menuIds = list.stream().map(Menu::getMenuId).collect(Collectors.toSet());
        List<MenuTreeVo> menuVos = list.stream()
                .filter(m->m.getParentId().equals(0L) || !menuIds.contains(m.getParentId()))
                .map(m -> {
                    MenuTreeVo menuTreeVo = new MenuTreeVo();
                    menuTreeVo.setId(m.getMenuId());
                    boolean b = m.getVisible().equals('0');
                    menuTreeVo.setDisabled(b);
                    menuTreeVo.setLabel(m.getMenuName());
                    List<MenuTreeVo> children = computeTreeChildren(m, list);
                    menuTreeVo.setChildren(children);
                    return menuTreeVo;
                }).toList();
        return menuVos;
    }

    private List<MenuTreeVo> computeTreeChildren(Menu menu, List<Menu> list) {
        //从list中找出m
        List<MenuTreeVo> treeVos = list.stream().filter(m -> m.getParentId().equals(menu.getMenuId())).map(m -> {
            MenuTreeVo menuTreeVo = new MenuTreeVo();
            menuTreeVo.setId(m.getMenuId());
            menuTreeVo.setLabel(m.getMenuName());
            boolean b = m.getVisible().equals('0');
            menuTreeVo.setDisabled(b);
            menuTreeVo.setChildren(computeTreeChildren(m, list));
            return menuTreeVo;
        }).toList();
        return treeVos;
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
    public Boolean updateSort(List<Menu> menus) {
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
            this.removeById(id);
        }
        deleteCheckResultDto.setFailList(failList);
        return deleteCheckResultDto;
    }

    @Override
    public Set<String> selectMenuPermsByRoleId(Long roleId) {
        Set<String> permsSet = new HashSet<>();
        List<String> perms = menuDao.selectMenuPermsByRoleId(roleId);
        for (String perm:perms){
            if(StringUtils.isNotEmpty(perm)){
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    @Override
    public List<MenuVo> selectMenuTreeByUserId(Long userId) {
        List<Menu> menus = menuDao.selectMenusByUserId(userId);
        //获取所有的菜单id
        Set<Long> menuIdSet = menus.stream().map(Menu::getMenuId).collect(Collectors.toSet());
        //过滤并转换
        List<MenuVo> menuVos = menus.stream()
                //过滤条件，是一级目录，或者父菜单不在权限列表里。避免重复
                .filter(m->m.getParentId() == 0 || !menuIdSet.contains(m.getParentId()))
                .map(menu -> {
                    MenuVo menuVo = new MenuVo();
                    BeanUtils.copyProperties(menu, menuVo);
                    menuVo.setChildren(computeChildren(menu, menus));
                    return menuVo;
                }).toList();
        return menuVos;
    }

    @Override
    public List<RouterVo> buildMenus(List<MenuVo> menuVoList) {
        List<RouterVo> routerVos = new ArrayList<>();
        for (MenuVo menu : menuVoList) {
            RouterVo router = new RouterVo();
            router.setHidden("1".equals(menu.getVisible()));
            router.setName(getRouteName(menu));
            router.setPath(getRouterPath(menu));
            router.setComponent(getComponent(menu));
            router.setQuery(menu.getQuery());
            router.setMeta(new MetaVo(menu.getMenuName(),menu.getIcon(),false,menu.getPath()));
            List<MenuVo> cMenu = menu.getChildren();

            //当子目录不为空，且为一级目录
            if((cMenu !=null && cMenu.size()>0) && UserConstants.TYPE_DIR.equals(menu.getMenuType())){
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildMenus(cMenu));
            }
            //当菜单是一个非外链的一级菜单
            else if(isMenuFrame(menu)){
                router.setMeta(null);
                List<RouterVo> childrenList = new ArrayList<>();
                RouterVo children = new RouterVo();
                children.setPath(menu.getPath());
                children.setComponent(menu.getComponent());
                children.setName(getRouteName(menu.getRouteName(),menu.getPath()));
                children.setMeta(new MetaVo(menu.getMenuName(),menu.getIcon(),false,menu.getPath()));
                children.setQuery(menu.getQuery());
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            //当菜单是一级菜单且是内链组件，和上个判断一样，我不理解
            else if(menu.getParentId().intValue() == 0 && isInnerLink(menu)){
                router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon()));
                router.setPath("/");
                List<RouterVo> childrenList = new ArrayList<>();
                RouterVo children = new RouterVo();
                String routerPath = innerLinkReplaceEach(menu.getPath());
                children.setPath(routerPath);
                children.setComponent(UserConstants.INNER_LINK);
                children.setName(getRouteName(menu.getRouteName(),menu.getPath()));
                children.setMeta(new MetaVo(menu.getMenuName(),menu.getIcon(),false,menu.getPath()));
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            routerVos.add(router);
        }
        return routerVos;
    }

    /**
     * 内链域名特殊字符替换
     * @param path
     * @return
     */
    //todo
    private String innerLinkReplaceEach(String path) {
        if (path == null || path.isEmpty()) {
            return path;
        }
        return path.replace("http://", "")
                .replace("https://", "")
                .replace("www.", "")
                .replace(".", "/")
                .replace(":", "/");
    }

    /**
     * 是否为内链组件
     * @param menu
     * @return
     */
    //TODO
    private boolean isInnerLink(MenuVo menu) {
        return menu.getIsFrame().equals("1")
                && (menu.getPath().startsWith("http://") || menu.getPath().startsWith("https://"));
    }

    /**
     * 是否为菜单内部跳转
     * @param menu
     * @return
     */
    private boolean isMenuFrame(MenuVo menu) {
        return menu.getParentId().intValue() == 0 && UserConstants.TYPE_MENU.equals(menu.getMenuType())
                && menu.getIsFrame().equals("1");
    }

    /**
     * 获取组件信息
     * @param menu
     * @return
     */
    //todo 不懂
    private String getComponent(MenuVo menu) {
        String component = UserConstants.LAYOUT;
        //当为非外链、一级目录且有组件信息
        if(StringUtils.isNotEmpty(menu.getComponent()) && !isMenuFrame(menu)){
            component = menu.getComponent();
        }
        else if(StringUtils.isEmpty(menu.getComponent()) && menu.getParentId().intValue() != 0 && isInnerLink(menu)){
            component = UserConstants.INNER_LINK;
        }
        else if(StringUtils.isEmpty(menu.getComponent()) && isParentView(menu)){
            component = UserConstants.PARENT_VIEW;
        }
        return component;
    }
    //TODO 不理解啊啊啊啊
    /**
     * 获取路由地址
     * @param menu
     * @return
     */
    private String getRouterPath(MenuVo menu) {
        String routerPath = menu.getPath();
        //内链打开外网方式
        if(menu.getParentId().intValue() != 0 && isInnerLink(menu)){
            routerPath = innerLinkReplaceEach(routerPath);
        }
        //非外链且是一级目录（类型为目录）
        if(menu.getParentId().intValue() == 0 && UserConstants.TYPE_DIR.equals(menu.getMenuType())
                && "1".equals(menu.getIsFrame())){
            routerPath = "/"+menu.getPath();
        }
        //非外链且是一级目录（类型为菜单）
        else if(isMenuFrame(menu)){
            routerPath = "/";
        }
        return routerPath;
    }

    /**
     * 判断目录如果不是外链且为一级目录时才返回路由名，否则为空
     * @param menu
     * @return
     */
    private String getRouteName(MenuVo menu) {
        //判断不是外链且是一级目录（类型为目录）
        if(isMenuFrame(menu)){
            return "";
        }
        return getRouteName(menu.getRouteName(),menu.getPath());
    }

    /**
     * 获取路由名称，如果路由名称不为空，则返回路由名称，否则返回路由地址，结果的第一个首字母大写
     * @param name
     * @param path
     * @return
     */
    private String getRouteName(String name, String path) {
        String routerName = StringUtils.isNotEmpty(name)?name:path;
        return org.springframework.util.StringUtils.capitalize(routerName);
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
            List<MenuVo> childrenList = menuList.stream()
                    .sorted(new Comparator<Menu>() {
                        @Override
                        public int compare(Menu m1, Menu m2) {
                            return m1.getOrderNum()-m2.getOrderNum();
                        }
                    })
                    .map(m -> {
                MenuVo menuVo = new MenuVo();
                BeanUtils.copyProperties(m,menuVo);
                List<MenuVo> children = computeChildren(m, list);
                menuVo.setChildren(children);
                return menuVo;
            }).toList();
            return childrenList;
        }
        return null;
    }

    /**
     * 是否为parent_view组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    //todoS
    public boolean isParentView(MenuVo menu)
    {
        return menu.getParentId().intValue() != 0 && UserConstants.TYPE_DIR.equals(menu.getMenuType());
    }
}
