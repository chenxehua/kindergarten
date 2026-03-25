package com.kgms.notice.controller;
import com.kgms.common.result.Result;
import com.kgms.notice.dto.NoticeDTO;
import com.kgms.notice.dto.NoticeVO;
import com.kgms.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/notice")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;
    @PostMapping("/publish")
    public Result publish(@RequestBody NoticeDTO dto, @RequestHeader("X-User-Id") String userId) {
        return Result.success(noticeService.publishNotice(dto, userId));
    }
    @GetMapping("/list")
    public Result list(@RequestParam String kgId, @RequestParam(required = false) String noticeType) {
        return Result.success(noticeService.getNoticeList(kgId, noticeType));
    }
    @GetMapping("/detail")
    public Result detail(@RequestParam String noticeId) {
        return Result.success(noticeService.getNoticeDetail(noticeId));
    }
    @PostMapping("/withdraw")
    public Result withdraw(@RequestParam String noticeId) {
        noticeService.withdrawNotice(noticeId);
        return Result.success();
    }
}
