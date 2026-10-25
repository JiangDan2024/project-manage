package com.plm.project.entity;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
/**
 * 物料主数据表(Material)实体类
 *
 * @author makejava
 * @since 2026-10-25 17:50:42
 */
@Data
@TableName(value = "biz_material")
public class Material implements Serializable {
    private static final long serialVersionUID = 133911073929980279L;
     
    @TableId("id")      
    private Long id;
    /**
     * 物料编码
     */     
        
    private String materialNo;
    /**
     * 物料名称
     */     
        
    private String materialName;
    /**
     * 物料分类id，关联分类树
     */     
        
    private Long categoryId;
    /**
     * 类型：1原材料 2半成品 3成品 4辅料
     */     
        
    private Integer materialType;
    /**
     * 规格型号
     */     
        
    private String spec;
    /**
     * 单位：PCS/KG/M/M2
     */     
        
    private String unit;
    /**
     * 状态：1启用 2停用 3待审核 4已作废
     */     
        
    private Integer status;
    /**
     * 标准单价
     */     
        
    private Double standardPrice;
    /**
     * 安全库存
     */     
        
    private Double safetyStock;
    /**
     * 保质期（天），NULL 表示无保质期
     */     
        
    private Integer shelfLife;
    /**
     * 默认供应商id
     */     
        
    private Long supplierId;
    /**
     * 版本号
     */     
        
    private String version;
    /**
     * 生效日期
     */     
        
    private Date effectiveDate;
     
        
    private Long tenantId;
     
        
    private Date createdTime;
     
        
    private Date updatedTime;

}

