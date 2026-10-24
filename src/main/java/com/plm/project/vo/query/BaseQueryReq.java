package com.plm.project.vo.query;

import lombok.Data;

@Data
public class BaseQueryReq {
    private Integer pageNum=1;
    private Integer pageSize=10;
    private String keyword;
}
