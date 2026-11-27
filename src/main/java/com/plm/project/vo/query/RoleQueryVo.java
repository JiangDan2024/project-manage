package com.plm.project.vo.query;

import lombok.Data;

@Data
public class RoleQueryVo extends BaseQueryReq{
    private String status;
    private Long roleId;
    //权限标识
    private String roleKey;
    //创建日期范围
    private String beginTime;
    private String endTime;
}
