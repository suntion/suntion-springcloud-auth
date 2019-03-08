package com.suntion.core.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 操作日志注解类处理
 * @author suns suntion@yeah.net
 * @since 2018年4月10日上午11:16:22
 */
@Aspect
@Component
public class AspectLogOperation {
    /**
     * 日志
     */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 注解切入
     */
    @Pointcut("@annotation(com.suntion.core.aspect.LogOperation)")
    public void annotationAspect() {
    }

    /**
     * @param joinPoint
     *            切入点
     */
    @After(value = " annotationAspect() ")
    public void doAfterLog(JoinPoint joinPoint) {
        try {
            if (logger.isInfoEnabled()) {
                StringBuffer mgsBuffer = AspectUtils.getStringBuilder(joinPoint);
                //注解文本
                String description = getMthodDescription(joinPoint);
                mgsBuffer.append(",Description[" + description + "]");
                // 打印
                logger.info(mgsBuffer.toString());
            }
        } catch (Throwable e) {
            logger.error("OperationLogAspect解析出错!", e);
        }
    }

    /**
     * 获取注解的描述信息
     *
     * @param joinPoint 切入点
     * @return 注释信息
     */
    private static String getMthodDescription(JoinPoint joinPoint) {
        try {
            String targetName = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            Object[] arguments = joinPoint.getArgs();
            Class<?> targetClass = Class.forName(targetName);
            Method[] methods = targetClass.getMethods();
            String description = "";
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    Class<?>[] clazzs = method.getParameterTypes();
                    if (clazzs.length == arguments.length) {
                        description = method.getAnnotation(LogOperation.class).value();
                        break;
                    }
                }
            }
            return description;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
