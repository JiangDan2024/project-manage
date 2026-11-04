package com.plm.project.common.annotation;

import com.plm.project.common.AuditEntityType;

import java.lang.annotation.*;

@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Change {
    /**
     * 快照类，可编辑字段的一个类，避免透露敏感信息
     */
    public Class oldEntity() default Object.class;
    /**
     * 查询类，前端传来的修改数据的类型
     */
    public Class newEntity() default Object.class;

    AuditEntityType entityName() default AuditEntityType.DEFAULT;


}
