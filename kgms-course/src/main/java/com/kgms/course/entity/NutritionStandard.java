package com.kgms.course.entity;

import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 营养成分标准实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NutritionStandard extends BaseEntity {

    private String standardId;
    private String ageGroup;
    private String nutrientName;
    private BigDecimal dailyAmount;
    private String unit;
    private String description;
}