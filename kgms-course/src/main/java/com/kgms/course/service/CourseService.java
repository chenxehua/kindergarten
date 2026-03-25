package com.kgms.course.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgms.common.exception.BusinessException;
import com.kgms.common.result.PageResult;
import com.kgms.common.util.IdGenerator;
import com.kgms.course.dto.CourseDTO;
import com.kgms.course.dto.CourseVO;
import com.kgms.course.entity.Course;
import com.kgms.course.mapper.CourseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseMapper courseMapper;

    public String addCourse(CourseDTO dto) {
        Course course = new Course();
        course.setCourseId(IdGenerator.generateStrId());
        course.setKgId(dto.getKgId());
        course.setCourseName(dto.getCourseName());
        course.setCourseType(dto.getCourseType());
        course.setAgeGroup(dto.getAgeGroup());
        course.setTeacherId(dto.getTeacherId());
        course.setCourseDesc(dto.getCourseDesc());
        course.setCourseGoal(dto.getCourseGoal());
        course.setDuration(dto.getDuration());
        course.setStatus(1);
        courseMapper.insert(course);
        return course.getCourseId();
    }

    public void updateCourse(String courseId, CourseDTO dto) {
        Course course = getCourseById(courseId);
        if (course == null) throw new BusinessException(404, "课程不存在");
        if (StringUtils.hasText(dto.getCourseName())) course.setCourseName(dto.getCourseName());
        if (StringUtils.hasText(dto.getCourseType())) course.setCourseType(dto.getCourseType());
        if (StringUtils.hasText(dto.getAgeGroup())) course.setAgeGroup(dto.getAgeGroup());
        if (StringUtils.hasText(dto.getTeacherId())) course.setTeacherId(dto.getTeacherId());
        if (dto.getDuration() != null) course.setDuration(dto.getDuration());
        courseMapper.updateById(course);
    }

    public CourseVO getCourseDetail(String courseId) {
        Course course = getCourseById(courseId);
        if (course == null) throw new BusinessException(404, "课程不存在");
        return convertToVO(course);
    }

    public PageResult<CourseVO> getCourseList(String kgId, String courseType, String ageGroup, Integer page, Integer pageSize) {
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(kgId)) wrapper.eq(Course::getKgId, kgId);
        if (StringUtils.hasText(courseType)) wrapper.eq(Course::getCourseType, courseType);
        if (StringUtils.hasText(ageGroup)) wrapper.eq(Course::getAgeGroup, ageGroup);
        wrapper.eq(Course::getStatus, 1);
        wrapper.orderByDesc(Course::getCreateTime);
        IPage<Course> pageResult = courseMapper.selectPage(new Page<>(page, pageSize), wrapper);
        return PageResult.of(pageResult.getRecords().stream().map(this::convertToVO).collect(Collectors.toList()),
                pageResult.getTotal(), page, pageSize);
    }

    private Course getCourseById(String courseId) {
        return courseMapper.selectOne(new LambdaQueryWrapper<Course>().eq(Course::getCourseId, courseId));
    }

    private CourseVO convertToVO(Course course) {
        CourseVO vo = new CourseVO();
        vo.setCourseId(course.getCourseId());
        vo.setKgId(course.getKgId());
        vo.setCourseName(course.getCourseName());
        vo.setCourseType(course.getCourseType());
        vo.setAgeGroup(course.getAgeGroup());
        vo.setTeacherId(course.getTeacherId());
        vo.setCourseDesc(course.getCourseDesc());
        vo.setCourseGoal(course.getCourseGoal());
        vo.setDuration(course.getDuration());
        return vo;
    }
}
