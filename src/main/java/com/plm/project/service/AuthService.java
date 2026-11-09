package com.plm.project.service;

import com.plm.project.entity.User;
import com.plm.project.vo.UserLoginVo;
import com.plm.project.vo.UserRegisterVo;
import com.plm.project.vo.UserVo;

public interface AuthService {
    boolean register(UserRegisterVo vo);

    UserVo login(UserLoginVo vo);
}
