package com.plm.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.plm.project.entity.Material;
import com.plm.project.dao.MaterialDao;
import com.plm.project.service.MaterialService;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.plm.project.vo.query.MaterialQueryVo;

import java.util.Date;
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
        Page<Material> page = new Page<>(material.getPageNum(), material.getPageSize());
        QueryWrapper<Material> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag", "0");
        if (StringUtils.isNotEmpty(material.getKeyword())){
            queryWrapper.and(q->{
               q.like("material_name",material.getKeyword())
                       .or()
                       .eq("material_no",material.getKeyword());
            });
        }
        if (material.getMaterialType()!=null){
            queryWrapper.eq("material_type",material.getMaterialType());
        }
        if (material.getStatus()!=null){
            queryWrapper.eq("status",material.getStatus());
        }
        IPage<Material> iPage = this.page(page, queryWrapper);
        return iPage;
    }

    @Override
    public boolean add(Material material) {
        material.setCreatedTime(new Date());
        return this.save(material);
    }

    @Override
    public boolean edit(Material material) {
        material.setUpdatedTime(new Date());
        return this.updateById(material);
    }

    @Override
    public boolean deleteByIds(List<Long> id) {
        return this.removeByIds(id);
    }
}
