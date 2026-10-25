package com.plm.project.vo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserRegisterVo {
    //用户名
    @NotEmpty(message = "用户名不为空")
    @Length(min = 6,max = 18,message = "用户名长度为6-18")
    private String userName;
    //密码
    @NotEmpty(message = "密码不为空")
    @Length(min = 6,max = 18,message = "密码长度为6-18")
    private String password;
    //电话
    @Pattern(regexp="^[1]([3-9])[0-9]{9}$",message = "手机号格式不正确")
    private String phone;
}
