package com.plm.project.common.component;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.plm.project.common.annotation.ExcelDict;
import org.apache.fesod.sheet.converters.Converter;
import org.apache.fesod.sheet.converters.WriteConverterContext;
import org.apache.fesod.sheet.enums.CellDataTypeEnum;
import org.apache.fesod.sheet.metadata.GlobalConfiguration;
import org.apache.fesod.sheet.metadata.data.ReadCellData;
import org.apache.fesod.sheet.metadata.data.WriteCellData;
import org.apache.fesod.sheet.metadata.property.ExcelContentProperty;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class CommonStringStringConverter implements Converter<String> {
    private static final Map<String,Map<String,Object>> DICT_EXCEL=new HashMap<String,Map<String,Object>>();
    static {
        Map<String,Object> sexMap = new HashMap<>();
        //0男 1女 2未知
        sexMap.put("0","男");
        sexMap.put("1","女");
        sexMap.put("2","未知");
        DICT_EXCEL.put("sex",sexMap);

        Map<String,Object> statusMap = new HashMap<>();
        statusMap.put("0","正常");
        statusMap.put("1","停用");
        DICT_EXCEL.put("status",statusMap);
    }

    @Override
    public Class<?> supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public String convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        //判断是否有值，无值返回空
        String stringValue = cellData.getStringValue();
        if(StringUtils.isEmpty(stringValue)) return "";
        //有值才获取ExcelDict注解，并获取注解的value
        if(contentProperty==null) return stringValue;
        Field field = contentProperty.getField();
        ExcelDict annotation = field.getAnnotation(ExcelDict.class);
        //获取不到注解也返回空
        if(annotation==null){return stringValue;}
        //根据value找map，然后一一映射
        String value = annotation.value();
        if(value!=null){
            Map<String, Object> map = DICT_EXCEL.get(value);
            //根据value获取key
            for(Map.Entry<String,Object> entry:map.entrySet()){
                if(entry.getValue().equals(stringValue)){
                    return entry.getKey().toString();
                }
            }
        }
        return "";
    }

    @Override
    public WriteCellData<?> convertToExcelData(String value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        //判断值是否为空
        if(StringUtils.isEmpty(value)){return new WriteCellData<>("");}
        //不为空时利用反射找到ExcelDict注解
        Field field = contentProperty.getField();
        //注解不存在则返回空
        ExcelDict annotation = field.getAnnotation(ExcelDict.class);
        if (annotation==null){return new WriteCellData<>(value);}
        //注解存在则得到value，在map中找对应的值
        String dictValue = annotation.value();
        if (dictValue!=null){
            Map<String, Object> map = DICT_EXCEL.get(dictValue);
            Object o = map.get(value);
            return new WriteCellData<>(o.toString());
        }
        return new WriteCellData<>("");
    }
}
