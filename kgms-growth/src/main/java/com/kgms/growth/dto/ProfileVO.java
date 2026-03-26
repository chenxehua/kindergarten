package com.kgms.growth.dto;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 成长档案VO
 */
@Data
public class ProfileVO {
    /** 档案ID */
    private String profileId;
    /** 学生ID */
    private String studentId;
    /** 学生姓名 */
    private String studentName;
    /** 档案月份 */
    private String profileMonth;

    /** 情绪评分 */
    private Integer emotionScore;
    private String emotionLevel;
    private String emotionTrend;
    private String emotionDetail;

    /** 社交评分 */
    private Integer socialScore;
    private String socialLevel;
    private String socialTrend;
    private String socialDetail;

    /** 学习评分 */
    private Integer learningScore;
    private String learningLevel;
    private String learningTrend;
    private String learningDetail;

    /** 运动评分 */
    private Integer sportScore;
    private String sportLevel;
    private String sportTrend;
    private String sportDetail;

    /** 饮食评分 */
    private Integer dietScore;
    private String dietLevel;
    private String dietTrend;
    private String dietDetail;

    /** 综合评分 */
    private Integer overallScore;
    /** AI分析 */
    private String aiAnalysis;
    /** 建议 */
    private List<String> suggestions;
    /** 预警信息 */
    private List<Map<String, Object>> warnings;
    /** 雷达图URL */
    private String radarChartUrl;
    /** 生成时间 */
    private String generatedAt;
}
