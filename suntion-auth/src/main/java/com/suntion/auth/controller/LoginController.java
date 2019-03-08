package com.suntion.auth.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.suntion.auth.model.AuthUser;
import com.suntion.auth.service.AuthService;
import com.suntion.auth.service.MiaoshaService;
import com.suntion.core.aspect.LogOperation;
import com.suntion.core.common.constants.AuthConstants;
import com.suntion.core.common.lang.JwtTokenUtil;
import com.suntion.core.common.lang.ResponseEntity;
import com.suntion.core.shiro.JwtAuthenticationToken;

import java.util.UUID;

/**
 * @version V1.0
 * @Description: 登陆跳转
 * @author: suntion@yeah.net
 * @date: 2018年8月3日 下午2:21:581
 */
@RestController
public class LoginController {

    @Autowired
    AuthService authService;


    @Autowired StringRedisTemplate redisTemplate;

    @PostMapping("/unauth/login")
    @LogOperation("使用账号密码登陆")
    public Object unauthLogin(String username, String password) {
        Assert.notNull(username, "请输入账户");
        Assert.notNull(password, "请输入密码");
        AuthUser authUser = authService.accountlogin(username,password);
        if (authUser != null) {
            String authToken = JwtTokenUtil.createToken(username, AuthConstants.TTLMILLS,AuthConstants.SECRETKET);
            JwtAuthenticationToken statelessToken = new JwtAuthenticationToken(authToken);
            //UsernamePasswordToken authenticationToken = new UsernamePasswordToken("adamin","admin");
            //TelCheckCodeAuthenticationToken telCheckCodeAuthenticationToken = new TelCheckCodeAuthenticationToken("adamin","admin","1");

            SecurityUtils.getSubject().login(statelessToken);
            return ResponseEntity.SUCCESS().setResult(authToken);
        }
        return ResponseEntity.FAILED().setResult("账号密码错误");
    }

    @PostMapping("/unauth/register")
    @LogOperation("使用账号密码登陆")
    public Object unauthRegister(String username, String password) {
        Assert.notNull(username, "请输入账户");
        Assert.notNull(password, "请输入密码");

        Integer integer = authService.accountRegeister(username,password);
        return ResponseEntity.SUCCESS().setResult(integer);
    }


    @Autowired
    MiaoshaService miaoshaService;

    @PostMapping("/unauth/miaosha/{goodid}/{userid}")
    public Object miaosha(@PathVariable String goodid, @PathVariable String userid) {
        boolean ismiaosha = miaoshaService.miaosha(goodid, UUID.randomUUID().toString());
        if (ismiaosha) {
            return ResponseEntity.SUCCESS();
        }
        return ResponseEntity.FAILED();
    }

}