package com.plm.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.plm.project.entity.User;
import com.plm.project.exception.PhoneExistException;
import com.plm.project.exception.UserNameExistException;
import com.plm.project.exception.UserNotRegisterExpection;
import com.plm.project.service.AuthService;
import com.plm.project.service.UserService;
import com.plm.project.vo.UserLoginVo;
import com.plm.project.vo.UserRegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserService userService;

    @Override
    public boolean register(UserRegisterVo vo) {
        User user = new User();
        //校验用户名是否存在，不存在则构建用户对象，存在则抛出异常
        checkUsernameUnique(vo.getUserName());
        checkPhoneUnique(vo.getPhone());
        user.setUserName(vo.getUserName());
        user.setLoginName(vo.getUserName());
        user.setPhoneNumber(vo.getPhone());
        user.setUpdateTime(new Date());
        user.setCreateTime(new Date());
        //TODO同步更新人和创建人
        //密码加密
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(vo.getPassword()));
        //保存
        userService.save(user);
        return true;
    }

    @Override
    public User login(UserLoginVo vo) {
        //根据用户名查询用户信息
        User user = getByLoginacct(vo.getLoginacct());
        if (user == null) {
            throw new UserNotRegisterExpection();
        }
        //用户存在，则校验密码；用户不存在则返回异常
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean matches = encoder.matches(vo.getPassword(), user.getPassword());
        if (matches) {
            return user;
        }else{
            throw new UserNotRegisterExpection();
        }
    }

    private User getByLoginacct(String loginacct) {
        User one = userService.getOne(new QueryWrapper<User>().eq("login_name", loginacct)
                .or().eq("user_name", loginacct)
                .or().eq("phone_number", loginacct));
        return one;
    }

    private void checkPhoneUnique(String phone) {
        long count = userService.count(new QueryWrapper<User>().eq("phone_number", phone));
        if (count > 0) {
            throw new PhoneExistException();
        }
    }

    private void checkUsernameUnique(String userName) {
        long count = userService.count(new QueryWrapper<User>().eq("login_name", userName));
        if (count > 0) {
            throw new UserNameExistException();
        }
    }
}
