package com.plm.project.common.annotation;

import java.lang.annotation.*;

@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Change {
    /**
     * 快照类
     */
    public Class entity() default Object.class;
    /**
     * 查询类
     */
    public Class queryEntity() default Object.class;
}
