package com.suntion.auth.service.realm;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author Suntion
 */
public class TelCheckCodeAuthenticationToken implements AuthenticationToken {

    private String tel;
    private String checkCode;
    private String type;

    @Override
    public Object getPrincipal() {
        return tel;
    }

    @Override
    public Object getCredentials() {
        return checkCode;
    }

    public TelCheckCodeAuthenticationToken(String tel, String checkCode, String type) {
        this.tel = tel;
        this.checkCode = checkCode;
        this.type = type;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
