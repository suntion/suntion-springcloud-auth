package com.suntion.core.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import com.suntion.core.common.constants.AuthConstants;
import com.suntion.core.common.constants.HttpConstants;
import com.suntion.core.common.lang.JwtTokenUtil;
import com.suntion.core.common.lang.ResponseEntity;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAccessControlFilterFilter extends AccessControlFilter {

    /**
     * shiro权限拦截核心方法 返回true允许访问resource
     * <p>
     * 先执行：isAccessAllowed 再执行onAccessDenied
     * <p>
     * 如果返回true的话，就直接返回交给下一个filter进行处理。
     * <p>
     * 如果返回false的话，回往下执行onAccessDenied
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        //如果是opetios请求 后续不处理
        if (httpRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpResponse.setHeader("Access-control-Allow-Origin", "*");
            httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            httpResponse.setHeader("Access-Control-Allow-Headers", httpRequest.getHeader("Access-Control-Request-Headers"));
            httpResponse.setStatus(HttpStatus.OK.value());
            return Boolean.TRUE;
        }
        return isLoginRequest(request, response);
    }

    /**
     * onAccessDenied：表示当访问拒绝时是否已经处理了；如果返回true表示需要继续处理；
     * 如果返回false表示该拦截器实例已经处理了，将直接返回即可。
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        //获取token
        String authToken = httpRequest.getHeader(AuthConstants.DEFAULT_TOKEN_NAME);
        if (StringUtils.isBlank(authToken)) {
            authToken = httpRequest.getParameter(AuthConstants.DEFAULT_TOKEN_NAME);
        }
        if (StringUtils.isBlank(authToken)) {
            this.onLoginFail(response, HttpConstants.NO_AUTH, "No Authorization header or paramter.");
            return Boolean.FALSE;
        }
        if (authToken.startsWith("Bearer")) {
            authToken = authToken.replaceAll("Bearer","").trim();
        }
        //验证token
        try {
            return JwtTokenUtil.validateToken(authToken, AuthConstants.SECRETKET);
        } catch (Exception e) {
            this.onLoginFail(response, HttpConstants.NO_AUTH, e.getMessage());
            return Boolean.FALSE;
        }
    }

    //登录失败时默认返回401状态码
    private void onLoginFail(ServletResponse response, String code, String msg) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setCharacterEncoding("utf-8");

        ServletOutputStream output = response.getOutputStream();
        output.write(ResponseEntity.FAILED().setCode(code).setMessage(msg).toString().getBytes());
        output.flush();
        output.close();
    }
}