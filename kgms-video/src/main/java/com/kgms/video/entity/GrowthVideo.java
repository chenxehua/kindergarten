package com.kgms.video.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 成长视频实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_growth_video")
public class GrowthVideo extends BaseEntity {
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
    /** 模板ID */
    private String templateId;
    /** 生成状态 */
    private Integer genStatus;
    /** 生成进度 */
    private Integer genProgress;
    /** 错误信息 */
    private String errorMsg;
    /** 审核状态 */
    private Integer auditStatus;
    /** 审核时间 */
    private LocalDateTime auditTime;
    /** 浏览次数 */
    private Integer viewCount;
    /** 下载次数 */
    private Integer downloadCount;
}
