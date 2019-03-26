package com.suntion.core.jwt;

import org.apache.shiro.authc.AuthenticationToken;
/**
 * @author Suntion
 */
public class JwtAuthenticationToken implements AuthenticationToken {

    private String jwtToken;

    public JwtAuthenticationToken(String authToken) {
        this.jwtToken = authToken;
    }


    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    @Override
    public Object getPrincipal() {
        return jwtToken;
    }

    @Override
    public Object getCredentials() {
        return jwtToken;
    }
}
