package com.suntion.core.quartz.utils;

import com.suntion.core.exception.SuntionException;
import com.suntion.core.quartz.dto.QuartzJob;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 定时任务操作工具类
 * 
 * @author suns suntion@yeah.net
 * @since 2018年2月27日上午11:20:55
 */
public class QuartzUtils {

    /** 日志对象 */
    private static final Logger logger = LoggerFactory.getLogger(QuartzUtils.class);

    /**
     * 获取触发器key
     * 
     * @param className
     * @param jobGroup
     * @return
     */
    public static TriggerKey getTriggerKey(String className, String jobGroup) {
        return TriggerKey.triggerKey(className, jobGroup);
    }


    /**
     * 获取jobKey
     *
     * @param className
     *            the job name
     * @param jobGroup
     *            the job group
     * @return the job key
     */
    public static JobKey getJobKey(String className, String jobGroup) {
        return JobKey.jobKey(className, jobGroup);
    }

    /**
     * 验证是否存在
     * 
     * @param className
     * @param jobGroup
     * @throws SchedulerException
     *             2016年10月8日下午5:30:43
     */
    private static boolean checkExists(Scheduler scheduler, String className, String jobGroup) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(className, jobGroup);
        return scheduler.checkExists(triggerKey);
    }

    /**
     * 创建定时任务
     * @param scheduler
     * @param scheduleMisfireEnum
     * @param className
     * @param jobGroup
     * @param cronExpression
     * @param jobDescription
     * @param jobData
     * @throws SchedulerException
     */
    @SuppressWarnings("unchecked")
    public static void createScheduleJob(Scheduler scheduler, QuartzMisfireEnum scheduleMisfireEnum, String className, String jobGroup, String cronExpression, String jobDescription, String jobData) throws SchedulerException {
        try {
            if (checkExists(scheduler, className, jobGroup)) {
                throw new SchedulerException(String.format("Job已经存在, className:{%s},jobGroup:{%s}", className, jobGroup));
            }
            Class<? extends Job> jobClass = (Class<? extends Job>) Class.forName(className);
            JobDetail jobDetail = JobBuilder.newJob(jobClass)
            		.requestRecovery(Boolean.TRUE)
            		.withIdentity(className, jobGroup)
            		.withDescription(jobDescription)
            		.build();

            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            if (scheduleMisfireEnum == null || scheduleMisfireEnum == QuartzMisfireEnum.withMisfireHandlingInstructionDoNothing) {
                scheduleBuilder = scheduleBuilder.withMisfireHandlingInstructionDoNothing();
            } else if (scheduleMisfireEnum != null && scheduleMisfireEnum == QuartzMisfireEnum.withHandlingInstructionFireAndProceed) {
                scheduleBuilder = scheduleBuilder.withMisfireHandlingInstructionFireAndProceed();
            } else if (scheduleMisfireEnum != null && scheduleMisfireEnum == QuartzMisfireEnum.withMisfireHandlingInstructionIgnoreMisfires) {
                scheduleBuilder = scheduleBuilder.withMisfireHandlingInstructionIgnoreMisfires();
            }

            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .usingJobData(QuartzContants.JOBDETAIL_DATAMAP_KEY, jobData)
            		.withIdentity(className, jobGroup)
            		.withSchedule(scheduleBuilder)
            		.build();
            
            scheduler.scheduleJob(jobDetail, trigger);
            
            logger.debug("===> create quartzJob success, misfireinstruction:{}, className:{}", trigger.getMisfireInstruction(), className);
        } catch (ClassNotFoundException e) {
            throw new SchedulerException("未找到JOB类 " + e.getLocalizedMessage(), e);
        } catch (RuntimeException e) {
            throw new SchedulerException("创建定时任务失败，" + e.getLocalizedMessage(), e);
        }
    }

    /**
     * 更新定时任务
     *
     * @param scheduler
     *            the scheduler
     * @param className
     *            the job name
     * @param jobGroup
     *            the job group
     * @param cronExpression
     *            the cron expression
     * @throws SchedulerException
     */
    public static void updateScheduleJob(Scheduler scheduler, QuartzMisfireEnum scheduleMisfireEnum, String className, String jobGroup, String cronExpression, String jobDescription, String jobData) throws SchedulerException {
        try {
            if (!checkExists(scheduler, className, jobGroup)) {
                throw new SchedulerException(String.format("Job不存在, className:{%s},jobGroup:{%s}", className, jobGroup));
            }

            // 按新的cronExpression表达式重新构建trigger
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            if (scheduleMisfireEnum == null || scheduleMisfireEnum == QuartzMisfireEnum.withMisfireHandlingInstructionDoNothing) {
                scheduleBuilder = scheduleBuilder.withMisfireHandlingInstructionDoNothing();
            } else if (scheduleMisfireEnum != null && scheduleMisfireEnum == QuartzMisfireEnum.withHandlingInstructionFireAndProceed) {
                scheduleBuilder = scheduleBuilder.withMisfireHandlingInstructionFireAndProceed();
            } else if (scheduleMisfireEnum != null && scheduleMisfireEnum == QuartzMisfireEnum.withMisfireHandlingInstructionIgnoreMisfires) {
                scheduleBuilder = scheduleBuilder.withMisfireHandlingInstructionIgnoreMisfires();
            }
            
            TriggerKey triggerKey = QuartzUtils.getTriggerKey(className, jobGroup);
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .usingJobData(QuartzContants.JOBDETAIL_DATAMAP_KEY, jobData)
                    .withIdentity(triggerKey)
                    .withSchedule(scheduleBuilder)
                    .withDescription(jobDescription)
                    .build();
            
            // 忽略状态为PAUSED的任务，解决集群环境中在其他机器设置定时任务为PAUSED状态后，集群环境启动另一台主机时定时任务全被唤醒的bug
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
            if (!triggerState.name().equalsIgnoreCase(QuartzContants.JOB_STATUS_PAUSED)) {
                scheduler.rescheduleJob(triggerKey, trigger);
            }
            logger.debug("===> updateScheduleJob success, className:{}", className);
        } catch (RuntimeException e) {
            throw new SchedulerException("更新失败，" + e.getLocalizedMessage(), e);
        }
    }

    /**
     * 运行一次任务
     * 
     * @param scheduler
     * @param className
     * @param jobGroup
     * @throws SchedulerException
     */
    public static void runOnce(Scheduler scheduler, String className, String jobGroup, JobDataMap jobDataMap) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(className, jobGroup);
        try {
            scheduler.triggerJob(jobKey, jobDataMap);
            logger.debug("===> triggerJob success, className:{}", className);
        } catch (JobPersistenceException e) {
            throw new SuntionException(e.getLocalizedMessage(), e);
        } catch (RuntimeException e) {
            throw new SchedulerException(e.getLocalizedMessage(), e);
        }
    }

    /**
     * 暂停任务
     * 
     * @param scheduler
     * @param className
     * @param jobGroup
     * @throws SchedulerException
     */
    public static void pauseJob(Scheduler scheduler, String className, String jobGroup) throws SchedulerException {
        try {
            if (checkExists(scheduler, className, jobGroup)) {
                scheduler.pauseJob(getJobKey(className, jobGroup));
                logger.debug("===> pauseJob success, className:{}", className);
            } else {
                throw new IllegalArgumentException("not found Job. className:" + className + ", jobGroup:" + jobGroup);
            }
        } catch (RuntimeException e) {
            throw new SchedulerException("暂停定时任务失败" + e.getLocalizedMessage(), e);
        }
    }

    /**
     * 恢复任务
     *
     * @param scheduler
     * @param className
     * @param jobGroup
     * @throws SchedulerException
     */
    public static void resumeJob(Scheduler scheduler, String className, String jobGroup) throws SchedulerException {
        try {
            if (checkExists(scheduler, className, jobGroup)) {
                scheduler.resumeJob(getJobKey(className, jobGroup));
                logger.debug("===> resumeJob success, className:{}", className);
            } else {
                throw new IllegalArgumentException("not found Job. className:" + className + ", jobGroup:" + jobGroup);
            }
        } catch (RuntimeException e) {
            throw new SchedulerException("恢复失败，" + e.getLocalizedMessage() , e);
        }
    }

    /**
     * 暂停触发器
     * 
     * @param className
     * @param jobGroup
     *            2016年10月10日上午9:40:19
     * @throws SchedulerException
     */
    public static void pauseTrigger(Scheduler scheduler, String className, String jobGroup) throws SchedulerException {
        try {
            if (checkExists(scheduler, className, jobGroup)) {
                TriggerKey triggerKey = getTriggerKey(className, jobGroup);
                scheduler.pauseTrigger(triggerKey);
                logger.debug("===> Pause success, triggerKey:{}", triggerKey);
            } else {
                throw new IllegalArgumentException("not found Job. className:" + className + ", jobGroup:" + jobGroup);
            }
        } catch (RuntimeException e) {
            throw new SchedulerException("暂停失败," + e.getLocalizedMessage(), e);
        }
    }

    /**
     * 重新开始任务
     * 
     * @param className
     * @param jobGroup
     *            2016年10月10日上午9:40:58
     * @throws SchedulerException
     */
    public static void resumeTrigger(Scheduler scheduler, String className, String jobGroup) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(className, jobGroup);
        try {
            if (checkExists(scheduler, className, jobGroup)) {
                scheduler.resumeTrigger(triggerKey);
                logger.debug("===> resumeTrigger success, triggerKey:{}", triggerKey);
            } else {
                throw new IllegalArgumentException("not found Job. className:" + className + ", jobGroup:" + jobGroup);
            }
        } catch (RuntimeException e) {
            throw new SchedulerException("重新开始失败," + e.getLocalizedMessage(), e);
        }
    }

    /**
     * 删除定时任务
     *
     * @param scheduler
     * @param className
     * @param jobGroup
     * @throws SchedulerException
     */
    public static void deleteScheduleJob(Scheduler scheduler, String className, String jobGroup) throws SchedulerException {
        try {
            if (checkExists(scheduler, className, jobGroup)) {
            	scheduler.deleteJob(getJobKey(className, jobGroup));
                logger.debug("===> deleteScheduleJob success, className:{}, jobGroup:{} ", className, jobGroup);
            } else {
                throw new IllegalArgumentException("not found Job. className:" + className + ", jobGroup:" + jobGroup);
            }
        } catch (RuntimeException e) {
            throw new SchedulerException("deleteJob failed. " + e.getLocalizedMessage(), e);
        }
    }
    
    
    /**
     *  获取当前的任务
     * @param scheduler
     * @return
     * @throws SchedulerException
     */
    public static List<QuartzJob> scheduleCronJobList(Scheduler scheduler) throws SchedulerException {
        List<QuartzJob> list = new ArrayList<>();
        for (String groupJob : scheduler.getJobGroupNames()) {
            for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.<JobKey> groupEquals(groupJob))) {
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    JobDetail jobDetail = scheduler.getJobDetail(jobKey);

                    String cronExpression = "", createTime = "";

                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        cronExpression = cronTrigger.getCronExpression();
                        createTime = cronTrigger.getDescription();
                    }
                    QuartzJob info = new QuartzJob();
                    info.setJobClass(jobKey.getName());
                    info.setJobGroup(jobKey.getGroup());
                    info.setJobDescription(jobDetail.getDescription());
                    String param = jobDetail.getJobDataMap().getString(QuartzContants.JOBDETAIL_DATAMAP_KEY);
                    info.setJobData(param);
                    info.setJobStatus(triggerState.name());
                    info.setCronExpression(cronExpression);
                    info.setCreateTime(createTime);
                    list.add(info);
                }
            }
        }
        return list;
    }
    
}
