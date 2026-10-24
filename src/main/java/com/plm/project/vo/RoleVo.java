package com.plm.project.vo;

import com.plm.project.entity.Role;
import lombok.Data;

@Data
public class RoleVo extends Role {
    //标识当前用户是否具备该权限
    private Boolean flag = false;
}
