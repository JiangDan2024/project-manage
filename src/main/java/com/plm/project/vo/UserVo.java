package com.plm.project.vo;

import com.plm.project.entity.Dept;
import com.plm.project.entity.Role;
import com.plm.project.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class UserVo extends User {
    private Dept dept;
    private List<Long> roleIds;
    private List<RoleVo> roleList;
}
