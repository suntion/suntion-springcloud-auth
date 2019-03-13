package com.suntion.core.aspect;

import com.suntion.core.exception.SuntionException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 业务service包异常处理
 * @author suns suntion@yeah.net
 * @since 2018年4月10日上午11:16:33
 */
@Aspect
@Component
public class AspectServiceException {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 表达式切入 所有service包 和 controller包
     */
    @Pointcut("execution(* *..service..*.*(..))")
    public void serviceAspect() {
    }

    /**
     * 异常通知 用于拦截service层记录异常日志
     *
     * @param joinPoint
     *            切入点
     * @param e
     *            异常
     */
    @AfterThrowing(value = " serviceAspect() ", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        try {
            if (logger.isInfoEnabled()) {
                StringBuffer mgsBuffer = AspectUtils.getStringBuilder(joinPoint);
                mgsBuffer.append(",ExceptionCode[" + e.getClass().getName() + "]");
                mgsBuffer.append(",ExceptionInfo[" + e.getMessage() + "]。");

                if (SuntionException.class.isAssignableFrom(e.getClass())) {
                    logger.info(mgsBuffer.toString());
                } else {
                    logger.error(mgsBuffer.toString(), e);
                }
            }
        } catch (Throwable ex) {
            logger.error("ServiceExceptionAspect解析出错", ex);
        }
    }
}
