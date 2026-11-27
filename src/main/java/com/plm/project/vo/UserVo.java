package com.plm.project.vo;

import com.plm.project.entity.Dept;
import com.plm.project.entity.User;
import lombok.Data;
import lombok.ToString;
import org.apache.fesod.sheet.annotation.ExcelIgnore;

import java.util.List;

@ToString
@Data
public class UserVo extends User {
    @ExcelIgnore
    private Dept dept;
    @ExcelIgnore
    private List<Long> roleIds;
    //roleList是所有的角色列表，为了使编辑用户信息时，无需再次请求角色列表
    @ExcelIgnore
    private List<RoleVo> roleList;
}
