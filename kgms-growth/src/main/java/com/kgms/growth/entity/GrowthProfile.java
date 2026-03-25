package com.kgms.growth.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 成长档案实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_growth_profile")
public class GrowthProfile extends BaseEntity {
    /** 档案ID */
    private String profileId;
    /** 学生ID */
    private String studentId;
    /** 档案月份 */
    private String profileMonth;
    /** 情绪评分 */
    private Integer emotionScore;
    /** 情绪详情 */
    private String emotionDetail;
    /** 社交评分 */
    private Integer socialScore;
    /** 社交详情 */
    private String socialDetail;
    /** 学习评分 */
    private Integer learningScore;
    /** 学习详情 */
    private String learningDetail;
    /** 运动评分 */
    private Integer sportScore;
    /** 运动详情 */
    private String sportDetail;
    /** 饮食评分 */
    private Integer dietScore;
    /** 饮食详情 */
    private String dietDetail;
    /** 综合评分 */
    private Integer overallScore;
    /** AI分析 */
    private String aiAnalysis;
    /** 建议 */
    private String suggestions;
    /** 预警 */
    private String warnings;
}
