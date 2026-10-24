package com.plm.project.entity;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 变更历史记录表(ChangeLog)实体类
 *
 * @author makejava
 * @since 2026-10-23 14:34:33
 */
@Data
@TableName(value = "sys_change_log")
public class ChangeLog implements Serializable {
    private static final long serialVersionUID = 704399059172015066L;
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 实体类名
     */
    private String entityName;
    /**
     * 实体对象的Id
     */
    private String entityId;
    /**
     * 实体的属性名
     */
    private String property;
    /**
     * 原属性值
     */
    private String oldValue;
    /**
     * 新属性值
     */
    private String newValue;
    /**
     * 修改人
     */
    private String operator;
    /**
     * 来源(如: MANUAL手动修改, EXCEL_IMPORT导入)
     */
    private String source;
    /**
     * 描述/备注
     */
    private String remark;
    /**
     * 修改时间
     */
    private Date modifyTime;

}

