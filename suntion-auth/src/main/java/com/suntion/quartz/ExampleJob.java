package com.suntion.quartz;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import suntion.core.quartz.utils.QuartzContants;

/**
 * 举例job运行
 * 
 * @author suns suntion@yeah.net
 * @since 2018年2月27日上午10:54:21
 */

// 第一个任务还未执行完整，第二个任务如果到了执行时间，则会立马开启新线程执行任务，这样如果我们是从数据库读取信息，两次重复读取可能出现重复执行任务的情况，所以我们需要将这个值设置为false，这样第二个任务会往后推迟，只有在第一个任务执行完成后才会执行第二个任务。我们只需要在任务类上加入disallowconcurrentExeution就可以了
@DisallowConcurrentExecution
public class ExampleJob extends QuartzJobBean {
	private final static org.slf4j.Logger Logger = LoggerFactory.getLogger(ExampleJob.class);

	@Override
	public void executeInternal(JobExecutionContext context) throws JobExecutionException {
		String jobName = context.getJobDetail().getJobClass().getName();
		String paramStr = context.getMergedJobDataMap().getString(QuartzContants.JOBDETAIL_DATAMAP_KEY);
		Logger.info("Job '{}' executed. param '{}'.", jobName, paramStr);
	}
}
