package com.kgms.classs.service;

import com.kgms.classs.dto.ClassDTO;
import com.kgms.classs.dto.ClassVO;
import com.kgms.classs.entity.ClassInfo;
import com.kgms.classs.mapper.ClassInfoMapper;
import com.kgms.common.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClassServiceTest {

    @Mock
    private ClassInfoMapper classInfoMapper;

    @InjectMocks
    private ClassService classService;

    private ClassInfo testClass;

    @BeforeEach
    void setUp() {
        testClass = new ClassInfo();
        testClass.setId(1L);
        testClass.setClassId("class_001");
        testClass.setClassName("向日葵班");
        testClass.setGradeName("小班");
        testClass.setTeacherId("teacher_001");
        testClass.setStudentCount(20);
        testClass.setMaxStudentCount(30);
        testClass.setStatus(1);
    }

    /**
     * TC-CLASS-001: 新增班级
     */
    @Test
    void testAddClass_Success() {
        // Given
        ClassDTO dto = new ClassDTO();
        dto.setClassName("玫瑰班");
        dto.setGradeName("中班");
        dto.setTeacherId("teacher_002");
        dto.setMaxStudentCount(25);
        
        when(classInfoMapper.insert(any(ClassInfo.class))).thenReturn(1);

        // When
        String classId = classService.addClass(dto);

        // Then
        assertNotNull(classId);
        verify(classInfoMapper, times(1)).insert(any(ClassInfo.class));
    }

    /**
     * TC-CLASS-002: 更新班级信息
     */
    @Test
    void testUpdateClass_Success() {
        // Given
        when(classInfoMapper.selectOne(any())).thenReturn(testClass);
        when(classInfoMapper.updateById(any())).thenReturn(1);

        ClassDTO dto = new ClassDTO();
        dto.setClassName("新的班级名");

        // When
        classService.updateClass("class_001", dto);

        // Then
        verify(classInfoMapper, times(1)).updateById(any());
    }

    /**
     * TC-CLASS-002: 更新班级 - 不存在
     */
    @Test
    void testUpdateClass_NotFound() {
        // Given
        when(classInfoMapper.selectOne(any())).thenReturn(null);

        // When & Then
        try {
            classService.updateClass("not_exist", new ClassDTO());
            fail("应该抛出异常");
        } catch (BusinessException e) {
            assertTrue(e.getMessage().contains("不存在"));
        }
    }

    /**
     * TC-CLASS-003: 删除班级
     */
    @Test
    void testDeleteClass_Success() {
        // Given
        when(classInfoMapper.selectOne(any())).thenReturn(testClass);
        when(classInfoMapper.updateById(any())).thenReturn(1);

        // When
        classService.deleteClass("class_001");

        // Then
        verify(classInfoMapper, times(1)).updateById(any());
    }

    /**
     * TC-CLASS-003: 删除班级 - 有学生
     */
    @Test
    void testDeleteClass_HasStudents() {
        // Given
        testClass.setStudentCount(10);
        when(classInfoMapper.selectOne(any())).thenReturn(testClass);

        // When & Then
        try {
            classService.deleteClass("class_001");
            fail("应该抛出异常");
        } catch (BusinessException e) {
            assertTrue(e.getMessage().contains("有学生"));
        }
    }

    /**
     * TC-CLASS-004: 获取班级列表
     */
    @Test
    void testGetClassList() {
        // Given
        List<ClassInfo> classes = Arrays.asList(testClass);
        when(classInfoMapper.selectList(any())).thenReturn(classes);

        // When
        List<ClassVO> result = classService.getClassList();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("向日葵班", result.get(0).getClassName());
    }

    /**
     * TC-CLASS-005: 获取班级详情
     */
    @Test
    void testGetClassDetail_Success() {
        // Given
        when(classInfoMapper.selectOne(any())).thenReturn(testClass);

        // When
        ClassVO vo = classService.getClassDetail("class_001");

        // Then
        assertNotNull(vo);
        assertEquals("class_001", vo.getClassId());
        assertEquals("向日葵班", vo.getClassName());
        assertEquals(20, vo.getStudentCount());
    }
}
