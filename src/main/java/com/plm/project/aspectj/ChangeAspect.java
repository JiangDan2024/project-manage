package com.plm.project.aspectj;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.plm.project.common.BizPage;
import com.plm.project.common.R;
import com.plm.project.common.annotation.BizId;
import com.plm.project.common.annotation.Change;
import com.plm.project.common.annotation.DeleteChange;
import com.plm.project.common.annotation.QueryChange;
import com.plm.project.common.component.EntityLoader;
import com.plm.project.entity.ChangeLog;
import com.plm.project.service.ChangeLogService;
import com.plm.project.service.EntiyService;
import com.plm.project.utils.DiffUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@Aspect
public class ChangeAspect {

    @Autowired
    EntityLoader entityLoader;

    @Autowired
    ChangeLogService changeLogService;

    //把查到的实体id存储起来，方便取用
    public static ThreadLocal<Serializable> threadLocal = new ThreadLocal<>();

    /**
     * 业务对象变更时，记录变更前后的值，批量新增到变更记录表中
     * @param pjp
     * @param change
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Around(value = "@annotation(change)")
    public Object batchAddChangeLogs(ProceedingJoinPoint pjp,Change change) throws InvocationTargetException, IllegalAccessException {
        /**
         * 查询数据库，得到修改之前的值
         * 问题来了，怎么查？需要调业务的查询方法才行，但是这是处理所有业务的，要么就根据change的参数判断调用哪个service--笨方法；
         * 或者diff工具是怎么用的
         */

        //1.获取新值
        Object newEntity = pjp.getArgs()[0];
        //2.方法执行前得到id查询原本值
        Object oldEntity = getOld(change,newEntity);

        Object proceed=null;
        try {
            //3.执行方法
            proceed = pjp.proceed();
            //4.成功后，比较不同
            List<ChangeLog> changeLogs = DiffUtils.compare(oldEntity, newEntity, change.oldEntity(), change.newEntity(),change.entityName());
            //5.保存变更记录
            changeLogService.batchAdd(changeLogs);
        } catch (Throwable e) {
            log.error(e.getMessage(),e);
            throw new RuntimeException(e);
        }finally {
            return proceed;
        }

    }

    /**
     * 分页查询业务表后，根据业务id和entity_name查出对应变更记录，一起返回前端
     * @param pjp
     * @param queryChange
     * @return
     */
    @Around(value = "@annotation(queryChange)")
    public Object queryChange(ProceedingJoinPoint pjp, QueryChange queryChange){
        //目标方法执行结束后，处理分页对象，转换为BizPage，查询对应的变更记录并返回
        try {
            R proceed = (R)pjp.proceed();
            if (proceed.getData() instanceof IPage) {
                IPage<?> page = (IPage<?>) proceed.getData();
                //获取id的list，使用getEntityId
                List ids = new ArrayList<>();
                Class<?> entityClass = queryChange.entityClass();
                if (page.getRecords() != null && !page.getRecords().isEmpty()) {
                    ids = page.getRecords().stream().map(item->{
                        try {
                            Serializable entityId = getEntityId(item, entityClass);
                            if(entityId != null){
                                return Long.valueOf(entityId.toString());
                            }
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                        return null;
                    }).toList();
                }
                //查询id对应的changelogs
                List<ChangeLog> changeLogs = changeLogService.getList(ids,queryChange.entityName().getCode());
                //放入Map<id,changelogs>，返回BizPage
                Map<Long,List<ChangeLog>> changeLogsMap = new HashMap<>();
                for (ChangeLog changeLog : changeLogs) {
                    List<ChangeLog> logs = changeLogsMap.get(changeLog.getEntityId());
                    if (logs == null||logs.isEmpty()) {
                        logs = new ArrayList<>();
                    }
                    logs.add(changeLog);
                    changeLogsMap.put(changeLog.getEntityId(),logs);
                }
                BizPage bizPage = new BizPage(page, changeLogsMap);
                return R.ok(bizPage);
            }
            return proceed;
        }catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 业务记录删除成功后，删除对应的变更记录
     * @param joinPoint
     * @param deleteChange
     */
    @AfterReturning(value = "@annotation(deleteChange)")
    public void deleteChange(JoinPoint joinPoint, DeleteChange deleteChange) {
        if (deleteChange.batchDelete()){
            List<Long> ids = (List<Long>) joinPoint.getArgs()[0];
            changeLogService.deleteBatch(ids,deleteChange.entityName().getCode());
        }else{
            Long id = (Long) joinPoint.getArgs()[0];
            changeLogService.delete(id,deleteChange.entityName().getCode());
        }
    }

    /**
     * 查询原对象
     * @param change
     * @param newEntity
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private Object getOld(Change change,Object newEntity) throws IllegalAccessException, InvocationTargetException {
        Class entity = change.oldEntity();
        EntiyService entiyService = entityLoader.getEntiyService(entity);
        Class queryEntity = change.newEntity();
        Serializable id = getEntityId(newEntity,queryEntity);
        threadLocal.set(id);
        Object oldEntity = entiyService.getSnapshotById(id);
        return oldEntity;
    }

    /**
     * 查找id
     * @param newEntity
     * @param queryEntity
     * @return
     */
    private Serializable getEntityId(Object newEntity, Class queryEntity) throws IllegalAccessException {
        Field idField = getIdField(queryEntity);
        if (idField == null) {
            return null;
        }
        //获取id值
        idField.setAccessible(true);
        Object id = idField.get(newEntity);
        if (id != null) {
            return (Serializable)id;
        }
        return null;
    }

    /**
     * 获取标识了@BizId的字段，利用spring的反射机制
     * @param clazz
     * @return
     */
    public Field getIdField(Class<?> clazz){
        //看不懂
        for (Class<?> c = clazz;c != Object.class && c != null;c = c.getSuperclass()) {
            for (Field field : c.getDeclaredFields()) {
                if (field.isAnnotationPresent(BizId.class)){
                    return field;
                }
            }
        }
        return null;
    }
}
