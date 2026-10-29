package com.plm.project.aspectj;

import com.plm.project.common.annotation.BizId;
import com.plm.project.common.annotation.Change;
import com.plm.project.common.component.EntityLoader;
import com.plm.project.service.EntiyService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

@Component
@Aspect
public class ChangeAspect {

    @Autowired
    EntityLoader entityLoader;

    //TODO 删除方法也应该加aop？删除某个id后，也要删除对应的变更记录
    @Around(value = "@annotation(change)")
    public Object doAround(ProceedingJoinPoint pjp,Change change) throws InvocationTargetException, IllegalAccessException {
        //查询数据库，得到修改之前的值
        // 问题来了，怎么查？需要调业务的查询方法才行，但是这是处理所有业务的，要么就根据change的参数判断调用哪个service--笨方法；
        // 或者diff工具是怎么用的


        //1.获取新值
        Object newEntity = pjp.getArgs()[0];
        //2.方法执行前得到id查询原本值
        Object oldEntity = getOld(change,newEntity);

        Object proceed=null;
        try {
            //3.执行方法
            proceed = pjp.proceed();
            //4.成功后，比较不同

        } catch (Throwable e) {
            throw new RuntimeException(e);
        }finally {
            return proceed;
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
        Class entity = change.entity();
        EntiyService entiyService = entityLoader.getEntiyService(entity);
        Class queryEntity = change.queryEntity();
        Serializable id = getEntityId(newEntity,queryEntity);
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
