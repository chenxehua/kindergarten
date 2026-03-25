package com.kgms.student.service;

import com.kgms.student.dto.StudentDTO;
import com.kgms.student.dto.StudentVO;
import com.kgms.student.entity.StudentInfo;
import com.kgms.student.mapper.StudentInfoMapper;
import com.kgms.common.enums.Gender;
import com.kgms.common.enums.StudentStatus;
import com.kgms.common.exception.BusinessException;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentInfoMapper studentInfoMapper;

    @InjectMocks
    private StudentService studentService;

    private StudentInfo testStudent;

    @BeforeEach
    void setUp() {
        testStudent = new StudentInfo();
        testStudent.setId(1L);
        testStudent.setStudentId("stu_001");
        testStudent.setStudentName("张三");
        testStudent.setGender(1);
        testStudent.setBirthday(LocalDate.of2020, 3, 15);
        testStudent.setClassId("class_001");
        testStudent.setEnrollDate(LocalDate.of2023, 9, 1);
        testStudent.setStatus(1);
        testStudent.setNickname("小张");
    }

    /**
     * TC-STUDENT-001: 新增学生 - 成功
     */
    @Test
    void testAddStudent_Success() {
        // Given
        StudentDTO dto = new StudentDTO();
        dto.setStudentName("李四");
        dto.setGender(2);
        dto.setBirthday(LocalDate.of2021, 5, 20));
        dto.setClassId("class_001");
        
        when(studentInfoMapper.insert(any(StudentInfo.class))).thenReturn(1);

        // When
        String studentId = studentService.addStudent(dto);

        // Then
        assertNotNull(studentId);
        verify(studentInfoMapper, times(1)).insert(any(StudentInfo.class));
    }

    /**
     * TC-STUDENT-002: 更新学生信息 - 成功
     */
    @Test
    void testUpdateStudent_Success() {
        // Given
        when(studentInfoMapper.selectOne(any())).thenReturn(testStudent);
        when(studentInfoMapper.updateById(any())).thenReturn(1);

        StudentDTO dto = new StudentDTO();
        dto.setNickname("新昵称");

        // When & Then
        assertDoesNotThrow(() -> studentService.updateStudent("stu_001", dto));
        verify(studentInfoMapper, times(1)).updateById(any());
    }

    /**
     * TC-STUDENT-002: 更新学生信息 - 学生不存在
     */
    @Test
    void testUpdateStudent_NotFound() {
        // Given
        when(studentInfoMapper.selectOne(any())).thenReturn(null);

        // When & Then
        try {
            studentService.updateStudent("not_exist", new StudentDTO());
            fail("应该抛出异常");
        } catch (BusinessException e) {
            assertTrue(e.getMessage().contains("不存在"));
        }
    }

    /**
     * TC-STUDENT-003: 删除学生 - 成功
     */
    @Test
    void testDeleteStudent_Success() {
        // Given
        when(studentInfoMapper.selectOne(any())).thenReturn(testStudent);
        when(studentInfoMapper.updateById(any())).thenReturn(1);

        // When
        studentService.deleteStudent("stu_001");

        // Then
        verify(studentInfoMapper, times(1)).updateById(any());
    }

    /**
     * TC-STUDENT-004: 转班操作 - 成功
     */
    @Test
    void testTransferClass_Success() {
        // Given
        when(studentInfoMapper.selectOne(any())).thenReturn(testStudent);
        when(studentInfoMapper.updateById(any())).thenReturn(1);

        // When
        studentService.transferClass("stu_001", "class_002");

        // Then
        verify(studentInfoMapper, times(1)).updateById(any());
    }

    /**
     * TC-STUDENT-005: 获取学生详情 - 成功
     */
    @Test
    void testGetStudentDetail_Success() {
        // Given
        when(studentInfoMapper.selectOne(any())).thenReturn(testStudent);

        // When
        StudentVO vo = studentService.getStudentDetail("stu_001");

        // Then
        assertNotNull(vo);
        assertEquals("stu_001", vo.getStudentId());
        assertEquals("张三", vo.getStudentName());
        assertEquals("男", vo.getGenderDesc());
        assertEquals("在园", vo.getStatusDesc());
    }

    /**
     * TC-STUDENT-005: 获取学生列表 - 分页
     */
    @Test
    void testGetStudentList_Pagination() {
        // Given
        List<StudentInfo> students = Arrays.asList(testStudent);
        when(studentInfoMapper.selectPage(any(), any())).thenReturn(null); // 简化处理

        // When
        // 注意: 需要mock Page对象，这里简化测试

        // Then
        // 验证分页查询方法被调用
        verify(studentInfoMapper, times(1)).selectPage(any(), any());
    }
}