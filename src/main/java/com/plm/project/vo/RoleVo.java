package com.plm.project.vo;

import cn.dev33.satoken.json.SaJsonType;
import com.plm.project.entity.Role;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class RoleVo extends Role implements SaJsonType {
    //标识当前用户是否具备该权限
    private Boolean flag = false;
    private List<Long> menuIds;
    private List<Long> deptIds;
    private List<Long> userIds;
    private Long userId;
    private Set<String> permissions;
}
