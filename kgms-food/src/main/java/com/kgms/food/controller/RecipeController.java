package com.kgms.food.controller;

import com.kgms.common.result.Result;
import com.kgms.food.dto.RecipeDTO;
import com.kgms.food.dto.RecipeVO;
import com.kgms.food.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping("/recipe/add")
    public Result<String> addRecipe(@RequestBody RecipeDTO dto) {
        return Result.success(recipeService.addRecipe(dto));
    }

    @PutMapping("/recipe/update")
    public Result<Void> updateRecipe(@RequestParam String recipeId, @RequestBody RecipeDTO dto) {
        recipeService.updateRecipe(recipeId, dto);
        return Result.success();
    }

    @PostMapping("/recipe/publish")
    public Result<Void> publishRecipe(@RequestParam String recipeId) {
        recipeService.publishRecipe(recipeId);
        return Result.success();
    }

    @GetMapping("/recipe/detail")
    public Result<RecipeVO> getRecipeDetail(@RequestParam String recipeId) {
        return Result.success(recipeService.getRecipeDetail(recipeId));
    }

    @GetMapping("/recipe/thisWeek")
    public Result<RecipeVO> getCurrentWeekRecipe(@RequestParam String kgId) {
        return Result.success(recipeService.getCurrentWeekRecipe(kgId));
    }

    @GetMapping("/recipe/list")
    public Result<List<RecipeVO>> getRecipeList(
            @RequestParam String kgId,
            @RequestParam Integer year,
            @RequestParam Integer month) {
        return Result.success(recipeService.getRecipeList(kgId, year, month));
    }
}
