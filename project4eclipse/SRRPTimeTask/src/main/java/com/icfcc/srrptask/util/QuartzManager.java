package com.icfcc.srrptask.util;

import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;

import com.icfcc.srrptask.listener.QuartzJobListener;

public class QuartzManager {


    /** 
     * @Description: 添加一个定时任务 
     *  
     * @param jobName 任务名 
     * @param jobGroupName  任务组名 
     * @param triggerName 触发器名 
     * @param triggerGroupName 触发器组名 
     * @param jobClass  任务 
     * @param cron   时间设置，参考quartz说明文档  
     */  
    @SuppressWarnings({ "unchecked", "rawtypes" })  
    public static void addJob(Scheduler sched,String jobName, String jobGroupName, 
            String triggerName, String triggerGroupName, Class jobClass, String cron) {  
        try {  
        	 // ①获取调度器中所有的触发器组
            List<String> triggerGroups = sched.getTriggerGroupNames();
            // ②重新恢复在tgroup1组中，名为trigger1触发器的运行
            
            // 任务名，任务组，任务执行类
            JobDetail jobDetail= JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
            // 触发器  
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            
            // 触发器名,触发器组  
            triggerBuilder.withIdentity(triggerName, triggerGroupName);
            triggerBuilder.startNow();
            // 触发器时间设定  
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
            // 创建Trigger对象
            CronTrigger trigger = (CronTrigger) triggerBuilder.build();
            //添加监听
            sched.getListenerManager().addJobListener(new QuartzJobListener(), KeyMatcher.keyEquals(jobDetail.getKey()));
            // 调度容器设置JobDetail和Trigger
            sched.scheduleJob(jobDetail, trigger);  
            // 启动  
            if (!sched.isShutdown()) {  
                sched.start();  
            }  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }  

    /** 
     * @Description: 修改一个任务的触发时间
     *  
     * @param jobName 
     * @param jobGroupName
     * @param triggerName 触发器名
     * @param triggerGroupName 触发器组名 
     * @param cron   时间设置，参考quartz说明文档   
     */  
    public static void modifyJobTime(Scheduler sched,String jobName, 
            String jobGroupName, String triggerName, String triggerGroupName, String cron) {  
        try {  
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);  
            if (trigger == null) {  
                return;  
            }  

            String oldTime = trigger.getCronExpression();  
            if (!oldTime.equalsIgnoreCase(cron)) { 
                /** 方式一 ：调用 rescheduleJob 开始 */
                // 触发器  
                TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
                // 触发器名,触发器组  
                triggerBuilder.withIdentity(triggerName, triggerGroupName);
                triggerBuilder.startNow();
                // 触发器时间设定  
                triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
                // 创建Trigger对象
                trigger = (CronTrigger) triggerBuilder.build();
                // 方式一 ：修改一个任务的触发时间
                sched.rescheduleJob(triggerKey, trigger);
                /** 方式一 ：调用 rescheduleJob 结束 */

                /** 方式二：先删除，然后在创建一个新的Job  */
                //JobDetail jobDetail = sched.getJobDetail(JobKey.jobKey(jobName, jobGroupName));  
                //Class<? extends Job> jobClass = jobDetail.getJobClass();  
                //removeJob(jobName, jobGroupName, triggerName, triggerGroupName);  
                //addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass, cron); 
                /** 方式二 ：先删除，然后在创建一个新的Job */
            }  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }  
  /** 
  * 暂停一个job 
  *  
  * @param scheduleJob 
  * @throws SchedulerException 
  */  
 public static void pauseJob(Scheduler sched,String jobName, 
         String jobGroupName) {  
     JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);  
     try {
		sched.pauseJob(jobKey);
	} catch (SchedulerException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
 }  
/** 
* 立即执行job 
*  
* @param scheduleJob 
* @throws SchedulerException 
*/  
public static void runAJobNow(Scheduler sched,String jobName, 
        String jobGroupName) throws SchedulerException {  
	JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);  
	sched.triggerJob(jobKey);  
}  
 /** 
  * 恢复一个job 
  *  
  * @param scheduleJob 
  * @throws SchedulerException 
  */  
 public static void resumeJob(Scheduler sched,String jobName, 
         String jobGroupName){  
     JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);  
     try {
		sched.resumeJob(jobKey);
	} catch (SchedulerException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}  
 }  
    /** 
     * @Description: 移除一个任务 
     *  
     * @param jobName 
     * @param jobGroupName 
     * @param triggerName 
     * @param triggerGroupName 
     */  
    public static void removeJob(Scheduler sched,String jobName, String jobGroupName,  
            String triggerName, String triggerGroupName) {  
        try {  

            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);

            sched.pauseTrigger(triggerKey);// 停止触发器  
            sched.unscheduleJob(triggerKey);// 移除触发器  
            sched.deleteJob(JobKey.jobKey(jobName, jobGroupName));// 删除任务  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }  

    /** 
     * @Description:启动所有定时任务 
     */  
    public static void startJobs(Scheduler sched) {  
        try {  
            sched.start();  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }  

    /** 
     * @Description:关闭所有定时任务 
     */  
    public static void shutdownJobs(Scheduler sched) {  
        try {  
            if (!sched.isShutdown()) {  
                sched.shutdown();  
            }  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }  
//    /** 
//     * 添加任务到任务队列 
//     *  
//     * @param scheduleJob 
//     * @throws SchedulerException 
//     * @throws ClassNotFoundException 
//     */  
//    public ScheduleJob addJob(ScheduleJob job) throws SchedulerException, ClassNotFoundException {  
//        if (job == null || !ScheduleJob.STATUS_RUNNING.equals(job.getJobStatus())) {  
//            return null;  
//        }  
//  
//        Scheduler scheduler = schedulerFactoryBean.getScheduler();  
//        log.debug(scheduler  
//                + ".......................................................................................add");  
//        TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());  
//  
//        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);  
//  
//        // 不存在，创建一个  
//        if (null == trigger) {  
//            Class clazz = Class.forName(job.getBeanClass());  
//  
//            JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getJobName(), job.getJobGroup()).build();  
//  
//            jobDetail.getJobDataMap().put("scheduleJob", job);  
//  
//            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());  
//  
//            trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup())  
//                    .withSchedule(scheduleBuilder).build();  
//  
//            scheduler.scheduleJob(jobDetail, trigger);  
//        } else {  
//            // Trigger已存在，那么更新相应的定时设置  
//            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());  
//  
//            // 按新的cronExpression表达式重新构建trigger  
//            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();  
//  
//            // 按新的trigger重新设置job执行  
//            scheduler.rescheduleJob(triggerKey, trigger);  
//        }  
//  
//        // 得到任务下一次的计划执行时间  
//        Date nextProcessTime = trigger.getNextFireTime();  
//        job.setNextProcessTime(nextProcessTime);  
//        return job;  
//    }  
//  
//    /** 
//     * 服务器启动，加载job_status=1的任务到任务队列中 
//     *  
//     * @throws Exception 
//     */  
//    @PostConstruct  
//    public void init() throws Exception {  
//  
//        Scheduler scheduler = schedulerFactoryBean.getScheduler();  
//  
//        // 这里获取任务信息数据  
//        List<ScheduleJob> jobList = scheduleJobMapper.getAllJobs();  
//  
//        for (ScheduleJob job : jobList) {  
//            addJob(job);  
//        }  
//    }  
//  
//    /** 
//     * 获取所有计划中的任务列表 
//     *  
//     * @return 
//     * @throws SchedulerException 
//     */  
//    @Deprecated  
//    public List<ScheduleJob> getAllJob() throws SchedulerException {  
//        Scheduler scheduler = schedulerFactoryBean.getScheduler();  
//        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();  
//        Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);  
//        List<ScheduleJob> jobList = new ArrayList<ScheduleJob>();  
//        for (JobKey jobKey : jobKeys) {  
//            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);  
//            for (Trigger trigger : triggers) {  
//                ScheduleJob job = new ScheduleJob();  
//                job.setJobName(jobKey.getName());  
//                job.setJobGroup(jobKey.getGroup());  
//                job.setDescription("触发器:" + trigger.getKey());  
//                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());  
//                job.setJobStatus(triggerState.name());  
//                if (trigger instanceof CronTrigger) {  
//                    CronTrigger cronTrigger = (CronTrigger) trigger;  
//                    String cronExpression = cronTrigger.getCronExpression();  
//                    job.setCronExpression(cronExpression);  
//                }  
//                jobList.add(job);  
//            }  
//        }  
//        return jobList;  
//    }  
//  
//    /** 
//     * 获取所有正在运行的job 
//     *  
//     * @return 
//     * @throws SchedulerException 
//     */  
//    @Deprecated  
//    public List<ScheduleJob> getRunningJob() throws SchedulerException {  
//        Scheduler scheduler = schedulerFactoryBean.getScheduler();  
//        List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();  
//        List<ScheduleJob> jobList = new ArrayList<ScheduleJob>(executingJobs.size());  
//        for (JobExecutionContext executingJob : executingJobs) {  
//            ScheduleJob job = new ScheduleJob();  
//            JobDetail jobDetail = executingJob.getJobDetail();  
//            JobKey jobKey = jobDetail.getKey();  
//            Trigger trigger = executingJob.getTrigger();  
//            job.setJobName(jobKey.getName());  
//            job.setJobGroup(jobKey.getGroup());  
//            job.setDescription("触发器:" + trigger.getKey());  
//            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());  
//            job.setJobStatus(triggerState.name());  
//            if (trigger instanceof CronTrigger) {  
//                CronTrigger cronTrigger = (CronTrigger) trigger;  
//                String cronExpression = cronTrigger.getCronExpression();  
//                job.setCronExpression(cronExpression);  
//            }  
//            jobList.add(job);  
//        }  
//        return jobList;  
//    }  
//  
//    /** 
//     * 暂停一个job 
//     *  
//     * @param scheduleJob 
//     * @throws SchedulerException 
//     */  
//    @Deprecated  
//    public void pauseJob(ScheduleJob scheduleJob) throws SchedulerException {  
//        Scheduler scheduler = schedulerFactoryBean.getScheduler();  
//        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());  
//        scheduler.pauseJob(jobKey);  
//    }  
//  
//    /** 
//     * 恢复一个job 
//     *  
//     * @param scheduleJob 
//     * @throws SchedulerException 
//     */  
//    @Deprecated  
//    public void resumeJob(ScheduleJob scheduleJob) throws SchedulerException {  
//        Scheduler scheduler = schedulerFactoryBean.getScheduler();  
//        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());  
//        scheduler.resumeJob(jobKey);  
//    }  
//  
//    /** 
//     * 删除一个job 
//     *  
//     * @param scheduleJob 
//     * @throws SchedulerException 
//     */  
//    public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException {  
//        Scheduler scheduler = schedulerFactoryBean.getScheduler();  
//        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());  
//        scheduler.deleteJob(jobKey);  
//    }  
//  
//    /** 
//     * 立即执行job 
//     *  
//     * @param scheduleJob 
//     * @throws SchedulerException 
//     */  
//    public void runAJobNow(ScheduleJob scheduleJob) throws SchedulerException {  
//        Scheduler scheduler = schedulerFactoryBean.getScheduler();  
//        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());  
//        scheduler.triggerJob(jobKey);  
//    }  
//  
//    /** 
//     * 更新job时间表达式 
//     *  
//     * @param scheduleJob 
//     * @throws SchedulerException 
//     */  
//    public ScheduleJob updateJobCron(ScheduleJob scheduleJob) throws SchedulerException {  
//        Scheduler scheduler = schedulerFactoryBean.getScheduler();  
//  
//        TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());  
//  
//        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);  
//  
//        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());  
//  
//        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();  
//  
//        scheduler.rescheduleJob(triggerKey, trigger);  
//        // 得到任务下一次的计划执行时间  
//        Date nextProcessTime = trigger.getNextFireTime();  
//        scheduleJob.setNextProcessTime(nextProcessTime);  
//  
//        return scheduleJob;  
//    } 
}
