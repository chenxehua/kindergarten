package com.kgms.video.service;

import com.kgms.video.dto.VideoDTO;
import com.kgms.video.dto.VideoVO;
import com.kgms.video.entity.Video;
import com.kgms.video.mapper.VideoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VideoServiceTest {

    @Mock
    private VideoMapper videoMapper;

    @InjectMocks
    private VideoService videoService;

    private Video testVideo;

    @BeforeEach
    void setUp() {
        testVideo = new Video();
        testVideo.setId(1L);
        testVideo.setVideoId("video_001");
        testVideo.setVideoName("六一节目");
        testVideo.setVideoUrl("https://example.com/video.mp4");
        testVideo.setCoverUrl("https://example.com/cover.jpg");
        testVideo.setDuration(300);
        testVideo.setTeacherId("teacher_001");
        testVideo.setClassId("class_001");
        testVideo.setPublishStatus(1);
        testVideo.setCreateTime(LocalDateTime.now());
    }

    /**
     * TC-VIDEO-001: 上传视频
     */
    @Test
    void testUploadVideo_Success() {
        // Given
        VideoDTO dto = new VideoDTO();
        dto.setVideoName("运动会");
        dto.setVideoUrl("https://example.com/sports.mp4");
        dto.setCoverUrl("https://example.com/sports_cover.jpg");
        dto.setDuration(600);
        
        when(videoMapper.insert(any(Video.class))).thenReturn(1);

        // When
        String videoId = videoService.uploadVideo(dto, "teacher_001", "class_001");

        // Then
        assertNotNull(videoId);
        verify(videoMapper, times(1)).insert(any(Video.class));
    }

    /**
     * TC-VIDEO-002: 获取视频列表
     */
    @Test
    void testGetVideoList() {
        // Given
        List<Video> videos = Arrays.asList(testVideo);
        when(videoMapper.selectList(any())).thenReturn(videos);

        // When
        List<VideoVO> result = videoService.getVideoList("class_001");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("六一节目", result.get(0).getVideoName());
    }

    /**
     * TC-VIDEO-003: 获取视频详情
     */
    @Test
    void testGetVideoDetail_Success() {
        // Given
        when(videoMapper.selectOne(any())).thenReturn(testVideo);

        // When
        VideoVO vo = videoService.getVideoDetail("video_001");

        // Then
        assertNotNull(vo);
        assertEquals("video_001", vo.getVideoId());
        assertEquals("六一节目", vo.getVideoName());
    }

    /**
     * TC-VIDEO-004: 删除视频
     */
    @Test
    void testDeleteVideo_Success() {
        // Given
        when(videoMapper.selectOne(any())).thenReturn(testVideo);
        when(videoMapper.updateById(any())).thenReturn(1);

        // When
        videoService.deleteVideo("video_001");

        // Then
        verify(videoMapper, times(1)).updateById(any());
    }
}
