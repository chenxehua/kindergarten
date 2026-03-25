package com.kgms.food.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 食谱实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_food_recipe")
public class FoodRecipe extends BaseEntity {
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
    /** 发布时间 */
    private LocalDateTime publishTime;
}
