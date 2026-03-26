package com.kgms.video.service;

import com.kgms.video.dto.VideoVO;
import com.kgms.video.entity.GrowthVideo;
import com.kgms.video.mapper.GrowthVideoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VideoServiceTest {

    @Mock
    private GrowthVideoMapper videoMapper;

    @InjectMocks
    private VideoService videoService;

    private GrowthVideo testVideo;

    @BeforeEach
    void setUp() {
        testVideo = new GrowthVideo();
        testVideo.setId(1L);
        testVideo.setVideoId("video_001");
        testVideo.setStudentId("stu_001");
        testVideo.setVideoUrl("https://oss.example.com/video/test.mp4");
        testVideo.setCoverUrl("https://oss.example.com/cover/test.jpg");
        testVideo.setDuration(180);
        testVideo.setGenStatus(1);
        testVideo.setAuditStatus(1);
        testVideo.setViewCount(0);
        testVideo.setDownloadCount(0);
    }

    @Test
    void testGetVideoList() {
        when(videoMapper.selectList(any())).thenReturn(Arrays.asList(testVideo));
        var result = videoService.getVideoList("stu_001");
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testGetVideoDetail() {
        when(videoMapper.selectOne(any())).thenReturn(testVideo);
        VideoVO vo = videoService.getVideoDetail("video_001");
        assertNotNull(vo);
    }

    @Test
    void testGenerateVideo() {
        when(videoMapper.insert(any(GrowthVideo.class))).thenReturn(1);
        String videoId = videoService.generateVideo("stu_001", "2026-03", null);
        assertNotNull(videoId);
    }

    @Test
    void testGetVideoPlayUrl() {
        when(videoMapper.selectOne(any())).thenReturn(testVideo);
        String url = videoService.getVideoPlayUrl("video_001");
        assertNotNull(url);
    }

    @Test
    void testGetVideoDownloadUrl() {
        when(videoMapper.selectOne(any())).thenReturn(testVideo);
        String url = videoService.getVideoDownloadUrl("video_001");
        assertNotNull(url);
    }
}
