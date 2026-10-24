package com.plm.project.entity;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 任务表(Task)实体类
 *
 * @author makejava
 * @since 2026-10-23 14:34:14
 */
@Data
@TableName(value = "biz_task")
public class Task implements Serializable {
    private static final long serialVersionUID = 190092915016660682L;

    private Long id;
    /**
     * 任务编号
     */
    private String taskNo;
    /**
     * 任务标题
     */
    private String taskTitle;
    /**
     * 关联项目id
     */
    private Long projectId;
    /**
     * 负责人id
     */
    private Long assigneeId;
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
    /**
     * 截止日期
     */
    private Date dueDate;
    /**
     * 预估工时
     */
    private Double estimateHours;
    /**
     * 实际工时
     */
    private Double actualHours;
    /**
     * 标签，逗号分隔
     */
    private String tags;

    private Long tenantId;

    private Date createdTime;

    private Date updatedTime;

}

