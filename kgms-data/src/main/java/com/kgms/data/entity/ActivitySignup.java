package com.kgms.data.entity;

import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 活动报名实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ActivitySignup extends BaseEntity {

    private String signupId;
    private String activityId;
    private String studentId;
    private String parentId;

    // 报名信息
    private String status;
    private String remark;

    // 审批信息
    private String approveBy;
    private Date approveTime;
    private String approveRemark;
}
