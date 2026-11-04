package com.plm.project.service;

import com.plm.project.entity.ChangeLog;
import com.baomidou.mybatisplus.spring.service.IService;
import java.util.List;

/**
 * 变更历史记录表(ChangeLog)表服务接口
 *
 * @author makejava
 * @since 2026-10-25 15:40:28
 */
public interface ChangeLogService extends IService<ChangeLog> {

    boolean batchAdd(List<ChangeLog> changeLogs);

    List<ChangeLog> getList(ChangeLog changeLog);

    List<ChangeLog> getList(List<Long> ids,String entityName);

    void deleteBatch(List<Long> ids, String code);

    void delete(Long id, String code);
}
