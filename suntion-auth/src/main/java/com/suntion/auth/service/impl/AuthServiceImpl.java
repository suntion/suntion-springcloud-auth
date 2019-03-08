package com.suntion.auth.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.suntion.auth.mapper.AuthUserMapper;
import com.suntion.auth.model.AuthUser;
import com.suntion.auth.service.AuthService;
import com.suntion.core.common.constants.BaseConstants;
import com.suntion.core.common.lang.ShiroUtil;
import com.suntion.core.exception.SuntionException;

import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    AuthUserMapper authUserMapper;

    @Override
    public AuthUser getUserByAccount(String account) {
        AuthUser authUser = new AuthUser();
        authUser.setAccount(account)
                .setDr(0);
        return authUserMapper.selectOne(authUser);
    }

    @Override
    public AuthUser accountlogin(String account, String password) {
        //通过账户获取用户
        AuthUser authUser = new AuthUser();
        authUser.setAccount(account);
        authUser = authUserMapper.selectOne(authUser);
        if (authUser == null) {
            throw  new SuntionException("账号还未注册");
        }
        //判断密码是否相等
        if (authUser != null
                && authUser.getPassword().equals(ShiroUtil.encryptPassword(password, authUser.getSalt()))) {
            return authUser;
        }
        return null;
    }

    @Override
    public Integer accountRegeister(String account, String password) {
        //通过账户获取用户
        AuthUser authUser = new AuthUser();
        authUser.setAccount(account);
        authUser.setDr(BaseConstants.DR_NO);
        authUser = authUserMapper.selectOne(authUser);
        if (authUser != null) {
            throw new SuntionException("账户已经注册");
        }

        authUser = new AuthUser();
        authUser.setDr(BaseConstants.DR_NO);
        authUser.setUpdateTime(new Date());
        authUser.setAccount(account);
        authUser.setVersion(1);
        String salt = ShiroUtil.randomSaltNumber();
        authUser.setSalt(salt);
        authUser.setPassword(ShiroUtil.encryptPassword(password,salt));
        return authUserMapper.insert(authUser);
    }
}
