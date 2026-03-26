package com.kgms.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 操作日志实体
 */
@Data
@TableName("t_operation_log")
public class OperationLog {

    @TableId(type = IdType.ASSIGN_UUID)
    private String logId;

    private String userId;

    private String username;

    private String operation;

    private String method;

    private String params;

    private String result;

    private String ip;

    private LocalDateTime operateTime;

    private Long duration;
}