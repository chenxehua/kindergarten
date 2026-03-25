package com.kgms.record.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 成长照片VO
 */
@Data
public class PhotoVO {

    /** 照片ID */
    private String photoId;
    /** 学生ID */
    private String studentId;
    /** 记录ID */
    private String recordId;
    /** 照片类型 */
    private String photoType;
    /** 照片URL */
    private String photoUrl;
    /** 缩略图URL */
    private String thumbnailUrl;
    /** 水印URL */
    private String watermarkUrl;

    /** 拍摄时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime photoTime;

    /** 标签 */
    private String tags;
    /** AI标签 */
    private String aiTags;
}
