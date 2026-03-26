package com.kgms.data.controller;

import com.kgms.common.result.Result;
import com.kgms.data.dto.ClassDashboardDTO;
import com.kgms.data.dto.GrowthDashboardDTO;
import com.kgms.data.dto.SchoolDashboardDTO;
import com.kgms.data.entity.DashboardSnapshot;
import com.kgms.data.service.DashboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dashboard")
@Slf4j
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    /**
     * 获取班级看板数据
     */
    @GetMapping("/class")
    public Result<ClassDashboardDTO> getClassDashboard(@RequestParam String classId) {
        return Result.success(dashboardService.getClassDashboard(classId));
    }

    /**
     * 获取园所看板数据
     */
    @GetMapping("/school")
    public Result<SchoolDashboardDTO> getSchoolDashboard(@RequestParam String kgId) {
        return Result.success(dashboardService.getSchoolDashboard(kgId));
    }

    /**
     * 获取成长看板数据
     */
    @GetMapping("/growth")
    public Result<GrowthDashboardDTO> getGrowthDashboard(@RequestParam String kgId) {
        return Result.success(dashboardService.getGrowthDashboard(kgId));
    }

    /**
     * 生成每日数据快照（定时任务调用）
     */
    @PostMapping("/generate-snapshot")
    public Result<Void> generateDailySnapshot(@RequestParam String kgId) {
        dashboardService.generateDailySnapshot(kgId);
        return Result.success();
    }

    /**
     * 获取历史快照数据
     */
    @GetMapping("/snapshot/history")
    public Result<List<DashboardSnapshot>> getSnapshotHistory(
            @RequestParam String kgId,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(required = false) String snapshotType) {
        return Result.success(dashboardService.getSnapshotHistory(kgId, startDate, endDate, snapshotType));
    }
}
