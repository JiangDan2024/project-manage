package com.plm.project.entity;

import java.util.Date;
import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
/**
 * 菜单权限表(Menu)实体类
 *
 * @author makejava
 * @since 2026-11-08 16:04:33
 */
@Data
@TableName(value = "sys_menu")
public class Menu implements Serializable {
    private static final long serialVersionUID = 607675434279002161L;
    /**
     * 菜单ID
     */     
    @TableId("menu_id")      
    private Long menuId;
    /**
     * 菜单名称
     */
    @NotEmpty(message = "菜单名称不为空")
    private String menuName;
    /**
     * 父菜单ID
     */
    @NotNull(message = "父类菜单不为空")
    private Long parentId;
    /**
     * 显示顺序
     */
    @NotNull(message = "显示顺序不为空")
    private Integer orderNum;
    /**
     * 路由地址
     */
    @NotEmpty(message = "路由地址不为空")
    private String path;
    /**
     * 组件路径
     */     
        
    private String component;
    /**
     * 路由参数
     */     
        
    private String query;
    /**
     * 路由名称
     */     
        
    private String routeName;
    /**
     * 是否为外链（0是 1否）
     */     
        
    private String isFrame;
    /**
     * 是否缓存（0缓存 1不缓存）
     */     
        
    private String isCache;
    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */     
        
    private String menuType;
    /**
     * 菜单状态（0显示 1隐藏）
     */     
        
    private String visible;
    /**
     * 菜单状态（0正常 1停用）
     */     
        
    private String status;
    /**
     * 权限标识
     */     
        
    private String perms;
    /**
     * 菜单图标
     */     
        
    private String icon;
    /**
     * 创建者
     */     
        
    private String createBy;
    /**
     * 创建时间
     */     
        
    private Date createTime;
    /**
     * 更新者
     */     
        
    private String updateBy;
    /**
     * 更新时间
     */     
        
    private Date updateTime;
    /**
     * 备注
     */     
        
    private String remark;

}

