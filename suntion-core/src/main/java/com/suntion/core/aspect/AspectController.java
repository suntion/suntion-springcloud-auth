package com.suntion.core.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * AOP 拦截请求日志 拦截所有 包名中包含 controller的方法
 * 
 * @author suns suntion@yeah.net
 * @since 2017年11月28日上午9:43:40
 */
@Aspect
@Component
public class AspectController {
    /**
     * 日志
     */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("execution(* *..controller..*Controller.*(..)) ")
    public void controllerAspect() {
    }

    /**
     * @param joinPoint
     *            切入点
     */
    @Before(value = " controllerAspect() ")
    public void doBeforeLog(JoinPoint joinPoint) {
        try {
            if (logger.isInfoEnabled()){
                StringBuffer mgsBuffer = AspectUtils.getStringBuilder(joinPoint);
                logger.info(mgsBuffer.toString());
            }
        } catch (Throwable e) {
            logger.error("ControllerAspect解析出错!", e);
        }
    }

}