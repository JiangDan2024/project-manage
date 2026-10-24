package com.plm.project.entity;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 用户表格列个性化配置(TableConfig)实体类
 *
 * @author makejava
 * @since 2026-10-23 14:36:06
 */
@Data
@TableName(value = "sys_table_config")
public class TableConfig implements Serializable {
    private static final long serialVersionUID = 659431175976691475L;

    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 表格/页面标识，如 order_list
     */
    private String tableCode;
    /**
     * 列字段，稳定标识
     */
    private String columnField;
    /**
     * 列名，仅展示/辅助
     */
    private String columnName;
    /**
     * 列排序
     */
    private Integer columnSort;
    /**
     * 是否显示：1显示 0隐藏
     */
    private Integer columnShowFlag;
    /**
     * 列宽，单位px
     */
    private Integer columnWidth;

    private Date createTime;

    private Date updateTime;

}

