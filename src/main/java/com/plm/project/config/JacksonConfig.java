package com.plm.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;
import tools.jackson.databind.ser.std.ToStringSerializer;

@Configuration
public class JacksonConfig {
    //注入新的jackson规则
    @Bean
    public SimpleModule jacksonObjectMapper() {
        //在原有的配置上加入Long类型转为String的配置
        System.out.println("——————————Jackson配置生效————————————");
        SimpleModule module = new SimpleModule();
        // 把 Long 类型转成 String
        module.addSerializer(Long.class, ToStringSerializer.instance);
        module.addSerializer(Long.TYPE, ToStringSerializer.instance);
        return module;
    }
}
