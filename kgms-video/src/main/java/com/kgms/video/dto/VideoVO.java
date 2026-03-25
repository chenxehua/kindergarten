package com.kgms.video.dto;
import lombok.Data;

/**
 * 成长视频VO
 */
@Data
public class VideoVO {
    /** 视频ID */
    private String videoId;
    /** 学生ID */
    private String studentId;
    /** 视频类型 */
    private String videoType;
    /** 视频月份 */
    private String videoMonth;
    /** 视频URL */
    private String videoUrl;
    /** 封面URL */
    private String coverUrl;
    /** 时长（秒） */
    private Integer duration;
    /** 生成状态 */
    private Integer genStatus;
    /** 审核状态 */
    private Integer auditStatus;
}
