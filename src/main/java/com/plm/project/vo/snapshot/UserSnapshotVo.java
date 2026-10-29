package com.plm.project.vo.snapshot;

import lombok.Data;

import java.util.List;

@Data
public class UserSnapshotVo {
    /**
     * 主键
     */
    private Long userId;

    /**
     * 部门ID
     */
    private Long deptId;

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
     * 账号状态（0正常 1停用）
     */
    private String status="0";
    /**
     * 备注
     */
    private String remark;
    /**
     * 关联的角色id
     */
    private List<Long> roleIds;
}
