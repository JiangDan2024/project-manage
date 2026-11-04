package com.plm.project.vo.query;

import lombok.Data;

@Data
public class MaterialQueryVo extends BaseQueryReq{
    /**
     * 状态：1启用 2停用 3待审核 4已作废
     */

    private Integer status;
    /**
     * 类型：1原材料 2半成品 3成品 4辅料
     */

    private Integer materialType;
}
