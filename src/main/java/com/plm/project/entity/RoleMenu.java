package com.plm.project.entity;

import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 角色和菜单关联表(RoleMenu)实体类
 *
 * @author makejava
 * @since 2026-10-23 14:35:51
 */
@Data
@TableName(value = "sys_role_menu")
public class RoleMenu implements Serializable {
    private static final long serialVersionUID = 227911880427852787L;
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 菜单ID
     */
    private Long menuId;

}

