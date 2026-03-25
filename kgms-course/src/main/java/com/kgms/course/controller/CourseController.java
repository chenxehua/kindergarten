package com.kgms.course.controller;

import com.kgms.course.dto.CourseDTO;
import com.kgms.course.dto.CourseVO;
import com.kgms.course.dto.ScheduleDTO;
import com.kgms.course.dto.ScheduleVO;
import com.kgms.course.service.CourseService;
import com.kgms.common.result.PageResult;
import com.kgms.common.result.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程管理Controller
 * 权限: 园长可管理, 家长/老师可查看
 */
@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    /**
     * 新增课程 - TC-COURSE-001
     * 权限: 园长
     */
    @PostMapping("/add")
    public Result<String> addCourse(@RequestBody @Valid CourseDTO dto) {
        // TODO: 权限检查 - 只有园长能新增课程
        return Result.success(courseService.addCourse(dto));
    }

    /**
     * 更新课程 - TC-COURSE-001
     */
    @PutMapping("/update")
    public Result<Void> updateCourse(@RequestParam String courseId, @RequestBody @Valid CourseDTO dto) {
        courseService.updateCourse(courseId, dto);
        return Result.success();
    }

    /**
     * 获取课程详情
     */
    @GetMapping("/detail")
    public Result<CourseVO> getCourseDetail(@RequestParam String courseId) {
        return Result.success(courseService.getCourseDetail(courseId));
    }

    /**
     * 获取课程列表 - TC-COURSE-001
     */
    @GetMapping("/list")
    public Result<PageResult<CourseVO>> getCourseList(
            @RequestParam(required = false) String kgId,
            @RequestParam(required = false) String courseType,
            @RequestParam(required = false) String ageGroup,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize) {
        return Result.success(courseService.getCourseList(kgId, courseType, ageGroup, page, pageSize));
    }

    /**
     * 编排班级课表 - TC-COURSE-002
     * 权限: 园长/老师
     */
    @PostMapping("/schedule/add")
    public Result<String> addSchedule(@RequestBody @Valid ScheduleDTO dto) {
        return Result.success(courseService.addSchedule(dto));
    }

    /**
     * 更新课表
     */
    @PutMapping("/schedule/update")
    public Result<Void> updateSchedule(@RequestParam String scheduleId, @RequestBody ScheduleDTO dto) {
        courseService.updateSchedule(scheduleId, dto);
        return Result.success();
    }

    /**
     * 获取班级课表 - TC-COURSE-003
     */
    @GetMapping("/schedule/class")
    public Result<List<ScheduleVO>> getClassSchedule(
            @RequestParam String classId,
            @RequestParam(required = false) Integer weekDay) {
        return Result.success(courseService.getClassSchedule(classId, weekDay));
    }

    /**
     * 获取班级本周课表
     */
    @GetMapping("/schedule/thisWeek")
    public Result<List<ScheduleVO>> getThisWeekSchedule(@RequestParam String classId) {
        return Result.success(courseService.getThisWeekSchedule(classId));
    }

    /**
     * 删除课表
     */
    @DeleteMapping("/schedule/delete")
    public Result<Void> deleteSchedule(@RequestParam String scheduleId) {
        courseService.deleteSchedule(scheduleId);
        return Result.success();
    }
}