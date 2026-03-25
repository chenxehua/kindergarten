package com.kgms.record.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 成长照片实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_growth_photo")
public class GrowthPhoto extends BaseEntity {
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
    /** 拍摄时间 */
    private LocalDateTime photoTime;
    /** 拍摄人 */
    private String takeBy;
    /** 水印URL */
    private String watermarkUrl;
    /** 标签 */
    private String tags;
    /** AI标签 */
    private String aiTags;
}
