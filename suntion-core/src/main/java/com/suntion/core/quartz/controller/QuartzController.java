package com.suntion.core.quartz.controller;

import com.suntion.common.lang.ResponseEntity;
import com.suntion.core.aspect.LogOperation;
import com.suntion.core.quartz.dto.QuartzJob;
import com.suntion.core.quartz.service.QuartzService;
import com.suntion.core.quartz.utils.QuartzContants;
import com.suntion.core.quartz.vo.JobVo;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 任务测试
 *
 * @author suns suntion@yeah.net
 * @since 2018年2月27日上午10:56:59
 */
@RestController
@RequestMapping("quartz")
public class QuartzController {

    private final QuartzService quartzService;

    @Autowired
    public QuartzController(QuartzService quartzService) {
        this.quartzService = quartzService;
    }


    private QuartzJob initQuartzJob(JobVo vo) {
        QuartzJob job = new QuartzJob();
        job.setJobClass(vo.getJobClass());
        job.setJobGroup(vo.getJobGroup());
        job.setCronExpression(vo.getCronExpression());
        job.setJobDescription(vo.getJobDescription());
        job.setJobData(vo.getJobData());
        return job;
    }

    private void assertVo(JobVo vo) {
        Assert.notNull(vo.getJobClass(), "paramter jobClass must not be null");
        Assert.notNull(vo.getCronExpression(), "paramter cronExpression must not be null");
        Assert.notNull(vo.getJobGroup(), "paramter jobGroup must not be null");
        Assert.notNull(vo.getJobDescription(), "paramter jobDescription must not be null");
    }


    @LogOperation("新增JOB")
    @PostMapping(value = "/job")
    public Object add(JobVo vo) throws SchedulerException {
        this.assertVo(vo);

        QuartzJob job = this.initQuartzJob(vo);
        job.setJobStatus(QuartzContants.JOB_STATUS_NONE);
        quartzService.createScheduleJob(job);

        return ResponseEntity.success();
    }

    @LogOperation("更新JOB")
    @PutMapping(value = "/job")
    public Object update(JobVo vo) throws SchedulerException {
        this.assertVo(vo);

        QuartzJob job = this.initQuartzJob(vo);
        job.setJobStatus(QuartzContants.JOB_STATUS_NONE);
        quartzService.updateScheduleJob(job);
        return ResponseEntity.success();
    }

    @LogOperation("删除JOB")
    @DeleteMapping(value = "/job/{jobClass}/{jobGroup}")
    public Object delete(@PathVariable("jobClass") String jobClass, @PathVariable("jobGroup") String jobGroup) throws SchedulerException {
        Assert.notNull(jobClass, "paramter jobClass must not be null");
        Assert.notNull(jobGroup, "paramter jobGroup must not be null");
        quartzService.delete(jobClass, jobGroup);
        return ResponseEntity.success();
    }

    @LogOperation("暂停JOB")
    @PutMapping(value = "/job/pause/{jobClass}/{jobGroup}")
    public Object pause(@PathVariable("jobClass") String jobClass, @PathVariable("jobGroup") String jobGroup) throws SchedulerException {
        quartzService.pause(jobClass, jobGroup);
        return ResponseEntity.success();
    }

    @LogOperation("恢复JOB")
    @PutMapping(value = "/job/resume/{jobClass}/{jobGroup}")
    public Object resume(@PathVariable("jobClass") String jobClass, @PathVariable("jobGroup") String jobGroup) throws SchedulerException {
        quartzService.resume(jobClass, jobGroup);
        return ResponseEntity.success();
    }

    @LogOperation("执行一次JOB")
    @PutMapping(value = "/job/runonce/{jobClass}/{jobGroup}")
    public Object runOnce(@PathVariable("jobClass") String jobClass, @PathVariable("jobGroup") String jobGroup, HttpServletRequest request) throws SchedulerException {
        String jobData = request.getParameter("jobData");
        quartzService.runOnce(jobClass, jobGroup, jobData);
        return ResponseEntity.success();
    }

    @LogOperation("查询JOB")
    @GetMapping(value = "/jobs")
    public Object list() throws SchedulerException {
        List<QuartzJob> list = this.quartzService.list();
        return ResponseEntity.success(list);
    }
}
