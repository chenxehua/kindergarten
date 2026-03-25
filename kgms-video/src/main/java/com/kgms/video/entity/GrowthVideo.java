package com.kgms.video.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_growth_video")
public class GrowthVideo extends BaseEntity {
    private String videoId;
    private String studentId;
    private String videoType;
    private String videoMonth;
    private String videoUrl;
    private String coverUrl;
    private Integer duration;
    private String templateId;
    private Integer genStatus;
    private Integer genProgress;
    private String errorMsg;
    private Integer auditStatus;
    private LocalDateTime auditTime;
    private Integer viewCount;
    private Integer downloadCount;
}
