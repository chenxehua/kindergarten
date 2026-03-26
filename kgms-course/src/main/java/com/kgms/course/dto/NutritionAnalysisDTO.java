package com.kgms.course.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 营养分析DTO
 */
@Data
public class NutritionAnalysisDTO {

    /** 食谱ID */
    private String recipeId;
    /** 日期 */
    private String date;
    /** 餐次 */
    private String mealType;

    /** 营养素汇总 */
    private BigDecimal totalCalories;
    private BigDecimal totalProtein;
    private BigDecimal totalFat;
    private BigDecimal totalCarbohydrates;

    /** 各营养素占比 */
    private Map<String, BigDecimal> nutrientPercentages;

    /** 与标准对比 */
    private Map<String, Object> comparisonWithStandard;

    /** 过敏原提醒 */
    private List<String> allergenWarnings;

    /** 营养建议 */
    private List<String> suggestions;

    /** 评分 */
    private Integer score;
    private String level;
}
