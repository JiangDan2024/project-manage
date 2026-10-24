package com.plm.project.entity;

import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 用户和角色关联表(UserRole)实体类
 *
 * @author makejava
 * @since 2026-10-23 14:36:35
 */
@Data
@TableName(value = "sys_user_role")
public class UserRole implements Serializable {
    private static final long serialVersionUID = 369447265986254590L;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 角色ID
     */
    private Long roleId;

}

