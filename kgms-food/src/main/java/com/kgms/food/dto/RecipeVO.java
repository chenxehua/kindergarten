package com.kgms.food.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RecipeVO {
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
}
