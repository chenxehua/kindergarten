package com.kgms.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_sys_user")
public class SysUser extends BaseEntity {

    private String userId;
    private Integer userType;
    private String username;
    private String password;
    private String nickname;
    private String avatar;
    private String phone;
    private String wechatOpenid;
    private String wechatUnionid;
    private Integer status;
    private LocalDateTime lastLoginTime;
}
