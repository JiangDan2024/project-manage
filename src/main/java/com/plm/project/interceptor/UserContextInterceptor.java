package com.plm.project.interceptor;

import cn.dev33.satoken.stp.StpUtil;
import com.plm.project.common.UserContext;
import com.plm.project.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;

public class UserContextInterceptor implements HandlerInterceptor {
    //每个请求执行前初始化UserContext
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (StpUtil.isLogin()) {
            User user = StpUtil.getSession().getModel("user", User.class);
            UserContext.set(user);
        }
        return true;
    }

    //每个请求执行结束后清空threadlocal
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        UserContext.clear();
    }
}
