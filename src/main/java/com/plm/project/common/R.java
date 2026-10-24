package com.plm.project.common;

import java.util.HashMap;

public class R<T> extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public final static String CODE_STR = "code";
    public final static String MSG_STR = "msg";
    public final static String DATA_STR = "data";

    /**
     * 空消息
     */
    public R(){
    }

    public R(Integer code){
        new R(code,null);
    }

    public R(Integer code,String msg){
         new R(code,msg,null);
    }

    public R(Integer code,Object data){
        new R(code,null,data);
    }

    public R(Integer code,String msg,T data){
        put(CODE_STR,code);
        put(MSG_STR,msg);
        put(DATA_STR,data);
    }

    public static <T> R<T>  ok(){
        return ok(HttpStatus.SUCCESS,HttpStatus.SUCCESS_MSG);
    }

    public static <T> R<T> ok(Integer code,String msg){
        return ok(code,msg,null);
    }

    public static <T> R<T> ok(Integer code,String msg,T data){
        return new R(code,msg,data);
    }

    public static <T> R<T> ok(String msg,T data){
        return ok(HttpStatus.SUCCESS,msg,data);
    }

    public static <T> R<T> ok(T data){
        return ok(HttpStatus.SUCCESS,HttpStatus.SUCCESS_MSG,data);
    }

    public static <T> R<T> error(){
        return error(HttpStatus.ERROR,HttpStatus.ERROR_MSG);
    }

    public static <T> R<T> error(T data){
        return error(HttpStatus.ERROR,HttpStatus.ERROR_MSG,data);
    }

    public static <T> R<T> error(String msg){
        return error(HttpStatus.ERROR,msg,null);
    }

    public static <T> R<T> error(Integer code,String msg){
        return error(code,msg,null);
    }

    public static <T> R<T> error(Integer code,String msg,T data){
        return new R(code,msg,data);
    }


    @Override
    public Object put(String key, Object value) {
        return super.put(key, value);
    }
}
