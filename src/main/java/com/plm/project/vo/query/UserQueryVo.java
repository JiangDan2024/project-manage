package com.plm.project.vo.query;

import lombok.Data;

import java.util.Date;

@Data
public class UserQueryVo extends BaseQueryReq{
    private String phoneNumber;
    private String status;
    private Long userId;
    //开始时间
    private String beginTime;
    //结束时间
    private String endTime;
}
