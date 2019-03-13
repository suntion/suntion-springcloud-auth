package com.suntion.core.quartz.vo;

import java.io.Serializable;

/**
 * 定时任务JOB定义对象
 *
 * @author suns suntion@yeah.net
 * @since 2018年2月26日下午4:37:58
 */
public class JobVo implements Serializable {
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
     * 任务表达式
     */
    private String cronExpression;

    /**
     * 参数
     */
    private String jobData;

    public String getJobClass() {
        return jobClass;
    }

    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getJobData() {
        return jobData;
    }

    public void setJobData(String jobData) {
        this.jobData = jobData;
    }

}
