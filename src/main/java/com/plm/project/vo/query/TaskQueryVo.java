package com.plm.project.vo.query;

import lombok.Data;

@Data
public class TaskQueryVo extends BaseQueryReq{
    /**
     * 类型：DEV开发/TEST测试/DOC文档/MEETING会议
     */
    private String taskType;
    /**
     * 优先级：1高 2中 3低
     */
    private Integer priority;
    /**
     * 状态：0待开始 1进行中 2已完成 3已取消
     */
    private Integer status;
}
