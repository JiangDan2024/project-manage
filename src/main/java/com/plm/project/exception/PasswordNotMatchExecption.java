package com.plm.project.exception;

public class PasswordNotMatchExecption extends RuntimeException {
    public PasswordNotMatchExecption() {
        super("密码错误");
    }
}
