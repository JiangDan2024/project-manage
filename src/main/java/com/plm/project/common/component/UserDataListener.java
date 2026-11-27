package com.plm.project.common.component;

import lombok.extern.slf4j.Slf4j;
import org.apache.fesod.sheet.context.AnalysisContext;
import org.apache.fesod.sheet.event.AnalysisEventListener;
import org.apache.fesod.sheet.exception.ExcelDataConvertException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UserDataListener<T> extends AnalysisEventListener<T> {
    private List<T> list = new ArrayList<>();

    @Override
    public void onException(Exception exception,AnalysisContext context) {
        log.error("失败：{}",exception.getMessage());
        if(exception instanceof ExcelDataConvertException){
            ExcelDataConvertException ex = (ExcelDataConvertException)exception;
            log.error("第{}行，第{}列异常，数据{}",ex.getRowIndex(),ex.getColumnIndex(),ex.getCellData());
        }
    }

    @Override
    public void invoke(T vo, AnalysisContext analysisContext) {
        list.add(vo);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    public List<T> getList(){
        return list;
    }
}
