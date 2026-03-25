package com.kgms.video.dto;

import lombok.Data;

@Data
public class VideoGenerateRequest {

    /** 学生ID */
    private String studentId;

    /** 视频月份 */
    private String month;

    /** 视频类型: monthly/semester/year/activity */
    private String videoType;

    /** 模板ID */
    private String templateId;

    /** 是否需要AI配音 */
    private Boolean needAiDubbing;

    /** 背景音乐ID */
    private String musicId;
}
