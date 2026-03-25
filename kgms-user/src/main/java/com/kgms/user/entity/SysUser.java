package com.kgms.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 系统用户实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_sys_user")
public class SysUser extends BaseEntity {

    /** 用户ID */
    private String userId;
    /** 用户类型 */
    private Integer userType;
    /** 用户名 */
    private String username;
    /** 密码 */
    private String password;
    /** 昵称 */
    private String nickname;
    /** 头像 */
    private String avatar;
    /** 手机号 */
    private String phone;
    /** 微信OpenID */
    private String wechatOpenid;
    /** 微信UnionID */
    private String wechatUnionid;
    /** 状态 */
    private Integer status;
    /** 最后登录时间 */
    private LocalDateTime lastLoginTime;
}
