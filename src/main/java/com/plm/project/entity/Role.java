package com.plm.project.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.plm.project.common.annotation.ExcelDict;
import com.plm.project.common.component.CommonStringStringConverter;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.fesod.sheet.annotation.ExcelProperty;

/**
 * 角色信息表(Role)实体类
 *
 * @author makejava
 * @since 2026-10-23 14:35:20
 */
@Data
@TableName(value = "sys_role")
public class Role implements Serializable {
    private static final long serialVersionUID = -49627574701863885L;
    /**
     * 角色ID
     */
    @ExcelProperty("角色编号")
    @TableId(value = "role_id",type = IdType.AUTO)
    private Long roleId;
    /**
     * 角色名称
     */
    @ExcelProperty("角色名称")
    private String roleName;
    /**
     * 角色权限字符串
     */
    @ExcelProperty("权限字符")
    private String roleKey;
    /**
     * 显示顺序
     */
    @ExcelProperty("角色顺序")
    private Integer roleSort;
    /**
     * 角色状态（0正常 1停用）
     */
    @ExcelDict("status")
    @ExcelProperty(value = "状态",converter = CommonStringStringConverter.class)
    private String status;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag="0";
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 备注
     */
    private String remark;

}

