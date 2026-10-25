package com.plm.project.service.impl;

import com.plm.project.entity.Material;
import com.plm.project.dao.MaterialDao;
import com.plm.project.service.MaterialService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.plm.project.vo.query.MaterialQueryVo;
import java.util.List;
/**
 * 物料主数据表(Material)表服务实现类
 *
 * @author makejava
 * @since 2026-10-25 15:40:54
 */
@Service("materialService")
public class MaterialServiceImpl extends ServiceImpl<MaterialDao,Material> implements MaterialService {
    
    @Override
    public Material info(Long id) {

        return this.getById(id);
    }

    @Override
    public IPage<Material> queryByPage(MaterialQueryVo material) {
        return null;
    }

    @Override
    public boolean add(Material material) {
        return this.save(material);
    }

    @Override
    public boolean edit(Material material) {
        return this.updateById(material);
    }

    @Override
    public boolean deleteByIds(List<Long> id) {
        return this.removeByIds(id);
    }
}
