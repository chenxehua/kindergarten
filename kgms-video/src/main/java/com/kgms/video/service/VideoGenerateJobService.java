package com.kgms.video.service;

import com.kgms.common.util.IdGenerator;
import com.kgms.video.entity.GrowthVideo;
import com.kgms.video.mapper.GrowthVideoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 视频生成定时任务服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VideoGenerateJobService {

    private final GrowthVideoMapper videoMapper;

    /**
     * 生成上月的成长视频
     */
    public void generateLastMonthVideos() {
        LocalDate lastMonth = LocalDate.now().minusMonths(1);
        String monthStr = lastMonth.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        
        log.info("开始生成 {} 月度成长视频", monthStr);
        
        // TODO: 获取所有在园学生，逐一生成视频
        
        log.info("月度成长视频生成完成");
    }

    /**
     * 为指定学生生成视频任务
     */
    public String createVideoTask(String studentId, String month, String templateId) {
        GrowthVideo video = new GrowthVideo();
        video.setVideoId(IdGenerator.generateStrId());
        video.setStudentId(studentId);
        video.setVideoType("monthly");
        video.setVideoMonth(month);
        video.setTemplateId(templateId != null ? templateId : "default");
        video.setGenStatus(0); // 排队中
        video.setGenProgress(0);
        video.setAuditStatus(0);
        video.setViewCount(0);
        video.setDownloadCount(0);
        
        videoMapper.insert(video);
        
        // TODO: 加入消息队列，异步生成视频
        
        return video.getVideoId();
    }

    /**
     * 获取待生成的视频任务列表
     */
    public List<GrowthVideo> getPendingVideos() {
        return videoMapper.selectList(
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<GrowthVideo>()
                        .eq(GrowthVideo::getGenStatus, 0)
                        .orderByAsc(GrowthVideo::getCreateTime)
        );
    }
}
