package com.suntion.core.aspect;

import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * AOP 切面获取切点参数 工具类
 *
 * @author suns suntion@yeah.net
 * @since 2018年4月10日上午11:17:00
 */
public class AspectUtils {

    public static StringBuffer getStringBuilder(JoinPoint joinPoint) {
        StringBuffer mgsBuffer = new StringBuffer();
        try {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = servletRequestAttributes.getRequest();

            //IP地址
            mgsBuffer.append("IP[" + getIPAddress(request) + "]");

            //请求方法
            String methodStr = AspectUtils.getMethodName(joinPoint);
            mgsBuffer.append(" ,Method[" + methodStr + "]");

            //请求参数
            StringBuilder param = AspectUtils.getParamStr(joinPoint);
            mgsBuffer.append(" ,Params[" + param + "]");

            return mgsBuffer;
        } catch (Exception e) {
            return mgsBuffer.append("获取Request失败");
        }
    }


    /**
     * 通过切入点 获取方法请求的参数,request是key=value形式 ,方法中的参数只有一个key值
     *
     * @param joinPoint 切入点
     * @return 参数
     */
    public static StringBuilder getParamStr(JoinPoint joinPoint) {
        String[] names = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
        Object[] objArr = joinPoint.getArgs();
        if (ArrayUtils.isEmpty(names)) {
            return new StringBuilder("");
        }
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < names.length; i++) {
            if (objArr[i] instanceof HttpServletRequest) {
                HttpServletRequest hreq = (HttpServletRequest) objArr[i];
                Map<String, String[]> params = hreq.getParameterMap();
                String queryString = "";
                for (String key : params.keySet()) {
                    String[] values = params.get(key);
                    for (int k = 0; k < values.length; k++) {
                        String value = values[k];
                        queryString += key + "=" + value + "&";
                    }
                }
                if (queryString.length() >= 1) {
                    queryString = "?" + queryString.substring(0, queryString.length() - 1);
                }
                queryString = hreq.getProtocol() + " " + hreq.getMethod() + " " + hreq.getRequestURL() + queryString;
                sb.append(names[i] + ":" + queryString + " , ");
            } else {
                sb.append(names[i] + " : " + objArr[i] + ",");
            }
        }
        if (sb.length() >= 1) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb;
    }

    public static String getMethodName(JoinPoint joinPoint) {
        return joinPoint.getTarget().getClass().getSimpleName() + "." + joinPoint.getSignature().getName() + "()";
    }

    public static String getIPAddress(HttpServletRequest request) {
        String ip = null;

        //X-Forwarded-For：Squid 服务代理
        String ipAddresses = request.getHeader("X-Forwarded-For");

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //Proxy-Client-IP：apache 服务代理
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //WL-Proxy-Client-IP：weblogic 服务代理
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //HTTP_CLIENT_IP：有些代理服务器
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //X-Real-IP：nginx服务代理
            ipAddresses = request.getHeader("X-Real-IP");
        }

        //有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
        if (ipAddresses != null && ipAddresses.length() != 0) {
            ip = ipAddresses.split(",")[0];
        }

        //还是不能获取到，最后再通过request.getRemoteAddr();获取
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
