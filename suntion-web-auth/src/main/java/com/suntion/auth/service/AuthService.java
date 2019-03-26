package com.suntion.auth.service;

import com.suntion.auth.model.AuthUser;
/**
 * @author suntion
 */
public interface AuthService {

    /**
     * 通过账户登录
     * @param account 账户
     * @return 用户
     */
    AuthUser getUserByAccount(String account);

    /**
     * 使用账户登录
     * @param account 账户
     * @param password 密码
     * @return
     */
    AuthUser accountlogin(String account, String password);

    /**
     * 账号注册
     * @param account 账户
     * @param password 密码
     * @return
     */
    Integer accountRegeister(String account, String password);
}
