package com.plm.project.vo.excel;

import com.plm.project.common.annotation.ExcelDict;
import com.plm.project.common.component.CommonStringStringConverter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.fesod.sheet.annotation.ExcelIgnore;
import org.apache.fesod.sheet.annotation.ExcelIgnoreUnannotated;
import org.apache.fesod.sheet.annotation.ExcelProperty;

import java.util.Date;

@Data
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class UserExcelVo {
    /**
     * 用户ID
     */
    @ExcelProperty("用户编号")
    private Long userId;
    /**
     * 部门
     */
    @ExcelProperty("部门")
    private String deptName;
    /**
     * 登录账号
     */
    @ExcelProperty("用户名")
    @NotNull(message = "用户名不能为空")
    private String loginName;
    /**
     * 用户昵称
     */
    @ExcelProperty("用户昵称")
    private String userName;
    /**
     * 用户邮箱
     */
    @ExcelProperty("用户邮箱")
    private String email;
    /**
     * 手机号码
     */
    @ExcelProperty("手机号码")
    @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号格式有误")
    private String phoneNumber;
    /**
     * 用户性别（0男 1女 2未知）
     */
    @ExcelDict("sex")
    @ExcelProperty(value = "用户性别",converter = CommonStringStringConverter.class)
    private String sex="1";
    /**
     * 密码
     */
    @ExcelIgnore
    private String password;
    /**
     * 账号状态（0正常 1停用）
     */
    @ExcelDict("status")
    @ExcelProperty(value = "账号状态",converter = CommonStringStringConverter.class)
    private String status="0";
    /**
     * 创建者
     */
    @ExcelProperty("创建者")
    private String createBy;
    /**
     * 创建时间
     */
    @ExcelProperty("创建时间")
    private Date createTime;
    /**
     * 更新者
     */
    @ExcelProperty("更新者")
    private String updateBy;
    /**
     * 更新时间
     */
    @ExcelProperty("更新时间")
    private Date updateTime;
    /**
     * 备注
     */
    @ExcelProperty("备注")
    private String remark;
}
