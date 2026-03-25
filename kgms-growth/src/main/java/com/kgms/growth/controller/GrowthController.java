package com.kgms.growth.controller;

import com.kgms.common.result.Result;
import com.kgms.growth.service.GrowthProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/growth")
@RequiredArgsConstructor
public class GrowthController {
    private final GrowthProfileService growthProfileService;

    @GetMapping("/profile")
    public Result getProfile(@RequestParam String studentId, @RequestParam(required = false) String month) {
        return Result.success(growthProfileService.getProfile(studentId, month));
    }

    @PostMapping("/profile/generate")
    public Result generateProfile(@RequestParam String studentId, @RequestParam String month) {
        return Result.success(growthProfileService.generateProfile(studentId, month));
    }

    @GetMapping("/report/monthly")
    public Result getMonthlyReport(@RequestParam String studentId, @RequestParam String month) {
        return Result.success(growthProfileService.getMonthlyReport(studentId, month));
    }

    @PostMapping("/report/generate")
    public Result generateMonthlyReport(@RequestParam String studentId, @RequestParam String month) {
        return Result.success(growthProfileService.generateMonthlyReport(studentId, month));
    }
}
