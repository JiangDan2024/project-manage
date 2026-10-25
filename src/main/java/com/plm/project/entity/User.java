package com.plm.project.entity;

import java.util.Date;
import java.io.Serializable;

import cn.dev33.satoken.json.SaJsonType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 用户信息表(User)实体类
 *
 * @author makejava
 * @since 2026-10-23 14:36:21
 */
@Data
@TableName(value = "sys_user")
public class User implements Serializable, SaJsonType {
    private static final long serialVersionUID = -51828952244113293L;
    /**
     * 用户ID
     */
    @TableId("user_id")
    private Long userId;
    /**
     * 部门ID
     */
    private Long deptId;
    /**
     * 登录账号
     */
    private String loginName;
    /**
     * 用户昵称
     */
    private String userName;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 手机号码
     */
    private String phoneNumber;
    /**
     * 用户性别（0男 1女 2未知）
     */
    private String sex="1";
    /**
     * 头像路径
     */
    private String avatar;
    /**
     * 密码
     */
    private String password;
    /**
     * 账号状态（0正常 1停用）
     */
    private String status="0";
    /**
     * 删除标志（0代表存在 2代表删除）
     * 逻辑删除
     */
    @TableLogic(value = "0",delval = "2")
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

