package com.kgms.record.service;

import com.kgms.record.dto.RecordDTO;
import com.kgms.record.dto.RecordVO;
import com.kgms.record.entity.GrowthRecord;
import com.kgms.record.mapper.GrowthRecordMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecordServiceTest {

    @Mock
    private GrowthRecordMapper recordMapper;

    @InjectMocks
    private RecordService recordService;

    private GrowthRecord testRecord;

    @BeforeEach
    void setUp() {
        testRecord = new GrowthRecord();
        testRecord.setId(1L);
        testRecord.setRecordId("record_001");
        testRecord.setStudentId("stu_001");
        testRecord.setRecordDate(LocalDate.now());
        testRecord.setTeacherId("teacher_001");
        testRecord.setClassId("class_001");
        testRecord.setEmotionType("开心");
        testRecord.setBreakfast("食欲良好");
        testRecord.setOverallNote("今天表现很好");
        testRecord.setPublishStatus(1);
    }

    /**
     * TC-RECORD-001: 老师发布每日成长记录
     */
    @Test
    void testSaveRecord_NewRecord() {
        // Given
        RecordDTO dto = new RecordDTO();
        dto.setStudentId("stu_001");
        dto.setRecordDate(LocalDate.now());
        dto.setEmotionType("开心");
        dto.setBreakfast("食欲良好");
        
        when(recordMapper.selectOne(any())).thenReturn(null);
        when(recordMapper.insert(any(GrowthRecord.class))).thenReturn(1);

        // When
        String recordId = recordService.saveRecord(dto, "teacher_001", "class_001", true);

        // Then
        assertNotNull(recordId);
        verify(recordMapper, times(1)).insert(any(GrowthRecord.class));
    }

    /**
     * TC-RECORD-003: 家长查看孩子成长记录列表
     */
    @Test
    void testGetStudentRecords() {
        // Given
        List<GrowthRecord> records = Arrays.asList(testRecord);
        when(recordMapper.selectPage(any(), any())).thenReturn(null); // 简化

        // When
        // 调用查询方法

        // Then
        verify(recordMapper, times(1)).selectPage(any(), any());
    }

    /**
     * TC-RECORD-004: 家长查看记录详情
     */
    @Test
    void testGetRecordDetail_Success() {
        // Given
        when(recordMapper.selectOne(any())).thenReturn(testRecord);

        // When
        RecordVO vo = recordService.getRecordDetail("record_001");

        // Then
        assertNotNull(vo);
        assertEquals("record_001", vo.getRecordId());
        assertEquals("开心", vo.getEmotionType());
    }

    /**
     * TC-RECORD-004: 查看记录详情 - 记录不存在
     */
    @Test
    void testGetRecordDetail_NotFound() {
        // Given
        when(recordMapper.selectOne(any())).thenReturn(null);

        // When & Then
        assertThrows(Exception.class, () -> recordService.getRecordDetail("not_exist"));
    }

    /**
     * TC-RECORD-007: 查看历史某天记录
     */
    @Test
    void testGetRecordByDate() {
        // Given
        when(recordMapper.selectOne(any())).thenReturn(testRecord);

        // When
        RecordVO vo = recordService.getRecordByDate("stu_001", LocalDate.now());

        // Then
        assertNotNull(vo);
        assertEquals("stu_001", vo.getStudentId());
    }
}