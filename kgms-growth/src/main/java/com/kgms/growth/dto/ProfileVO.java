package com.kgms.growth.dto;
import lombok.Data;

/**
 * 成长档案VO
 */
@Data
public class ProfileVO {
    /** 档案ID */
    private String profileId;
    /** 学生ID */
    private String studentId;
    /** 档案月份 */
    private String profileMonth;
    /** 情绪评分 */
    private Integer emotionScore;
    /** 社交评分 */
    private Integer socialScore;
    /** 学习评分 */
    private Integer learningScore;
    /** 运动评分 */
    private Integer sportScore;
    /** 饮食评分 */
    private Integer dietScore;
    /** 综合评分 */
    private Integer overallScore;
    /** AI分析 */
    private String aiAnalysis;
}
