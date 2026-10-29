package com.plm.project.service;

import com.plm.project.entity.ChangeLog;
import com.baomidou.mybatisplus.spring.service.IService;
import com.plm.project.vo.query.ChangeLogQueryVo;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 变更历史记录表(ChangeLog)表服务接口
 *
 * @author makejava
 * @since 2026-10-25 15:40:28
 */
public interface ChangeLogService extends IService<ChangeLog> {

    boolean batchAdd(List<ChangeLog> changeLogs);

    List<ChangeLog> getList(ChangeLog changeLog);
}
