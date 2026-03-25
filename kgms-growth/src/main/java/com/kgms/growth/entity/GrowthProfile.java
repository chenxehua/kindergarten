package com.kgms.growth.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_growth_profile")
public class GrowthProfile extends BaseEntity {
    private String profileId;
    private String studentId;
    private String profileMonth;
    private Integer emotionScore;
    private String emotionDetail;
    private Integer socialScore;
    private String socialDetail;
    private Integer learningScore;
    private String learningDetail;
    private Integer sportScore;
    private String sportDetail;
    private Integer dietScore;
    private String dietDetail;
    private Integer overallScore;
    private String aiAnalysis;
    private String suggestions;
    private String warnings;
}
