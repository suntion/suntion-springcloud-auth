package com.suntion.core.quartz.service;

import com.suntion.core.quartz.dto.QuartzJob;
import com.suntion.core.quartz.utils.QuartzContants;
import com.suntion.core.quartz.utils.QuartzMisfireEnum;
import com.suntion.core.quartz.utils.QuartzUtils;
import org.quartz.JobDataMap;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 任务service 负责任务的创建 运行 更新
 * 
 * @author suns suntion@yeah.net
 * @since 2018年3月14日下午2:34:46
 */
@Service
public class QuartzService {
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;

	private static final String DEFAULT_GROUP = "jobGroup";

	/**
	 * 所有任务列表 2016年10月9日上午11:16:59
	 * 
	 * @throws SchedulerException SchedulerException
	 */
	public List<QuartzJob> list() throws SchedulerException {
		return QuartzUtils.scheduleCronJobList(schedulerFactoryBean.getScheduler());
	}

	/**
	 * 保存定时任务
	 * 
	 * @param job 2016年10月9日上午11:30:40
	 * @throws SchedulerException SchedulerException
	 */
	public void createScheduleJob(QuartzJob job, QuartzMisfireEnum scheduleMisfireEnum) throws SchedulerException {
		String jobClass = job.getJobClass();
		String jobGroup = job.getJobGroup();
		if (jobGroup == null || "".equals(jobGroup.trim())) {
			jobGroup = QuartzService.DEFAULT_GROUP;
		}
		if (scheduleMisfireEnum == null) {
			scheduleMisfireEnum = QuartzMisfireEnum.withMisfireHandlingInstructionDoNothing;
		}
		String cronExpression = job.getCronExpression();
		String jobDescription = job.getJobDescription();

		QuartzUtils.createScheduleJob(schedulerFactoryBean.getScheduler(), scheduleMisfireEnum, jobClass, jobGroup,
				cronExpression, jobDescription, job.getJobData());
	}

	/**
	 * 保存定时任务 Misfire 默认为 withMisfireHandlingInstructionDoNothing
	 *
	 * @param job JOB对象
	 * @throws SchedulerException SchedulerException
	 */
	public void createScheduleJob(QuartzJob job) throws SchedulerException {
		this.createScheduleJob(job, null);
	}

	/**
	 * 修改定时任务
	 * 
	 * @param job 2016年10月9日下午2:20:07
	 * @throws SchedulerException SchedulerException
	 */
	public void updateScheduleJob(QuartzJob job, QuartzMisfireEnum scheduleMisfireEnum) throws SchedulerException {
		String jobClass = job.getJobClass();
		String jobGroup = job.getJobGroup();
		if (jobGroup == null || "".equals(jobGroup.trim())) {
			jobGroup = QuartzService.DEFAULT_GROUP;
		}
		if (scheduleMisfireEnum == null) {
			scheduleMisfireEnum = QuartzMisfireEnum.withMisfireHandlingInstructionDoNothing;
		}
		String cronExpression = job.getCronExpression();
		String jobDescription = job.getJobDescription();

		QuartzUtils.updateScheduleJob(schedulerFactoryBean.getScheduler(), scheduleMisfireEnum, jobClass, jobGroup,
				cronExpression, jobDescription, job.getJobData());
	}

	/**
	 * 修改定时任务
	 * 
	 * @param job JOB对象
	 * @throws SchedulerException
	 */
	public void updateScheduleJob(QuartzJob job) throws SchedulerException {
		this.updateScheduleJob(job, QuartzMisfireEnum.withMisfireHandlingInstructionDoNothing);
	}

	/**
	 * 删除定时任务
	 * 
	 * @param jobClass JOB对象
	 * @param jobGroup 分组
	 * @throws SchedulerException SchedulerException
	 */
	public void delete(String jobClass, String jobGroup) throws SchedulerException {
		if (jobGroup == null || "".equals(jobGroup)) {
			jobGroup = DEFAULT_GROUP;
		}
		QuartzUtils.deleteScheduleJob(schedulerFactoryBean.getScheduler(), jobClass, jobGroup);
	}

	/**
	 * 暂停定时任务
	 * 
	 * @param jobClass JOB对象
	 * @param jobGroup 分组
	 * @throws SchedulerException SchedulerException
	 */
	public void pause(String jobClass, String jobGroup) throws SchedulerException {
		if (jobGroup == null || "".equals(jobGroup)) {
			jobGroup = DEFAULT_GROUP;
		}
		QuartzUtils.pauseJob(schedulerFactoryBean.getScheduler(), jobClass, jobGroup);
	}

	/**
	 * 重新开始任务
	 * 
	 * @param jobClass JOB对象
	 * @param jobGroup 分组
	 * @throws SchedulerException SchedulerException
	 */
	public void resume(String jobClass, String jobGroup) throws SchedulerException {
		if (jobGroup == null || "".equals(jobGroup)) {
			jobGroup = DEFAULT_GROUP;
		}
		QuartzUtils.resumeJob(schedulerFactoryBean.getScheduler(), jobClass, jobGroup);
	}

	/**
	 * 运行一次
	 *
	 * @param jobClass JOB对象
	 * @param jobGroup 分组
	 * @throws SchedulerException SchedulerException
	 */
	public void runOnce(String jobClass, String jobGroup, String jobData) throws SchedulerException {
		if (jobGroup == null || "".equals(jobGroup)) {
			jobGroup = DEFAULT_GROUP;
		}
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put(QuartzContants.JOBDETAIL_DATAMAP_KEY, jobData);
		QuartzUtils.runOnce(schedulerFactoryBean.getScheduler(), jobClass, jobGroup, jobDataMap);
	}

}