package com.plm.project.utils;

import com.alibaba.druid.support.json.JSONUtils;
import com.plm.project.entity.ChangeLog;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class DiffUtils {
    //比较两个对象的变更字段

    /**
     * 比较两个对象，返回变更字段列表
     * @param newObj
     * @param oldObj
     * @return
     */
    public static List<ChangeLog> compare(Object oldObj, Object newObj,Class<?> oldClass,Class<?> newClass) throws IllegalAccessException, NoSuchFieldException {
        List<ChangeLog> changeLogs = new ArrayList<>();
        //遍历oldObj所有字段（包括父类字段）
        if (oldObj == null) return changeLogs;
        List<Field> allFields = getAllFields(oldClass);
        for (Field field : allFields) {
            //忽略static、final、合成字段
            if (Modifier.isStatic(field.getModifiers())||field.isSynthetic()) continue;
            //获取新值
            field.setAccessible(true);
            Object oldValue = field.get(oldObj);
            //去newObj找同名同类型的字段。如果存在则获取值，不存在则下一个
            Field newField = findField(newClass,field.getName());
            if(newField==null) continue;

            newField.setAccessible(true);
            Object newValue = newField.get(newObj);
            //比较新旧值是否相同，不同则加入changelogs
            if (isEqual(oldValue,newValue)){
                ChangeLog changeLog = new ChangeLog();
                changeLog.setEntityName(field.getName());
                changeLog.setNewValue(convertToString(newValue));
                changeLog.setOldValue(convertToString(oldValue));
                changeLogs.add(changeLog);
            }
        }

        return changeLogs;
    }

    /**
     * 转object为json
     * @param val
     * @return
     */
    private static String convertToString(Object val) {
        if (val == null) return null;
        if (val instanceof String || val instanceof Number || val instanceof Boolean) {
            return val.toString(); // 普通值直接输出
        }
        // 集合、复杂对象转 JSON
        return JSONUtils.toJSONString(val);
    }

    /**
     * 判断两个对象是否相等
     * @param oldValue
     * @param newValue
     * @return
     */
    private static boolean isEqual(Object oldValue, Object newValue) {
        if(oldValue==null && newValue==null) return true;
        if(oldValue==null || newValue==null) return false;
        //集合对比，直接比较内容，不考虑顺序
        if (oldValue instanceof Collection && newValue instanceof Collection) {
            Collection<?> c1 = (Collection<?>) oldValue;
            Collection<?> c2 = (Collection<?>) newValue;
            return c1.size() == c2.size() && c1.containsAll(c2);
        }
        //数字类型对比
        if (oldValue instanceof Number && newValue instanceof Number) {
            return new BigDecimal(oldValue.toString()).compareTo(new BigDecimal(newValue.toString())) == 0;
        }
        //默认的比较
        return oldValue.equals(newValue);
    }


    /**
     * 获取某类中的同名字段
     * @param oldClass
     * @param name
     * @return
     */
    private static Field findField(Class<?> oldClass, String name) throws NoSuchFieldException {
        for (Class<?> clazz = oldClass; clazz != Object.class&&clazz!=null; clazz = clazz.getSuperclass()) {
            return clazz.getDeclaredField(name);
        }
        return null;
    }

    /**
     * 获取类中所有的字段（包括父类字段）
     * @param clazz
     * @return
     */
    private static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        //向上遍历，除Object之外的所有父类的属性都会得到
        for (Class<?> c = clazz; c!=null&&c!= Object.class; c=c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        return fields;
    }
}
