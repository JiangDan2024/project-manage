package com.plm.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class DeleteCheckResultDto {
    //是否成功的标志
    private boolean success=false;
    //失败列表
    private List<FailDetail> failList;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FailDetail{
        private Long id;
        private String name;
        private String msg;
    }
}
