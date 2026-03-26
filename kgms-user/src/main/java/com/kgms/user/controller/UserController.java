package com.kgms.user.controller;

import com.kgms.common.result.Result;
import com.kgms.user.dto.*;
import com.kgms.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户Controller
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 账号密码登录
     */
    @PostMapping("/login")
    public Result<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        LoginResponse response = userService.loginByPassword(request.getUsername(), request.getPassword());
        return Result.success(response);
    }

    /**
     * 微信授权登录
     */
    @PostMapping("/login/wechat")
    public Result<LoginResponse> loginByWechat(@RequestBody @Valid WechatLoginRequest request) {
        LoginResponse response = userService.loginByWechat(
                request.getCode(),
                request.getNickname(),
                request.getAvatar(),
                request.getUserType()
        );
        return Result.success(response);
    }

    /**
     * 手机验证码登录
     */
    @PostMapping("/login/sms")
    public Result<LoginResponse> loginBySms(@RequestBody @Valid SmsLoginRequest request) {
        LoginResponse response = userService.loginBySms(request.getPhone(), request.getCode());
        return Result.success(response);
    }

    /**
     * 获取当前用户信息
     */
    @GetMapping("/info")
    public Result<UserInfoVO> getUserInfo(@RequestHeader("X-User-Id") String userId) {
        UserInfoVO userInfo = userService.getUserInfo(userId);
        return Result.success(userInfo);
    }

    /**
     * 登出
     */
    @PostMapping("/logout")
    public Result<Void> logout() {
        // TODO: 处理登出逻辑（如移除Redis中的Token）
        return Result.success();
    }

    /**
     * 家长获取孩子列表
     */
    @GetMapping("/child/list")
    public Result<List<ChildVO>> getChildList(@RequestHeader("X-User-Id") String userId) {
        List<ChildVO> children = userService.getChildList(userId);
        return Result.success(children);
    }

    /**
     * 家长查看孩子详情
     */
    @GetMapping("/child/detail")
    public Result<ChildDetailVO> getChildDetail(
            @RequestParam String studentId,
            @RequestHeader("X-User-Id") String userId) {
        ChildDetailVO detail = userService.getChildDetail(studentId, userId);
        return Result.success(detail);
    }
}
