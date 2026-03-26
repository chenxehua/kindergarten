package com.kgms.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 权限实体
 */
@Data
@TableName("t_permission")
public class Permission {

    @TableId(type = IdType.ASSIGN_UUID)
    private String permissionId;

    private String permissionName;

    private String permissionCode;

    private String permissionType;

    private String path;

    private String component;

    private String icon;

    private Integer sort;

    private String status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}