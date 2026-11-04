package com.plm.project.vo.query;

import lombok.Data;

@Data
public class ProjectQueryVo extends BaseQueryReq{
    /**
     * 项目编号
     */
//    private String projectNo;
    /**
     * 项目名称
     */
//    private String projectName;
    /**
     * 状态：1进行中 2已完成 3已暂停 4已关闭
     */
    private Integer status;
    /**
     * 优先级：1高 2中 3低
     */
    private Integer priority;
}
