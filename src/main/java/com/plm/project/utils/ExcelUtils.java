package com.plm.project.utils;

import com.plm.project.common.component.CommonStringStringConverter;
import com.plm.project.common.component.UserDataListener;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.fesod.sheet.FesodSheet;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class ExcelUtils {

    /**
     * 导出表格
     * @param list 数据
     * @param fileName 文件名
     * @param clazz 类
     * @param response
     * @param <T>
     */
    public <T> void exportExcel(List<T> list, String fileName, Class<T> clazz,String templateFile, HttpServletResponse response){
        //加载模版文件
        ClassPathResource resource = new ClassPathResource(templateFile);
        try (InputStream templateInputStream = resource.getInputStream();){
            //设置返回体
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            FesodSheet.write(response.getOutputStream(),clazz)
                    .registerConverter(new CommonStringStringConverter())
                    .withTemplate(templateInputStream)
                    .sheet("Sheet1")
                    .doFill(list);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 导入表格
     * @param file
     * @param clazz
     * @return
     * @param <T>
     */
    public <T> List<T> importExcel(MultipartFile file, Class<T> clazz){
        UserDataListener<T> listener = new UserDataListener<T>();
        try {
            FesodSheet.read(file.getInputStream(),clazz,listener)
                    .registerConverter(new CommonStringStringConverter())
                    .sheet()
                    .doRead();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return listener.getList();
    }
}
