package com.kgms.video.dto;
import lombok.Data;
@Data
public class VideoVO {
    private String videoId;
    private String studentId;
    private String videoType;
    private String videoMonth;
    private String videoUrl;
    private String coverUrl;
    private Integer duration;
    private Integer genStatus;
    private Integer auditStatus;
}
