package com.kgms.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_sys_user")
public class SysUser extends BaseEntity {

    /** 用户ID */
    private String userId;

    /** 用户类型: 1-家长 2-老师 3-园长 4-管理员 */
    private Integer userType;

    /** 用户名(手机号) */
    private String username;

    /** 密码 */
    private String password;

    /** 昵称 */
    private String nickname;

    /** 头像URL */
    private String avatar;

    /** 手机号 */
    private String phone;

    /** 微信OpenID */
    private String wechatOpenid;

    /** 微信UnionID */
    private String wechatUnionid;

    /** 状态: 0-禁用 1-正常 */
    private Integer status;

    /** 最后登录时间 */
    private java.time.LocalDateTime lastLoginTime;
}
