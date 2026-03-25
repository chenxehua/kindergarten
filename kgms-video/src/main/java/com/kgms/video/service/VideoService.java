package com.kgms.video.service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kgms.common.exception.BusinessException;
import com.kgms.video.dto.VideoVO;
import com.kgms.video.entity.GrowthVideo;
import com.kgms.video.mapper.GrowthVideoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
public class VideoService {
    private final GrowthVideoMapper videoMapper;
    public List<VideoVO> getVideoList(String studentId) {
        return videoMapper.selectList(
                new LambdaQueryWrapper<GrowthVideo>()
                        .eq(GrowthVideo::getStudentId, studentId)
                        .orderByDesc(GrowthVideo::getCreateTime)
        ).stream().map(this::convertToVO).collect(Collectors.toList());
    }
    public VideoVO getVideoDetail(String videoId) {
        GrowthVideo video = videoMapper.selectOne(
                new LambdaQueryWrapper<GrowthVideo>().eq(GrowthVideo::getVideoId, videoId)
        );
        if (video == null) throw new BusinessException(404, "视频不存在");
        return convertToVO(video);
    }
    public String generateVideo(String studentId, String month, String templateId) {
        // TODO: 调用AI视频生成服务
        log.info("生成成长视频: studentId={}, month={}, templateId={}", studentId, month, templateId);
        GrowthVideo video = new GrowthVideo();
        video.setVideoId(java.util.UUID.randomUUID().toString());
        video.setStudentId(studentId);
        video.setVideoType("monthly");
        video.setVideoMonth(month);
        video.setTemplateId(templateId);
        video.setGenStatus(0); // 排队中
        video.setAuditStatus(0);
        video.setViewCount(0);
        video.setDownloadCount(0);
        videoMapper.insert(video);
        return video.getVideoId();
    }
    private VideoVO convertToVO(GrowthVideo video) {
        VideoVO vo = new VideoVO();
        vo.setVideoId(video.getVideoId());
        vo.setStudentId(video.getStudentId());
        vo.setVideoType(video.getVideoType());
        vo.setVideoMonth(video.getVideoMonth());
        vo.setVideoUrl(video.getVideoUrl());
        vo.setCoverUrl(video.getCoverUrl());
        vo.setDuration(video.getDuration());
        vo.setGenStatus(video.getGenStatus());
        vo.setAuditStatus(video.getAuditStatus());
        return vo;
    }
}
