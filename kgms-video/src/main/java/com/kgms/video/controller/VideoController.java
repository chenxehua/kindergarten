package com.kgms.video.controller;
import com.kgms.common.result.Result;
import com.kgms.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/video")
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;
    @GetMapping("/list")
    public Result list(@RequestParam String studentId) {
        return Result.success(videoService.getVideoList(studentId));
    }
    @GetMapping("/detail")
    public Result detail(@RequestParam String videoId) {
        return Result.success(videoService.getVideoDetail(videoId));
    }
    @PostMapping("/generate")
    public Result generate(@RequestParam String studentId, @RequestParam String month, @RequestParam(required = false) String templateId) {
        return Result.success(videoService.generateVideo(studentId, month, templateId));
    }
}
