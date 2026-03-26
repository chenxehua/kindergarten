package com.kgms.course.entity;

import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 食材营养实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FoodNutrition extends BaseEntity {

    private String foodId;
    private String foodName;
    private String category;

    // 每100g营养成分
    private BigDecimal calories;
    private BigDecimal protein;
    private BigDecimal fat;
    private BigDecimal carbohydrates;
    private BigDecimal vitaminA;
    private BigDecimal vitaminC;
    private BigDecimal calcium;
    private BigDecimal iron;
    private BigDecimal zinc;
    private BigDecimal fiber;

    // 过敏原
    private String allergens;
}
