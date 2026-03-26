package com.kgms.data.entity;

import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 活动记录实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ActivityRecord extends BaseEntity {

    private String recordId;
    private String activityId;

    // 记录内容
    private String summary;
    private String photos;
    private String videos;

    // 参与学生
    private String participantIds;
    private String absentIds;

    // 评价
    private String parentFeedback;

    private String createBy;
}
