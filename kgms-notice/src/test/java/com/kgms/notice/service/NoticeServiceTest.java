package com.kgms.notice.service;

import com.kgms.notice.dto.NoticeDTO;
import com.kgms.notice.dto.NoticeVO;
import com.kgms.notice.entity.Notice;
import com.kgms.notice.mapper.NoticeMapper;
import com.kgms.common.exception.BusinessException;
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
import static org.mockito.ArgumentMatchers.anyString;
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
        testNotice.setKgId("kg_001");
        testNotice.setTitle("春季运动会通知");
        testNotice.setContent("本周五举行春季运动会");
        testNotice.setNoticeType("1");
        testNotice.setPublishBy("teacher_001");
        testNotice.setPublishTime(LocalDateTime.now());
        testNotice.setStatus(1);
    }

    /**
     * TC-NOTICE-001: 发布通知 - 成功
     */
    @Test
    void testPublishNotice_Success() {
        // Given
        NoticeDTO dto = new NoticeDTO();
        dto.setKgId("kg_001");
        dto.setTitle("测试通知");
        dto.setContent("测试内容");
        dto.setNoticeType("1");
        
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
        when(noticeMapper.selectList(any())).thenReturn(Arrays.asList(testNotice));

        // When
        List<NoticeVO> list = noticeService.getNoticeList("kg_001", null);

        // Then
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("春季运动会通知", list.get(0).getTitle());
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
    }

    /**
     * TC-NOTICE-003: 获取通知详情 - 不存在
     */
    @Test
    void testGetNoticeDetail_NotFound() {
        // Given
        when(noticeMapper.selectOne(any())).thenReturn(null);

        // When & Then
        assertThrows(BusinessException.class, () -> noticeService.getNoticeDetail("not_exist"));
    }

    /**
     * TC-NOTICE-004: 撤回通知
     */
    @Test
    void testWithdrawNotice_Success() {
        // Given
        when(noticeMapper.selectOne(any())).thenReturn(testNotice);
        when(noticeMapper.updateById(any())).thenReturn(1);

        // When
        noticeService.withdrawNotice("notice_001");

        // Then
        verify(noticeMapper, times(1)).updateById(any(Notice.class));
    }
}
