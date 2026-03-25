package com.kgms.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 手机验证码登录请求
 */
@Data
public class SmsLoginRequest {

    /** 手机号 */
    @NotBlank(message = "手机号不能为空")
    private String phone;

    /** 验证码 */
    @NotBlank(message = "验证码不能为空")
    private String code;
}
