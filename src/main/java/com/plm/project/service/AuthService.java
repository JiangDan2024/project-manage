package com.plm.project.service;

import com.plm.project.entity.User;
import com.plm.project.vo.UserLoginVo;
import com.plm.project.vo.UserRegisterVo;

public interface AuthService {
    boolean register(UserRegisterVo vo);

    User login(UserLoginVo vo);
}
