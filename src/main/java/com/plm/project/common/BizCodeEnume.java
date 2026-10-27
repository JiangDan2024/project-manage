package com.plm.project.common;

import lombok.Getter;

@Getter
public enum BizCodeEnume {
    USER_EXIST_EXCEPTION(15001,"用户已存在"),
    PHONE_EXIST_EXCEPTION(15002,"手机号已存在"),
    NOT_REGISTER_EXCEPTION(15003,"该账号未注册"),
    PASSWORD_NOT_MATCH_EXCEPTION(15004,"密码输入错误"),
    MENU_EXCEPTION(16001,"菜单校验异常"),
    UNKNOW_EXCEPTION(10000,"系统未知异常"),
    VAILD_EXCEPTION(10001,"参数格式校验失败");

    private Integer code;
    private String message;
    BizCodeEnume(Integer code,String message){
        this.code = code;
        this.message = message;
    }

}
