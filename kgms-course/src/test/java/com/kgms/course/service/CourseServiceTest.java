package com.kgms.course.service;

import com.kgms.course.dto.CourseDTO;
import com.kgms.course.dto.CourseVO;
import com.kgms.course.entity.Course;
import com.kgms.course.mapper.CourseMapper;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseMapper courseMapper;

    @InjectMocks
    private CourseService courseService;

    private Course testCourse;

    @BeforeEach
    void setUp() {
        testCourse = new Course();
        testCourse.setId(1L);
        testCourse.setCourseId("course_001");
        testCourse.setCourseName("音乐课");
        testCourse.setCourseType(1);
        testCourse.setTeacherId("teacher_001");
        testCourse.setDuration(30);
        testCourse.setMaxStudents(20);
        testCourse.setStatus(1);
    }

    /**
     * TC-COURSE-001: 新增课程
     */
    @Test
    void testAddCourse_Success() {
        // Given
        CourseDTO dto = new CourseDTO();
        dto.setCourseName("美术课");
        dto.setCourseType(2);
        dto.setTeacherId("teacher_002");
        dto.setDuration(45);
        
        when(courseMapper.insert(any(Course.class))).thenReturn(1);

        // When
        String courseId = courseService.addCourse(dto);

        // Then
        assertNotNull(courseId);
        verify(courseMapper, times(1)).insert(any(Course.class));
    }

    /**
     * TC-COURSE-002: 更新课程
     */
    @Test
    void testUpdateCourse_Success() {
        // Given
        when(courseMapper.selectOne(any())).thenReturn(testCourse);
        when(courseMapper.updateById(any())).thenReturn(1);

        CourseDTO dto = new CourseDTO();
        dto.setCourseName("新的课程名");

        // When
        courseService.updateCourse("course_001", dto);

        // Then
        verify(courseMapper, times(1)).updateById(any());
    }

    /**
     * TC-COURSE-003: 获取课程列表
     */
    @Test
    void testGetCourseList() {
        // Given
        List<Course> courses = Arrays.asList(testCourse);
        when(courseMapper.selectList(any())).thenReturn(courses);

        // When
        List<CourseVO> result = courseService.getCourseList();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("音乐课", result.get(0).getCourseName());
    }

    /**
     * TC-COURSE-004: 获取课程详情
     */
    @Test
    void testGetCourseDetail_Success() {
        // Given
        when(courseMapper.selectOne(any())).thenReturn(testCourse);

        // When
        CourseVO vo = courseService.getCourseDetail("course_001");

        // Then
        assertNotNull(vo);
        assertEquals("course_001", vo.getCourseId());
        assertEquals("音乐课", vo.getCourseName());
    }
}
