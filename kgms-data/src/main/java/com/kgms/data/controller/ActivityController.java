package com.kgms.data.controller;

import com.kgms.common.result.Result;
import com.kgms.data.dto.ActivityDTO;
import com.kgms.data.entity.Activity;
import com.kgms.data.entity.ActivitySignup;
import com.kgms.data.service.ActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/activities")
@Slf4j
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    /**
     * 创建活动
     */
    @PostMapping
    public Result<Activity> createActivity(@RequestBody ActivityDTO dto) {
        return Result.success(activityService.createActivity(dto));
    }

    /**
     * 发布活动
     */
    @PostMapping("/{activityId}/publish")
    public Result<Activity> publishActivity(@PathVariable String activityId) {
        return Result.success(activityService.publishActivity(activityId));
    }

    /**
     * 取消活动
     */
    @PostMapping("/{activityId}/cancel")
    public Result<Activity> cancelActivity(@PathVariable String activityId) {
        return Result.success(activityService.cancelActivity(activityId));
    }

    /**
     * 获取活动详情
     */
    @GetMapping("/{activityId}")
    public Result<ActivityDTO> getActivityDetail(@PathVariable String activityId) {
        return Result.success(activityService.getActivityDetail(activityId));
    }

    /**
     * 获取活动列表
     */
    @GetMapping
    public Result<List<ActivityDTO>> getActivityList(
            @RequestParam String kgId,
            @RequestParam(required = false) String activityType,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        return Result.success(activityService.getActivityList(kgId, activityType, status, startDate, endDate));
    }

    /**
     * 报名参加活动
     */
    @PostMapping("/{activityId}/signup")
    public Result<ActivitySignup> signUp(
            @PathVariable String activityId,
            @RequestParam String studentId,
            @RequestParam String parentId,
            @RequestParam(required = false) String remark) {
        return Result.success(activityService.signUp(activityId, studentId, parentId, remark));
    }

    /**
     * 审批报名
     */
    @PostMapping("/signup/{signupId}/approve")
    public Result<ActivitySignup> approveSignup(
            @PathVariable String signupId,
            @RequestParam String approveBy,
            @RequestParam boolean approved,
            @RequestParam(required = false) String remark) {
        return Result.success(activityService.approveSignup(signupId, approveBy, approved, remark));
    }

    /**
     * 取消报名
     */
    @DeleteMapping("/signup/{signupId}")
    public Result<Void> cancelSignup(@PathVariable String signupId) {
        activityService.cancelSignup(signupId);
        return Result.success();
    }
}
