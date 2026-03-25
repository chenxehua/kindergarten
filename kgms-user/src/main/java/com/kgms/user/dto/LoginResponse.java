package com.kgms.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录响应
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    /** 访问Token */
    private String token;

    /** 刷新Token */
    private String refreshToken;

    /** 用户ID */
    private String userId;

    /** 用户类型 */
    private Integer userType;

    /** 昵称 */
    private String nickname;

    /** 头像 */
    private String avatar;
}
