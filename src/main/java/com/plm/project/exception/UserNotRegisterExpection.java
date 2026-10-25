package com.plm.project.exception;

public class UserNotRegisterExpection extends RuntimeException {
    public UserNotRegisterExpection() {
        super("用户未注册");
    }
}
