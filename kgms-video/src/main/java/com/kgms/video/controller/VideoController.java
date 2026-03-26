package com.kgms.video.controller;

import com.kgms.video.dto.VideoTaskRequest;
import com.kgms.video.dto.VideoVO;
import com.kgms.video.entity.VideoTemplate;
import com.kgms.video.service.VideoService;
import com.kgms.video.service.VideoTemplateService;
import com.kgms.common.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 视频管理Controller
 * 权限: 家长可查看/下载自己孩子的视频, 园长可审核
 */
@RestController
@RequestMapping("/api/video")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;
    private final VideoTemplateService templateService;

    /**
     * 获取视频列表 - TC-VIDEO-002
     * 权限: 家长只能看自己孩子的视频
     */
    @GetMapping("/list")
    public Result<List<VideoVO>> list(
            @RequestParam String studentId,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Type", required = false) Integer userType) {

        // TODO: 权限检查 - 家长只能看自己孩子的视频
        // TODO: 老师可看班级学生视频, 园长可看全部
        return Result.success(videoService.getVideoList(studentId));
    }

    /**
     * 获取视频详情 - TC-VIDEO-003
     */
    @GetMapping("/detail")
    public Result<VideoVO> detail(@RequestParam String videoId) {
        return Result.success(videoService.getVideoDetail(videoId));
    }

    /**
     * 生成视频 - TC-VIDEO-001
     * 权限: 家长/老师可以手动触发生成
     */
    @PostMapping("/generate")
    public Result<String> generate(
            @RequestParam String studentId,
            @RequestParam String month,
            @RequestParam(required = false) String templateId) {

        // TODO: 检查权限
        return Result.success(videoService.generateVideo(studentId, month, templateId));
    }

    /**
     * 视频生成失败重新生成 - TC-VIDEO-06
     */
    @PostMapping("/regenerate")
    public Result<String> regenerate(@RequestParam String videoId) {
        return Result.success(videoService.regenerateVideo(videoId));
    }

    /**
     * 播放视频 - TC-VIDEO-003
     * 返回播放URL，增加播放计数
     */
    @GetMapping("/play")
    public Result<String> play(@RequestParam String videoId) {
        return Result.success(videoService.getVideoPlayUrl(videoId));
    }

    /**
     * 下载视频 - TC-VIDEO-004
     * 权限: 需要审核通过后才能下载
     */
    @GetMapping("/download")
    public Result<String> download(@RequestParam String videoId) {
        return Result.success(videoService.getVideoDownloadUrl(videoId));
    }

    /**
     * 分享视频 - TC-VIDEO-005
     * 生成小程序码或分享链接
     */
    @GetMapping("/share")
    public Result<String> share(@RequestParam String videoId) {
        // TODO: 生成分享小程序码
        return Result.success("https://miniprogram.example.com/video/" + videoId);
    }

    /**
     * 审核视频 - TC-REPORT-003
     * 权限: 园长
     */
    @PostMapping("/audit")
    public Result<Void> audit(
            @RequestParam String videoId,
            @RequestParam Boolean approved) {
        // TODO: 权限检查 - 园长才能审核
        videoService.auditVideo(videoId, approved);
        return Result.success();
    }

    // ==================== 视频模板管理 ====================

    /**
     * 获取所有视频模板
     */
    @GetMapping("/templates")
    public Result<List<VideoTemplate>> getAllTemplates() {
        return Result.success(templateService.getAllTemplates());
    }

    /**
     * 根据类型获取模板
     */
    @GetMapping("/templates/type")
    public Result<List<VideoTemplate>> getTemplatesByType(@RequestParam String templateType) {
        return Result.success(templateService.getTemplatesByType(templateType));
    }

    /**
     * 获取默认模板
     */
    @GetMapping("/templates/default")
    public Result<VideoTemplate> getDefaultTemplate() {
        return Result.success(templateService.getDefaultTemplate());
    }

    /**
     * 获取模板详情
     */
    @GetMapping("/templates/detail")
    public Result<VideoTemplate> getTemplateDetail(@RequestParam String templateId) {
        return Result.success(templateService.getTemplateDetail(templateId));
    }

    /**
     * 创建模板
     */
    @PostMapping("/templates")
    public Result<String> createTemplate(@RequestBody VideoTemplate template) {
        return Result.success(templateService.createTemplate(template));
    }

    /**
     * 更新模板
     */
    @PutMapping("/templates")
    public Result<Void> updateTemplate(@RequestBody VideoTemplate template) {
        templateService.updateTemplate(template);
        return Result.success();
    }

    /**
     * 删除模板
     */
    @DeleteMapping("/templates")
    public Result<Void> deleteTemplate(@RequestParam String templateId) {
        templateService.deleteTemplate(templateId);
        return Result.success();
    }

    /**
     * 设置默认模板
     */
    @PostMapping("/templates/set-default")
    public Result<Void> setDefaultTemplate(@RequestParam String templateId) {
        templateService.setDefaultTemplate(templateId);
        return Result.success();
    }

    /**
     * 智能选片
     */
    @GetMapping("/smart-select")
    public Result<List<String>> smartSelectPhotos(
            @RequestParam String studentId,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(defaultValue = "30") int maxCount) {
        return Result.success(templateService.smartSelectPhotos(studentId, startDate, endDate, maxCount));
    }
}