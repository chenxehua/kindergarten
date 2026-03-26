package com.kgms.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 角色实体
 */
@Data
@TableName("t_role")
public class Role {

    @TableId(type = IdType.ASSIGN_UUID)
    private String roleId;

    private String roleName;

    private String roleCode;

    private String description;

    private Boolean isSystem;

    private Integer sort;

    private String status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}