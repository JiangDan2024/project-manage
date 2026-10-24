package com.plm.project.entity;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 项目表(Project)实体类
 *
 * @author makejava
 * @since 2026-10-23 14:33:16
 */
@Data
@TableName(value = "biz_project")
public class Project implements Serializable {
    private static final long serialVersionUID = -31628711384701951L;

    private Long id;
    /**
     * 项目编号
     */
    private String projectNo;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 状态：1进行中 2已完成 3已暂停 4已关闭
     */
    private Integer status;
    /**
     * 优先级：1高 2中 3低
     */
    private Integer priority;
    /**
     * 项目经理用户id
     */
    private Long managerId;
    /**
     * 预算金额
     */
    private Double budget;
    /**
     * 开始日期
     */
    private Date startDate;
    /**
     * 结束日期
     */
    private Date endDate;
    /**
     * 进度百分比 0-100
     */
    private Integer progress;
    /**
     * 备注
     */
    private String remark;
    /**
     * 租户id
     */
    private Long tenantId;

    private Date createdTime;

    private Date updatedTime;

}

