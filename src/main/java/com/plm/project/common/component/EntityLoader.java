package com.plm.project.common.component;

import com.plm.project.service.EntiyService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

@Component
public class EntityLoader {

    private final BeanFactory beanFactory;

    public EntityLoader(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @SuppressWarnings("unchecked")
    public <T>EntiyService getEntiyService(Class<T> entityClass) {
        /**
         * 1.ResolvableType.forClassWithGenerics(...)用于避免泛型擦除，构建一个精确的可解析的类型
         * 2.beanFactory.getBeanProvider(...)在容器中找到对应的bean
         * 3..getObject()返回
         */
        EntiyService<T> service = (EntiyService<T>) beanFactory.getBeanProvider(
                ResolvableType.forClassWithGenerics(EntiyService.class, entityClass)
        ).getObject();
        return service;
    }
}
