package com.plm.project.service.impl;

import com.plm.project.entity.Menu;
import com.plm.project.dao.MenuDao;
import com.plm.project.service.MenuService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.plm.project.vo.MenuQueryVo;
import java.util.List;
/**
 * 菜单表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2026-10-23 15:21:17
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuDao,Menu> implements MenuService {
    
    @Override
    public Menu info(Long id) {

        return this.getById(id);
    }

    @Override
    public IPage<Menu> queryByPage(MenuQueryVo menu) {
        return null;
    }

    @Override
    public boolean add(Menu menu) {
        return this.save(menu);
    }

    @Override
    public boolean edit(Menu menu) {
        return this.updateById(menu);
    }

    @Override
    public boolean deleteByIds(List<Long> id) {
        return this.removeByIds(id);
    }
}
