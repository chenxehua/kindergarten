package com.kgms.video.controller;

import com.kgms.video.dto.VideoVO;
import com.kgms.video.service.VideoService;
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
}