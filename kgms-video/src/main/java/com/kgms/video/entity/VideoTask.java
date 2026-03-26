package com.kgms.video.entity;

import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 视频生成任务实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class VideoTask extends BaseEntity {

    private String taskId;
    private String videoId;
    private String studentId;

    // 生成参数
    private String videoType;
    private String startDate;
    private String endDate;
    private String templateId;
    private String musicId;
    private String customText;

    // 生成状态
    private String status;
    private Integer progress;
    private String errorMsg;

    // 智能选片
    private String selectedPhotos;
    private Integer photoCount;
}
