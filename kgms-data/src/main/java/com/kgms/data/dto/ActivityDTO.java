package com.kgms.data.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 活动详情DTO
 */
@Data
public class ActivityDTO {

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
    private List<String> targetIds;
    private String targetNames;
    private Integer maxParticipants;

    // 费用
    private Double fee;
    private String feeDescription;

    // 报名信息
    private Integer requireSignup;
    private Date signupDeadline;
    private Integer signupCount;
    private Integer approvedCount;

    // 状态
    private String status;
    private Date publishTime;

    // 负责人
    private String principalId;
    private String principalName;
    private String createBy;
    private String createByName;

    // 报名列表
    private List<Map<String, Object>> signupList;
}
