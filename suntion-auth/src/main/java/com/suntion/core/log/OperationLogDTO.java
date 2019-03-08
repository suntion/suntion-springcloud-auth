package com.suntion.core.log;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 操作日志传输对象
 */
public class OperationLogDTO implements Serializable{
    private static final long serialVersionUID = -4463347528461215743L;

    public static int DEFAULT_OPERATION_LOG_THREAD_POOL_COUNT = 5;
    
    /**
     * 类型 错误日志 操作日志
     */
    public String type;

    public String msg;

    public int threadCount = DEFAULT_OPERATION_LOG_THREAD_POOL_COUNT;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    /**
     * @return toString 方法
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
