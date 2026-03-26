package com.kgms.record.dto;

import lombok.Data;

/**
 * AI辅助填写结果
 */
@Data
public class AIAssistResultDTO {

    /** 课程记录建议 */
    private String courseSuggestion;
    /** 情绪记录建议 */
    private String emotionSuggestion;
    /** 饮食记录建议 */
    private String dietSuggestion;
    /** 活动记录建议 */
    private String activitySuggestion;
    /** 总体评价建议 */
    private String overallSuggestion;
    /** 置信度 */
    private Double confidence;
    /** 建议的可信程度 */
    private String reliability; // HIGH/MEDIUM/LOW
}
