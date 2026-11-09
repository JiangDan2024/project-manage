package com.plm.project.common;

import com.plm.project.vo.UserVo;

public class UserContext {
    //封装threadlocal工具类,进行统一的get、set、clear方法
    private static final ThreadLocal<UserVo> USER_HOLDER = new ThreadLocal<>();

    public static void set(UserVo user) {
        USER_HOLDER.set(user);
    }

    public static UserVo get() {
        return USER_HOLDER.get();
    }

    public static void clear(){
        USER_HOLDER.remove();
    }
}
