package com.kgms.record.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_growth_photo")
public class GrowthPhoto extends BaseEntity {

    private String photoId;
    private String studentId;
    private String recordId;
    private String photoType;
    private String photoUrl;
    private String thumbnailUrl;
    private LocalDateTime photoTime;
    private String takeBy;
    private String watermarkUrl;
    private String tags;
    private String aiTags;
}
