package com.kgms.record.service;

import com.kgms.record.dto.RecordDTO;
import com.kgms.record.dto.RecordVO;
import com.kgms.record.entity.GrowthRecord;
import com.kgms.record.mapper.GrowthRecordMapper;
import com.kgms.common.exception.BusinessException;
import com.kgms.common.result.PageResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
        testRecord.setTeacherId("teacher_001");
        testRecord.setClassId("class_001");
        testRecord.setRecordDate(LocalDate.of(2026, 3, 26));
        testRecord.setPublishStatus(1);
        testRecord.setPublishTime(LocalDateTime.now());
    }

    /**
     * TC-RECORD-001: 保存成长记录 - 成功
     */
    @Test
    void testSaveRecord_Success() {
        // Given
        RecordDTO dto = new RecordDTO();
        dto.setStudentId("stu_001");
        dto.setRecordDate(LocalDate.of(2026, 3, 26));
        
        when(recordMapper.selectOne(any())).thenReturn(null);
        when(recordMapper.insert(any(GrowthRecord.class))).thenReturn(1);

        // When
        String recordId = recordService.saveRecord(dto, "teacher_001", "class_001", true);

        // Then
        assertNotNull(recordId);
        verify(recordMapper, times(1)).insert(any(GrowthRecord.class));
    }

    /**
     * TC-RECORD-002: 发布成长记录
     */
    @Test
    void testPublishRecord() {
        // Given
        when(recordMapper.selectOne(any())).thenReturn(testRecord);
        when(recordMapper.updateById(any())).thenReturn(1);

        // When
        recordService.publishRecord("record_001");

        // Then
        verify(recordMapper, times(1)).updateById(any(GrowthRecord.class));
    }

    /**
     * TC-RECORD-003: 获取记录详情
     */
    @Test
    void testGetRecordDetail() {
        // Given
        when(recordMapper.selectOne(any())).thenReturn(testRecord);

        // When
        RecordVO vo = recordService.getRecordDetail("record_001");

        // Then
        assertNotNull(vo);
        assertEquals("record_001", vo.getRecordId());
    }

    /**
     * TC-RECORD-004: 根据日期获取记录
     */
    @Test
    void testGetRecordByDate() {
        // Given
        when(recordMapper.selectOne(any())).thenReturn(testRecord);

        // When
        RecordVO vo = recordService.getRecordByDate("stu_001", LocalDate.of(2026, 3, 26));

        // Then
        assertNotNull(vo);
    }
}
