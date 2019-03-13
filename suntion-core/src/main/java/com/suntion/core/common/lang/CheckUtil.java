package com.suntion.core.common.lang;

/**
 * 验证类
 * @author suns suntion@yeah.net
 * @since 2017年11月24日上午9:54:18
 */
public class CheckUtil {

    public static boolean paramNotNullException(Object obj) {
        if (obj == null) {
            return true;
        } else if ("".equals(obj.toString().trim())) {
            return true;
        } else {
            throw new IllegalArgumentException("param is not ");
        }
    }

    
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else if ("".equals(obj.toString().trim())) {
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean isNotEmpty(Object obj) {
        if (obj == null) {
            return false;
        } else if ("".equals(obj.toString().trim())) {
            return false;
        } else {
            return true;
        }
    }
    
}
