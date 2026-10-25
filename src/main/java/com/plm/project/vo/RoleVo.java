package com.plm.project.vo;

import com.plm.project.entity.Role;
import lombok.Data;

import java.util.List;

@Data
public class RoleVo extends Role {
    //标识当前用户是否具备该权限
    private Boolean flag = false;
    private List<Long> menuIds;
    private List<Long> deptIds;
    private List<Long> userIds;
    private Long userId;
}
