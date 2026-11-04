package com.plm.project.common.annotation;

import com.plm.project.common.AuditEntityType;

import java.lang.annotation.*;

@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QueryChange {
    AuditEntityType entityName() default AuditEntityType.DEFAULT;
    Class<?> entityClass() default Object.class;
}
