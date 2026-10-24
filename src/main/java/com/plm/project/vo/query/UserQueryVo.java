package com.plm.project.vo.query;

import lombok.Data;

@Data
public class UserQueryVo extends BaseQueryReq{
    private String phoneNumber;
    private Integer status;
    private Long userId;
}
