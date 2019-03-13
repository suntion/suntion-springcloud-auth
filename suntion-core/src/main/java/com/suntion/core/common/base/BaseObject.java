package com.suntion.core.common.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * 
 * BaseEntity 实体父类  用于辅助Bean实体
 * @author suns suntion@yeah.net
 * @since 2017年2月21日上午10:04:59
 */
@JsonInclude(Include.NON_EMPTY)
public class BaseObject extends Object implements Serializable{
    private static final long serialVersionUID = -1421730853738021591L;

    /**
     * @return toString 方法 
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
     * @param obj 参数
     * @return 是否相等 
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj, new String[0]);
    }

    /**
     * @return 类的hashCode值 
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, new String[0]);
    }

    /**
     * @param orig 需要拷贝的值
     */
    public void copy(Object orig) {
        BeanUtils.copyProperties(orig, this);
    }
}
