package com.plm.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.plm.project.entity.ChangeLog;
import com.plm.project.dao.ChangeLogDao;
import com.plm.project.service.ChangeLogService;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.plm.project.vo.query.ChangeLogQueryVo;

import java.util.Date;
import java.util.List;
/**
 * 变更历史记录表(ChangeLog)表服务实现类
 *
 * @author makejava
 * @since 2026-10-25 15:40:28
 */
@Service("changeLogService")
public class ChangeLogServiceImpl extends ServiceImpl<ChangeLogDao,ChangeLog> implements ChangeLogService {

    @Override
    public boolean batchAdd(List<ChangeLog> changeLogs) {
        changeLogs.forEach(changeLog -> {
            changeLog.setModifyTime(new Date());
        });
        return this.saveBatch(changeLogs);
    }

    @Override
    public List<ChangeLog> getList(ChangeLog changeLog) {
        QueryWrapper<ChangeLog> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(changeLog.getEntityName())){
            queryWrapper.eq("entity_name",changeLog.getEntityName());
        }
        if(StringUtils.isNotEmpty(changeLog.getEntityId())){
            queryWrapper.eq("entity_id",changeLog.getEntityId());
        }
        return this.list(queryWrapper);
    }
}
