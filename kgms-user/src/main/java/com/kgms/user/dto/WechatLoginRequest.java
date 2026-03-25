package com.kgms.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 微信登录请求
 */
@Data
public class WechatLoginRequest {

    /** 微信授权code */
    @NotBlank(message = "微信code不能为空")
    private String code;

    /** 用户信息(可选) */
    private String nickname;

    /** 头像URL(可选) */
    private String avatar;

    /** 用户类型: 1-家长 2-老师 */
    private Integer userType;
}
