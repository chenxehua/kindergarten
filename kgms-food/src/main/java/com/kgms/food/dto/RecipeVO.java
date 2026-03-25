package com.kgms.food.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * 食谱VO
 */
@Data
public class RecipeVO {
    /** 食谱ID */
    private String recipeId;
    /** 幼儿园ID */
    private String kgId;
    /** 周开始日期 */
    private LocalDate weekStart;
    /** 周一食谱 */
    private String monday;
    /** 周二食谱 */
    private String tuesday;
    /** 周三食谱 */
    private String wednesday;
    /** 周四食谱 */
    private String thursday;
    /** 周五食谱 */
    private String friday;
    /** 周六食谱 */
    private String saturday;
    /** 周日食谱 */
    private String sunday;
    /** 营养师 */
    private String nutritionist;
    /** 发布状态 */
    private Integer publishStatus;
}
