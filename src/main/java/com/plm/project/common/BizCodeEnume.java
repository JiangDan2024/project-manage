package com.plm.project.common;

import lombok.Getter;

@Getter
public enum BizCodeEnume {
    USER_EXIST_EXCEPTION(15001,"用户已存在");

    private Integer code;
    private String message;
    BizCodeEnume(Integer code,String message){
        this.code = code;
        this.message = message;
    }

}
