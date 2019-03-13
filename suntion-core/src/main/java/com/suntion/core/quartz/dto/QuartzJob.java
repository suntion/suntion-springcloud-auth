package com.suntion.core.quartz.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 定时任务JOB定义对象
 *
 * @author suns suntion@yeah.net
 * @since 2018年2月26日下午4:37:58
 */
@Accessors(chain = true)
@Data
public class QuartzJob implements Serializable {
    private static final long serialVersionUID = -8054692082716173379L;
    /**
     * 任务执行类
     */
    private String jobClass;

    /**
     * 任务分组
     */
    private String jobGroup;

    /**
     * 任务描述
     */
    private String jobDescription;

    /**
     * 任务状态
     * NONE:0
     * NORMAL:1
     * PAUSED:2
     * COMPLETE:3
     * ERROR:4
     * BLOCKED:5
     */
    private String jobStatus;

    /**
     * 任务表达式
     */
    private String cronExpression;

    private String createTime;

    /**
     * 参数
     */
    private String jobData;

}
