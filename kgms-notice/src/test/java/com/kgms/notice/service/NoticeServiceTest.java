package com.kgms.notice.service;

import com.kgms.notice.dto.NoticeDTO;
import com.kgms.notice.dto.NoticeVO;
import com.kgms.notice.entity.Notice;
import com.kgms.notice.mapper.NoticeMapper;
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
class NoticeServiceTest {

    @Mock
    private NoticeMapper noticeMapper;

    @InjectMocks
    private NoticeService noticeService;

    private Notice testNotice;

    @BeforeEach
    void setUp() {
        testNotice = new Notice();
        testNotice.setId(1L);
        testNotice.setNoticeId("notice_001");
        testNotice.setTitle("开学通知");
        testNotice.setContent("请各位家长注意开学时间");
        testNotice.setNoticeType(1);
        testNotice.setPublisherId("teacher_001");
        testNotice.setPublishTime(LocalDateTime.now());
        testNotice.setPublishStatus(1);
    }

    /**
     * TC-NOTICE-001: 发布通知
     */
    @Test
    void testPublishNotice_Success() {
        // Given
        NoticeDTO dto = new NoticeDTO();
        dto.setTitle("放假通知");
        dto.setContent("明天放假");
        dto.setNoticeType(1);
        
        when(noticeMapper.insert(any(Notice.class))).thenReturn(1);

        // When
        String noticeId = noticeService.publishNotice(dto, "teacher_001");

        // Then
        assertNotNull(noticeId);
        verify(noticeMapper, times(1)).insert(any(Notice.class));
    }

    /**
     * TC-NOTICE-002: 获取通知列表
     */
    @Test
    void testGetNoticeList() {
        // Given
        List<Notice> notices = Arrays.asList(testNotice);
        when(noticeMapper.selectList(any())).thenReturn(notices);

        // When
        List<NoticeVO> result = noticeService.getNoticeList();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("开学通知", result.get(0).getTitle());
    }

    /**
     * TC-NOTICE-003: 获取通知详情
     */
    @Test
    void testGetNoticeDetail_Success() {
        // Given
        when(noticeMapper.selectOne(any())).thenReturn(testNotice);

        // When
        NoticeVO vo = noticeService.getNoticeDetail("notice_001");

        // Then
        assertNotNull(vo);
        assertEquals("notice_001", vo.getNoticeId());
        assertEquals("开学通知", vo.getTitle());
    }

    /**
     * TC-NOTICE-004: 撤回通知
     */
    @Test
    void testRevokeNotice_Success() {
        // Given
        when(noticeMapper.selectOne(any())).thenReturn(testNotice);
        when(noticeMapper.updateById(any())).thenReturn(1);

        // When
        noticeService.revokeNotice("notice_001");

        // Then
        verify(noticeMapper, times(1)).updateById(any());
    }

    /**
     * TC-NOTICE-005: 删除通知
     */
    @Test
    void testDeleteNotice_Success() {
        // Given
        when(noticeMapper.selectOne(any())).thenReturn(testNotice);
        when(noticeMapper.deleteById(any())).thenReturn(1);

        // When
        noticeService.deleteNotice("notice_001");

        // Then
        verify(noticeMapper, times(1)).deleteById(any());
    }
}
