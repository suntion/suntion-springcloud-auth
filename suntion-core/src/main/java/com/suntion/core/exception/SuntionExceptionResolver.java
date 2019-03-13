package com.suntion.core.exception;

import com.suntion.core.common.constants.HttpConstants;
import com.suntion.core.common.lang.ResponseEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * HandlerExceptionResolver handler 就是controller类
 * AbstractHandlerMethodExceptionResolver handler 就是 handlerMethod
 * ExceptionHandlerExceptionResolver 处理@ExceptionHandler 和 @ControllerAdvice
 * 
 * @author long
 *
 */
@Component
public class SuntionExceptionResolver extends AbstractHandlerExceptionResolver {
    private static Logger LOGGER = LoggerFactory.getLogger(SuntionExceptionResolver.class);

    private static List<Class<?>> exceptionList = new ArrayList<>();

    static {
        exceptionList.add(SuntionException.class);
        exceptionList.add(AuthenticationException.class);
    }

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            String code = HttpConstants.CODE_FAILED;
            String message = ex.getMessage();
            if (StringUtils.isEmpty(message) && ex.getCause() != null) {
                message = ex.getCause().getMessage();
            }
            if (ex.getClass() != null && ex.getCause() != null) {
                for (Class<?> tClass : exceptionList) {
                    if (tClass.isAssignableFrom(ex.getClass()) || tClass.isAssignableFrom(ex.getCause().getClass())) {
                        message = ex.getCause().getMessage();
                        LOGGER.debug("ResponseBody exception info [" + message + "]: " + ex);
                    }
                }
            } else {
                LOGGER.info("ResponseBody exception info [" + message + "]: " + ex);
            }

            MappingJackson2JsonView view = new MappingJackson2JsonView();
            Map<String, Object> attributes = new HashMap<>(5);
            attributes.put(ResponseEntity.RESULT, message);
            attributes.put(ResponseEntity.CODE, code);
            view.setAttributesMap(attributes);

            ModelAndView mv = new ModelAndView();
            mv.setView(view);
            return mv;
        } catch (Exception e) {
            try {
                LOGGER.error("ExceptionResolver 解析出错", e);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
            } catch (Exception handlerException) {
                logger.error("Handling of [" + ex.getClass().getName() + "] resulted in Exception", handlerException);
            }
        }
        
        return new ModelAndView();
    }

}
