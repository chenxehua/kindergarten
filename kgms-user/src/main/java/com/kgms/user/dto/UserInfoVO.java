package com.kgms.user.dto;

import lombok.Data;

/**
 * 用户信息VO
 */
@Data
public class UserInfoVO {

    /** 用户ID */
    private String userId;

    /** 用户类型 */
    private Integer userType;

    /** 用户类型描述 */
    private String userTypeDesc;

    /** 昵称 */
    private String nickname;

    /** 头像 */
    private String avatar;

    /** 手机号 */
    private String phone;
}
