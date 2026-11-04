package com.plm.project.common.annotation;

import com.plm.project.common.AuditEntityType;

import java.lang.annotation.*;

@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DeleteChange {
    AuditEntityType entityName() default AuditEntityType.DEFAULT;
    boolean batchDelete() default true;
}
