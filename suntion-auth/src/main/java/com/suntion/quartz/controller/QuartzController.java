package com.suntion.quartz.controller;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import suntion.core.aspect.LogOperation;
import suntion.core.common.lang.ResponseEntity;
import suntion.core.quartz.dto.QuartzJob;
import suntion.core.quartz.service.QuartzService;
import suntion.core.quartz.utils.QuartzContants;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 任务测试
 *
 * @author suns suntion@yeah.net
 * @since 2018年2月27日上午10:56:59
 */
@RestController
@RequestMapping("/api/quartz")
public class QuartzController {

    @Autowired RedisTemplate redisTemplate;

    private final QuartzService quartzService;

    @Autowired
    public QuartzController(QuartzService quartzService) {
        this.quartzService = quartzService;
    }

    @LogOperation("新增JOB")
    @PostMapping(value = "/jobs")
    public Object add(JobVo vo) throws SchedulerException {
        Assert.notNull(vo.getJobClass(), "paramter jobClass must not be null");
        Assert.notNull(vo.getCronExpression(), "paramter cronExpression must not be null");
        Assert.notNull(vo.getJobGroup(), "paramter jobGroup must not be null");
        Assert.notNull(vo.getJobDescription(), "paramter jobDescription must not be null");

        QuartzJob job = new QuartzJob();
        job.setJobClass(vo.getJobClass());
        job.setJobGroup(vo.getJobGroup());
        job.setCronExpression(vo.getCronExpression());
        job.setJobDescription(vo.getJobDescription());
        job.setJobData(vo.getJobData());
        job.setJobStatus(QuartzContants.JOB_STATUS_NONE);
        quartzService.createScheduleJob(job);

        return ResponseEntity.SUCCESS();
    }

    @LogOperation("更新JOB")
    @PutMapping(value = "/jobs")
    public Object update(JobVo vo) throws SchedulerException {
        Assert.notNull(vo.getJobClass(), "paramter jobClass must not be null");
        Assert.notNull(vo.getCronExpression(), "paramter cronExpression must not be null");
        Assert.notNull(vo.getJobGroup(), "paramter jobGroup must not be null");
        Assert.notNull(vo.getJobDescription(), "paramter jobDescription must not be null");

        QuartzJob job = new QuartzJob();
        job.setJobClass(vo.getJobClass());
        job.setJobGroup(vo.getJobGroup());
        job.setCronExpression(vo.getCronExpression());
        job.setJobDescription(vo.getJobDescription());
        job.setJobData(vo.getJobData());
        job.setJobStatus(QuartzContants.JOB_STATUS_NONE);
        quartzService.updateScheduleJob(job);
        return ResponseEntity.SUCCESS();
    }

    @LogOperation("删除JOB")
    @DeleteMapping(value = "/jobs/{jobClass}/{jobGroup}")
    public Object delete(@PathVariable("jobClass") String jobClass, @PathVariable("jobGroup") String jobGroup) throws SchedulerException {
        Assert.notNull(jobClass, "paramter jobClass must not be null");
        Assert.notNull(jobGroup, "paramter jobGroup must not be null");
        quartzService.delete(jobClass, jobGroup);
        return ResponseEntity.SUCCESS();
    }

    @LogOperation("暂停JOB")
    @PutMapping(value = "/jobs/pause/{jobClass}/{jobGroup}")
    public Object pause(@PathVariable("jobClass") String jobClass, @PathVariable("jobGroup") String jobGroup) throws SchedulerException {
        quartzService.pause(jobClass, jobGroup);
        return ResponseEntity.SUCCESS();
    }

    @LogOperation("恢复JOB")
    @PutMapping(value = "/jobs/resume/{jobClass}/{jobGroup}")
    public Object resume(@PathVariable("jobClass") String jobClass, @PathVariable("jobGroup") String jobGroup) throws SchedulerException {
        quartzService.resume(jobClass, jobGroup);
        return ResponseEntity.SUCCESS();
    }

    @LogOperation("执行一次JOB")
    @PutMapping(value = "/quartz/jobs/runonce/{jobClass}/{jobGroup}")
    public Object runOnce(@PathVariable("jobClass") String jobClass, @PathVariable("jobGroup") String jobGroup, HttpServletRequest request) throws SchedulerException {
        String jobData = request.getParameter("jobData");
        quartzService.runOnce(jobClass, jobGroup, jobData);
        return ResponseEntity.SUCCESS();
    }

    @LogOperation("查询JOB")
    @GetMapping(value = "/jobs")
    public Object list() throws SchedulerException {
        List<QuartzJob> list = this.quartzService.list();
        redisTemplate.opsForValue().set("asd","11111"+new Date().getTime());
        return ResponseEntity.SUCCESS(list);
    }
}
