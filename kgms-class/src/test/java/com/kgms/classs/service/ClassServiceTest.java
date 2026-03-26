package com.kgms.classs.service;

import com.kgms.classs.dto.ClassDTO;
import com.kgms.classs.dto.ClassVO;
import com.kgms.classs.entity.ClassInfo;
import com.kgms.classs.mapper.ClassInfoMapper;
import com.kgms.common.exception.BusinessException;
import com.kgms.common.result.PageResult;
import com.kgms.student.dto.StudentVO;
import com.kgms.student.service.StudentService;
import com.kgms.user.entity.TeacherInfo;
import com.kgms.user.mapper.TeacherInfoMapper;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClassServiceTest {

    @Mock
    private ClassInfoMapper classInfoMapper;

    @Mock
    private StudentService studentService;

    @Mock
    private TeacherInfoMapper teacherInfoMapper;

    @InjectMocks
    private ClassService classService;

    private ClassInfo testClass;

    @BeforeEach
    void setUp() {
        testClass = new ClassInfo();
        testClass.setId(1L);
        testClass.setClassId("class_001");
        testClass.setKgId("kg_001");
        testClass.setClassName("星星班");
        testClass.setGrade("小班");
        testClass.setHeadTeacherId("teacher_001");
        testClass.setCapacity(30);
        testClass.setStudentCount(20);
        testClass.setStatus(1);
    }

    /**
     * TC-CLASS-001: 新增班级 - 成功
     */
    @Test
    void testAddClass_Success() {
        // Given
        ClassDTO dto = new ClassDTO();
        dto.setKgId("kg_001");
        dto.setClassName("星星班");
        dto.setGrade("小班");
        dto.setHeadTeacherId("teacher_001");
        dto.setCapacity(30);

        when(classInfoMapper.insert(any(ClassInfo.class))).thenReturn(1);

        // When
        String classId = classService.addClass(dto);

        // Then
        assertNotNull(classId);
        verify(classInfoMapper, times(1)).insert(any(ClassInfo.class));
    }

    /**
     * TC-CLASS-002: 更新班级信息 - 成功
     */
    @Test
    void testUpdateClass_Success() {
        // Given
        when(classInfoMapper.selectOne(any())).thenReturn(testClass);
        when(classInfoMapper.updateById(any())).thenReturn(1);

        ClassDTO dto = new ClassDTO();
        dto.setClassName("月亮班");

        // When & Then
        assertDoesNotThrow(() -> classService.updateClass("class_001", dto));
        verify(classInfoMapper, times(1)).updateById(any(ClassInfo.class));
    }

    /**
     * TC-CLASS-002: 更新班级信息 - 班级不存在
     */
    @Test
    void testUpdateClass_NotFound() {
        // Given
        when(classInfoMapper.selectOne(any())).thenReturn(null);

        ClassDTO dto = new ClassDTO();
        dto.setClassName("月亮班");

        // When & Then
        BusinessException exception = assertThrows(
            BusinessException.class,
            () -> classService.updateClass("not_exist", dto)
        );
        assertEquals(404, exception.getCode());
        assertTrue(exception.getMessage().contains("不存在"));
    }

    /**
     * TC-CLASS-003: 获取班级详情 - 成功
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
        assertEquals("星星班", vo.getClassName());
        assertEquals("小班", vo.getGrade());
    }

    /**
     * TC-CLASS-003: 获取班级详情 - 班级不存在
     */
    @Test
    void testGetClassDetail_NotFound() {
        // Given
        when(classInfoMapper.selectOne(any())).thenReturn(null);

        // When & Then
        BusinessException exception = assertThrows(
            BusinessException.class,
            () -> classService.getClassDetail("not_exist")
        );
        assertEquals(404, exception.getCode());
    }

    /**
     * TC-CLASS-004: 获取班级列表 - 分页查询
     */
    @Test
    void testGetClassList_Pagination() {
        // Given
        com.baomidou.mybatisplus.core.metadata.IPage<ClassInfo> page = 
            new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(1, 10);
        page.setRecords(Arrays.asList(testClass));
        page.setTotal(1);

        when(classInfoMapper.selectPage(any(), any())).thenReturn(page);

        // When
        PageResult result = classService.getClassList("kg_001", null, 1, 10);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getTotal());
        assertEquals(1, result.getRecords().size());
    }

    /**
     * TC-CLASS-005: 获取幼儿园所有班级
     */
    @Test
    void testGetClassesByKgId() {
        // Given
        when(classInfoMapper.selectList(any())).thenReturn(Arrays.asList(testClass));

        // When
        List<ClassVO> list = classService.getClassesByKgId("kg_001");

        // Then
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("星星班", list.get(0).getClassName());
    }

    /**
     * TC-CLASS-006: 获取班级学生列表 - 成功
     */
    @Test
    void testGetClassStudents_Success() {
        // Given
        StudentVO student = new StudentVO();
        student.setStudentId("stu_001");
        student.setStudentName("张三");

        when(studentService.getStudentsByClassId("class_001")).thenReturn(Arrays.asList(student));

        // When
        List<StudentVO> students = classService.getClassStudents("class_001");

        // Then
        assertNotNull(students);
        assertEquals(1, students.size());
        assertEquals("stu_001", students.get(0).getStudentId());
        verify(studentService, times(1)).getStudentsByClassId("class_001");
    }

    /**
     * TC-CLASS-007: 获取班级老师列表 - 成功
     */
    @Test
    void testGetClassTeachers_Success() {
        // Given
        TeacherInfo teacher = new TeacherInfo();
        teacher.setTeacherId("teacher_001");
        teacher.setTeacherName("李老师");

        when(teacherInfoMapper.selectByClassId("class_001")).thenReturn(Arrays.asList(teacher));

        // When
        List<TeacherInfo> teachers = classService.getClassTeachers("class_001");

        // Then
        assertNotNull(teachers);
        assertEquals(1, teachers.size());
        assertEquals("teacher_001", teachers.get(0).getTeacherId());
        verify(teacherInfoMapper, times(1)).selectByClassId("class_001");
    }
}
