package com.plm.project.exception;

public class LoginNameExistException extends RuntimeException {
    public LoginNameExistException() {
        super("用户名已存在");
    }
}
