package com.suntion.core.quartz.utils;

/**
 * Quartz Misfire 策略
 * 
 * @author suns suntion@yeah.net
 * @since 2018年2月28日下午2:33:23
 */
public class QuartzContants {
    /**
     * <p>
     * ——以当前时间为触发频率立刻触发一次执行
     * </p>
     * <p>
     * ——然后按照Cron频率依次执行
     * </p>
     */
    public static final int WITH_HANDLING_INSTRUCTIONFIRE_ANDPROCEED = 1;

    /**
     * <p>
     * ——不触发立即执行
     * </p>
     * <p>
     * ——等待下次Cron触发频率到达时刻开始按照Cron频率依次执行
     * </p>
     * <p>
     * 注意查看quartz.properties 中 org.quartz.jobStore.misfireThreshold
     * 要是小于暂停的时间将不起作用
     * </p>
     */
    public static final int WITH_MISFIREHANDLING_INSTRUCTIONDONOTHING = 2;

    /**
     * <p>
     * ——以错过的第一个频率时间立刻开始执行
     * </p>
     * <p>
     * ——重做错过的所有频率周期后
     * </p>
     * <p>
     * ——当下一次触发频率发生时间大于当前时间后，再按照正常的Cron频率依次执行
     * </p>
     */
    public static final int WITH_MISFIREHANDLING_INSTRUCTIONIGNOREMISFIRES = -1;

    /**
     * jobdetail 中参数传递
     */
    public static final String JOBDETAIL_DATAMAP_KEY = "param";
    
    public static final String JOB_STATUS_NONE = "0"; 
    public static final String JOB_STATUS_NORMAL = "1";
    public static final String JOB_STATUS_PAUSED = "2";
    public static final String JOB_STATUS_COMPLETE = "3"; 
    public static final String JOB_STATUS_ERROR = "4";
    public static final String JOB_STATUS_BLOCKED = "5";
}
