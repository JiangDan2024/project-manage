package com.plm.project.common;

import com.plm.project.entity.User;

public class UserContext {
    //封装threadlocal工具类,进行统一的get、set、clear方法
    private static final ThreadLocal<User> USER_HOLDER = new ThreadLocal<>();

    public static void set(User user) {
        USER_HOLDER.set(user);
    }

    public static User get() {
        return USER_HOLDER.get();
    }

    public static void clear(){
        USER_HOLDER.remove();
    }
}
