package com.kgms.growth.job;

import com.kgms.growth.service.GrowthProfileService;
import com.kgms.growth.service.MonthlyReportJobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 * 用于自动生成AI成长报告和视频
 */
@Slf4j
@Component
public class GrowthJob {

    private final GrowthProfileService growthProfileService;
    private final MonthlyReportJobService monthlyReportJobService;

    public GrowthJob(GrowthProfileService growthProfileService, MonthlyReportJobService monthlyReportJobService) {
        this.growthProfileService = growthProfileService;
        this.monthlyReportJobService = monthlyReportJobService;
    }

    /**
     * 每天凌晨1点执行 - 生成昨日AI成长画像
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void generateDailyProfile() {
        log.info("开始执行每日成长画像生成任务");
        try {
            // 获取所有在园学生
            // 逐个生成昨日成长画像
            // growthProfileService.generateDailyProfiles();
            log.info("每日成长画像生成任务完成");
        } catch (Exception e) {
            log.error("每日成长画像生成任务失败: {}", e.getMessage());
        }
    }

    /**
     * 每月1日凌晨2点执行 - 生成上月月度成长报告
     */
    @Scheduled(cron = "0 0 2 1 * ?")
    public void generateMonthlyReports() {
        log.info("开始执行月度成长报告生成任务");
        try {
            monthlyReportJobService.generateLastMonthReports();
            log.info("月度成长报告生成任务完成");
        } catch (Exception e) {
            log.error("月度成长报告生成任务失败: {}", e.getMessage());
        }
    }

    /**
     * 每月2日凌晨3点执行 - 生成上月成长视频
     */
    @Scheduled(cron = "0 0 3 2 * ?")
    public void generateMonthlyVideos() {
        log.info("开始执行月度成长视频生成任务");
        try {
            // TODO: 调用视频服务生成成长视频
            log.info("月度成长视频生成任务完成");
        } catch (Exception e) {
            log.error("月度成长视频生成任务失败: {}", e.getMessage());
        }
    }

    /**
     * 每天上午8点执行 - 发送每日提醒给未填写记录的班级老师
     */
    @Scheduled(cron = "0 0 8 * * ?")
    public void sendDailyReminder() {
        log.info("开始执行每日提醒任务");
        try {
            // 查询今日未填写记录的班级
            // 发送提醒给对应老师
            log.info("每日提醒任务完成");
        } catch (Exception e) {
            log.error("每日提醒任务失败: {}", e.getMessage());
        }
    }
}
