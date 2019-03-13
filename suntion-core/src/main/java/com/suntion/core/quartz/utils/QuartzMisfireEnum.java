package com.suntion.core.quartz.utils;

/**
 * Quartz Misfire 策略
 * @author suns suntion@yeah.net
 * @since 2018年2月28日下午4:05:27
 */
public enum QuartzMisfireEnum {
    /**
     * ——以当前时间为触发频率立刻触发一次执行
     * ——然后按照Cron频率依次执行
     */
    withHandlingInstructionFireAndProceed,
    
    /**
     * ——不触发立即执行
     * ——等待下次Cron触发频率到达时刻开始按照Cron频率依次执行
     * 注意查看quartz.properties 中 org.quartz.jobStore.misfireThreshold 要是小于暂停的时间将不起作用
     */
    withMisfireHandlingInstructionDoNothing,
    
    /**
     * ——以错过的第一个频率时间立刻开始执行
     * ——重做错过的所有频率周期后
     * ——当下一次触发频率发生时间大于当前时间后，再按照正常的Cron频率依次执行
     */
    withMisfireHandlingInstructionIgnoreMisfires,
}
