package com.kgms.data.entity;

import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 活动实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Activity extends BaseEntity {

    private String activityId;
    private String kgId;
    private String activityName;
    private String activityType;

    // 活动信息
    private Date activityTime;
    private Date endTime;
    private String location;
    private String description;
    private String process;

    // 参与对象
    private String targetType;
    private String targetIds;
    private Integer maxParticipants;

    // 费用
    private BigDecimal fee;
    private String feeDescription;

    // 报名信息
    private Integer requireSignup;
    private Date signupDeadline;

    // 状态
    private String status;
    private Date publishTime;

    // 负责人
    private String principalId;
    private String createBy;
}
