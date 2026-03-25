package com.kgms.food.controller;

import com.kgms.food.dto.RecipeDTO;
import com.kgms.food.dto.RecipeVO;
import com.kgms.food.service.RecipeService;
import com.kgms.common.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 食谱管理Controller
 * 权限: 园长/营养师可编辑, 家长/老师可查看
 */
@RestController
@RequestMapping("/api/food")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    /**
     * 新增食谱 - TC-FOOD-001
     * 权限: 园长/营养师
     */
    @PostMapping("/recipe/add")
    public Result<String> addRecipe(@RequestBody RecipeDTO dto) {
        // TODO: 权限检查
        return Result.success(recipeService.addRecipe(dto));
    }

    /**
     * 更新食谱 - TC-FOOD-001
     */
    @PutMapping("/recipe/update")
    public Result<Void> updateRecipe(@RequestParam String recipeId, @RequestBody RecipeDTO dto) {
        recipeService.updateRecipe(recipeId, dto);
        return Result.success();
    }

    /**
     * 发布食谱 - TC-FOOD-001
     */
    @PostMapping("/recipe/publish")
    public Result<Void> publishRecipe(@RequestParam String recipeId) {
        recipeService.publishRecipe(recipeId);
        return Result.success();
    }

    /**
     * 获取食谱详情
     */
    @GetMapping("/recipe/detail")
    public Result<RecipeVO> getRecipeDetail(@RequestParam String recipeId) {
        return Result.success(recipeService.getRecipeDetail(recipeId));
    }

    /**
     * 获取本周食谱 - TC-FOOD-002
     */
    @GetMapping("/recipe/thisWeek")
    public Result<RecipeVO> getCurrentWeekRecipe(@RequestParam String kgId) {
        return Result.success(recipeService.getCurrentWeekRecipe(kgId));
    }

    /**
     * 获取食谱列表
     */
    @GetMapping("/recipe/list")
    public Result<List<RecipeVO>> getRecipeList(
            @RequestParam String kgId,
            @RequestParam Integer year,
            @RequestParam Integer month) {
        return Result.success(recipeService.getRecipeList(kgId, year, month));
    }

    /**
     * 过敏食物提醒 - TC-FOOD-003
     * 系统自动检测并提醒
     */
    @GetMapping("/allergy/check")
    public Result<Boolean> checkAllergy(
            @RequestParam String studentId,
            @RequestParam String recipeId) {
        // TODO: 检查学生过敏信息与食谱是否冲突
        // 根据学生过敏信息和食谱内容，判断是否有过敏风险
        return Result.success(false);
    }
}