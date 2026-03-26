package com.kgms.video.dto;

import lombok.Data;

import java.util.List;

/**
 * 视频生成请求DTO
 */
@Data
public class VideoTaskRequest {

    private String studentId;
    private String videoType; // MONTHLY/REVIEW/HIGHLIGHT
    private String startDate;
    private String endDate;
    private String templateId;
    private String musicId;
    private String customText;
    private Integer duration; // 视频时长
    private List<String> photoIds; // 指定照片ID（可选）
}
