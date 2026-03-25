package com.kgms.growth.dto;
import lombok.Data;
@Data
public class ProfileVO {
    private String profileId;
    private String studentId;
    private String profileMonth;
    private Integer emotionScore;
    private Integer socialScore;
    private Integer learningScore;
    private Integer sportScore;
    private Integer dietScore;
    private Integer overallScore;
    private String aiAnalysis;
}
