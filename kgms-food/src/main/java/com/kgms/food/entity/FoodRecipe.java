package com.kgms.food.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_food_recipe")
public class FoodRecipe extends BaseEntity {

    private String recipeId;
    private String kgId;
    private LocalDate weekStart;
    private String monday;
    private String tuesday;
    private String wednesday;
    private String thursday;
    private String friday;
    private String saturday;
    private String sunday;
    private String nutritionist;
    private Integer publishStatus;
    private LocalDateTime publishTime;
}
