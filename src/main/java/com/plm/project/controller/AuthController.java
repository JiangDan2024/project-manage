package com.plm.project.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.plm.project.common.BizCodeEnume;
import com.plm.project.common.R;
import com.plm.project.entity.User;
import com.plm.project.exception.PasswordNotMatchExecption;
import com.plm.project.exception.PhoneExistException;
import com.plm.project.exception.UserNameExistException;
import com.plm.project.exception.UserNotRegisterExpection;
import com.plm.project.service.AuthService;
import com.plm.project.vo.UserLoginVo;
import com.plm.project.vo.UserRegisterVo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sys/auth/")
public class AuthController {
    //登录、注册、查看用户信息

    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public R register(@Valid @RequestBody UserRegisterVo vo) {
        //校验注册信息，不为空，格式正确，校验通过执行注册方法
        try{
            return R.ok(authService.register(vo));
        }catch (PhoneExistException e){
            return R.error(BizCodeEnume.PHONE_EXIST_EXCEPTION.getCode(),BizCodeEnume.PHONE_EXIST_EXCEPTION.getMessage(),e);
        }catch (UserNameExistException e){
            return R.error(BizCodeEnume.USER_EXIST_EXCEPTION.getCode(),BizCodeEnume.USER_EXIST_EXCEPTION.getMessage(),e);
        }
    }

    @PostMapping("/login")
    public SaResult login(@RequestBody UserLoginVo vo){
        try {
            User user = authService.login(vo);
            if (user != null){
                StpUtil.login(user.getUserId());
                //登录成功后将user数据存入session
                StpUtil.getSession().set("user", user);
                return SaResult.ok("登录成功！").setData(StpUtil.getTokenInfo());
            }
            return SaResult.error("登录失败！");
        }catch (UserNotRegisterExpection e){
            return new SaResult(BizCodeEnume.NOT_REGISTER_EXCEPTION.getCode(),
                    BizCodeEnume.NOT_REGISTER_EXCEPTION.getMessage(),e);
        }catch (PasswordNotMatchExecption e){
            return new SaResult(BizCodeEnume.PASSWORD_NOT_MATCH_EXCEPTION.getCode(),
                    BizCodeEnume.PASSWORD_NOT_MATCH_EXCEPTION.getMessage(),e);
        }
    }

//    @GetMapping("isLogin")
//    public SaResult isLogin() {
//        SaSession session = StpUtil.getSession();
//        return SaResult.ok("会话id：" + StpUtil.getLoginIdAsString()).setData(session);
//    }

    @PutMapping("logout")
    public SaResult logout(){
        StpUtil.logout();
        return SaResult.ok("退出成功！");
    }

}
