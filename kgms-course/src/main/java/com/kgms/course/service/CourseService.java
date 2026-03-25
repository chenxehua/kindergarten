package com.kgms.course.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgms.common.exception.BusinessException;
import com.kgms.common.result.PageResult;
import com.kgms.common.util.IdGenerator;
import com.kgms.course.dto.CourseDTO;
import com.kgms.course.dto.CourseVO;
import com.kgms.course.dto.ScheduleDTO;
import com.kgms.course.dto.ScheduleVO;
import com.kgms.course.entity.Course;
import com.kgms.course.entity.ClassSchedule;
import com.kgms.course.mapper.CourseMapper;
import com.kgms.course.mapper.ClassScheduleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseMapper courseMapper;
    private final ClassScheduleMapper scheduleMapper;

    // ==================== 课程管理 ====================

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

    // ==================== 课表管理 ====================

    /**
     * 编排课表 - TC-COURSE-002
     */
    public String addSchedule(ScheduleDTO dto) {
        // 检查是否已存在
        ClassSchedule existing = scheduleMapper.selectOne(
                new LambdaQueryWrapper<ClassSchedule>()
                        .eq(ClassSchedule::getClassId, dto.getClassId())
                        .eq(ClassSchedule::getWeekDay, dto.getWeekDay())
                        .eq(ClassSchedule::getTimeSlot, dto.getTimeSlot())
        );

        if (existing != null) {
            throw new BusinessException(400, "该时段已有课程安排");
        }

        ClassSchedule schedule = new ClassSchedule();
        schedule.setScheduleId(IdGenerator.generateStrId());
        schedule.setClassId(dto.getClassId());
        schedule.setWeekDay(dto.getWeekDay());
        schedule.setTimeSlot(dto.getTimeSlot());
        schedule.setCourseId(dto.getCourseId());
        schedule.setTeacherId(dto.getTeacherId());
        schedule.setClassroom(dto.getClassroom());

        scheduleMapper.insert(schedule);
        return schedule.getScheduleId();
    }

    public void updateSchedule(String scheduleId, ScheduleDTO dto) {
        ClassSchedule schedule = getScheduleById(scheduleId);
        if (schedule == null) throw new BusinessException(404, "课表不存在");

        if (dto.getCourseId() != null) schedule.setCourseId(dto.getCourseId());
        if (dto.getTeacherId() != null) schedule.setTeacherId(dto.getTeacherId());
        if (dto.getClassroom() != null) schedule.setClassroom(dto.getClassroom());

        scheduleMapper.updateById(schedule);
    }

    /**
     * 获取班级课表
     */
    public List<ScheduleVO> getClassSchedule(String classId, Integer weekDay) {
        LambdaQueryWrapper<ClassSchedule> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ClassSchedule::getClassId, classId);
        if (weekDay != null) {
            wrapper.eq(ClassSchedule::getWeekDay, weekDay);
        }
        wrapper.orderByAsc(ClassSchedule::getWeekDay, ClassSchedule::getTimeSlot);

        return scheduleMapper.selectList(wrapper).stream()
                .map(this::convertScheduleToVO)
                .collect(Collectors.toList());
    }

    /**
     * 获取本周课表
     */
    public List<ScheduleVO> getThisWeekSchedule(String classId) {
        int currentWeekDay = LocalDate.now().getDayOfWeek().getValue(); // 1-7
        return getClassSchedule(classId, null);
    }

    public void deleteSchedule(String scheduleId) {
        ClassSchedule schedule = getScheduleById(scheduleId);
        if (schedule == null) throw new BusinessException(404, "课表不存在");
        scheduleMapper.deleteById(scheduleId);
    }

    private Course getCourseById(String courseId) {
        return courseMapper.selectOne(new LambdaQueryWrapper<Course>().eq(Course::getCourseId, courseId));
    }

    private ClassSchedule getScheduleById(String scheduleId) {
        return scheduleMapper.selectOne(
                new LambdaQueryWrapper<ClassSchedule>().eq(ClassSchedule::getScheduleId, scheduleId)
        );
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

    private ScheduleVO convertScheduleToVO(ClassSchedule schedule) {
        ScheduleVO vo = new ScheduleVO();
        vo.setScheduleId(schedule.getScheduleId());
        vo.setClassId(schedule.getClassId());
        vo.setWeekDay(schedule.getWeekDay());
        vo.setTimeSlot(schedule.getTimeSlot());
        vo.setCourseId(schedule.getCourseId());
        vo.setTeacherId(schedule.getTeacherId());
        vo.setClassroom(schedule.getClassroom());

        // 查询课程信息
        Course course = getCourseById(schedule.getCourseId());
        if (course != null) {
            vo.setCourseName(course.getCourseName());
            vo.setCourseType(course.getCourseType());
        }

        return vo;
    }
}