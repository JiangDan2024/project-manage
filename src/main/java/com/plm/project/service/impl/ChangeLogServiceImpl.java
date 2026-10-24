package com.plm.project.service.impl;

import com.plm.project.entity.ChangeLog;
import com.plm.project.dao.ChangeLogDao;
import com.plm.project.service.ChangeLogService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.plm.project.vo.ChangeLogQueryVo;
import java.util.List;
/**
 * 变更历史记录表(ChangeLog)表服务实现类
 *
 * @author makejava
 * @since 2026-10-23 15:21:01
 */
@Service("changeLogService")
public class ChangeLogServiceImpl extends ServiceImpl<ChangeLogDao,ChangeLog> implements ChangeLogService {
    
    @Override
    public ChangeLog info(Long id) {

        return this.getById(id);
    }

    @Override
    public IPage<ChangeLog> queryByPage(ChangeLogQueryVo changeLog) {
        return null;
    }

    @Override
    public boolean add(ChangeLog changeLog) {
        return this.save(changeLog);
    }

    @Override
    public boolean edit(ChangeLog changeLog) {
        return this.updateById(changeLog);
    }

    @Override
    public boolean deleteByIds(List<Long> id) {
        return this.removeByIds(id);
    }
}
