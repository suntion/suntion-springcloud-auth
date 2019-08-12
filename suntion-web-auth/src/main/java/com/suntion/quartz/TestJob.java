package com.suntion.quartz;

import com.suntion.bankcard.BankCardFeginClient;
import com.suntion.common.lang.ResponseEntity;
import com.suntion.core.quartz.utils.QuartzContants;
import com.suntion.sms.SmsFeginClient;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 *
 * @Author scott
 * @Data 2019/7/1821:32
 **/
@DisallowConcurrentExecution
public class TestJob implements Job {
    private final static org.slf4j.Logger Logger = LoggerFactory.getLogger(TestJob.class);

    @Autowired
    SmsFeginClient smsFeginClient;


    @Autowired
    BankCardFeginClient bankCardFeginClient;

    @Override
    public void execute(JobExecutionContext context) {
        String jobName = context.getJobDetail().getJobClass().getName();
        String paramStr = context.getMergedJobDataMap().getString(QuartzContants.JOBDETAIL_DATAMAP_KEY);
        Logger.info("Job '{}' executed. param '{}'.", jobName, paramStr);

        for (int i=0; i< Integer.valueOf(paramStr); i++) {
            int x=(int)(Math.random()*100);
            ResponseEntity smsResponse = smsFeginClient.sms("13629711009",x+"");
            Logger.info(smsResponse.toString());

            ResponseEntity bankcardResponse = bankCardFeginClient.withhold("6214830234619722", new BigDecimal(x));
            Logger.info(bankcardResponse.toString());
        }
    }
}
