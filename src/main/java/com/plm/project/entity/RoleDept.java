package com.plm.project.entity;

import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 角色和部门关联表(RoleDept)实体类
 *
 * @author makejava
 * @since 2026-10-23 14:35:35
 */
@Data
@TableName(value = "sys_role_dept")
public class RoleDept implements Serializable {
    private static final long serialVersionUID = 464540679184226387L;
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 部门ID
     */
    private Long deptId;

}

