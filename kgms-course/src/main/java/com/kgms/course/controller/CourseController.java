package com.kgms.course.controller;

import com.kgms.course.dto.CourseDTO;
import com.kgms.course.dto.CourseVO;
import com.kgms.course.dto.NutritionAnalysisDTO;
import com.kgms.course.dto.ScheduleAdjustDTO;
import com.kgms.course.dto.ScheduleDTO;
import com.kgms.course.dto.ScheduleVO;
import com.kgms.course.entity.ScheduleAdjust;
import com.kgms.course.service.CourseService;
import com.kgms.course.service.NutritionService;
import com.kgms.course.service.ScheduleService;
import com.kgms.common.result.PageResult;
import com.kgms.common.result.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 课程管理Controller
 * 权限: 园长可管理, 家长/老师可查看
 */
@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final ScheduleService scheduleService;
    private final NutritionService nutritionService;

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

    // ==================== 调课管理 ====================

    /**
     * 申请调课
     */
    @PostMapping("/schedule/adjust")
    public Result<String> applyAdjust(@RequestBody ScheduleAdjustDTO dto) {
        return Result.success(scheduleService.applyAdjust(dto));
    }

    /**
     * 审批调课
     */
    @PostMapping("/schedule/adjust/{adjustId}/approve")
    public Result<Void> approveAdjust(
            @PathVariable String adjustId,
            @RequestParam String approveBy,
            @RequestParam boolean approved,
            @RequestParam(required = false) String remark) {
        scheduleService.approveAdjust(adjustId, approveBy, approved, remark);
        return Result.success();
    }

    /**
     * 取消调课
     */
    @PostMapping("/schedule/adjust/{adjustId}/cancel")
    public Result<Void> cancelAdjust(@PathVariable String adjustId) {
        scheduleService.cancelAdjust(adjustId);
        return Result.success();
    }

    /**
     * 获取班级调课记录
     */
    @GetMapping("/schedule/adjusts")
    public Result<List<ScheduleAdjust>> getClassAdjusts(@RequestParam String classId) {
        return Result.success(scheduleService.getClassAdjusts(classId));
    }

    /**
     * 获取待审批的调课申请
     */
    @GetMapping("/schedule/adjusts/pending")
    public Result<List<ScheduleAdjust>> getPendingAdjusts(@RequestParam String classId) {
        return Result.success(scheduleService.getPendingAdjusts(classId));
    }

    /**
     * 检查课程冲突
     */
    @GetMapping("/schedule/conflict")
    public Result<Boolean> checkConflict(
            @RequestParam String classId,
            @RequestParam String date,
            @RequestParam String timeSlot) {
        return Result.success(scheduleService.checkConflict(classId, date, timeSlot));
    }

    // ==================== 营养分析 ====================

    /**
     * 分析单日营养
     */
    @GetMapping("/nutrition/daily")
    public Result<NutritionAnalysisDTO> analyzeDailyNutrition(
            @RequestParam String recipeId,
            @RequestParam String date,
            @RequestParam(required = false) String mealType,
            @RequestParam List<String> foodNames) {
        return Result.success(nutritionService.analyzeDailyNutrition(recipeId, date, mealType, foodNames));
    }

    /**
     * 分析每周营养
     */
    @GetMapping("/nutrition/weekly")
    public Result<Map<String, Object>> analyzeWeeklyNutrition(
            @RequestParam String recipeId,
            @RequestParam List<String> dates) {
        return Result.success(nutritionService.analyzeWeeklyNutrition(recipeId, dates));
    }

    /**
     * 获取食材过敏原信息
     */
    @GetMapping("/nutrition/allergens")
    public Result<List<Map<String, Object>>> getFoodAllergens(@RequestParam List<String> foodNames) {
        return Result.success(nutritionService.getFoodAllergens(foodNames));
    }
}