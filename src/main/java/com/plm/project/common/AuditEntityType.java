package com.plm.project.common;


public enum AuditEntityType {
    USER("User","用户信息"),
    PROJECT("Project","项目信息"),
    TASK("Task","任务表"),
    MATERIAL("Material","物料表"),
    DEFAULT("Default","默认");
    private final String code;
    private final String desc;

    AuditEntityType(String user, String desc) {
        this.code = user;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
