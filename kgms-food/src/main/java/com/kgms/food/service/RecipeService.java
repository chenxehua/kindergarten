package com.kgms.food.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kgms.common.exception.BusinessException;
import com.kgms.common.util.IdGenerator;
import com.kgms.food.dto.RecipeDTO;
import com.kgms.food.dto.RecipeVO;
import com.kgms.food.entity.FoodRecipe;
import com.kgms.food.mapper.FoodRecipeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporaryAdjusters;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeService {

    private final FoodRecipeMapper recipeMapper;

    @Transactional
    public String addRecipe(RecipeDTO dto) {
        // 获取本周一
        LocalDate weekStart = LocalDate.now().with(TemporaryAdjusters.previousOrSame(DayOfWeek.MONDAY));
        if (dto.getWeekStart() != null) {
            weekStart = dto.getWeekStart();
        }

        // 检查是否已存在
        FoodRecipe existing = getRecipeByKgAndWeek(dto.getKgId(), weekStart);
        if (existing != null) {
            throw new BusinessException(400, "本周食谱已存在");
        }

        FoodRecipe recipe = new FoodRecipe();
        recipe.setRecipeId(IdGenerator.generateStrId());
        recipe.setKgId(dto.getKgId());
        recipe.setWeekStart(weekStart);
        recipe.setMonday(dto.getMonday());
        recipe.setTuesday(dto.getTuesday());
        recipe.setWednesday(dto.getWednesday());
        recipe.setThursday(dto.getThursday());
        recipe.setFriday(dto.getFriday());
        recipe.setSaturday(dto.getSaturday());
        recipe.setSunday(dto.getSunday());
        recipe.setNutritionist(dto.getNutritionist());
        recipe.setPublishStatus(0);

        recipeMapper.insert(recipe);
        return recipe.getRecipeId();
    }

    @Transactional
    public void updateRecipe(String recipeId, RecipeDTO dto) {
        FoodRecipe recipe = getRecipeById(recipeId);
        if (recipe == null) {
            throw new BusinessException(404, "食谱不存在");
        }

        if (dto.getMonday() != null) recipe.setMonday(dto.getMonday());
        if (dto.getTuesday() != null) recipe.setTuesday(dto.getTuesday());
        if (dto.getWednesday() != null) recipe.setWednesday(dto.getWednesday());
        if (dto.getThursday() != null) recipe.setThursday(dto.getThursday());
        if (dto.getFriday() != null) recipe.setFriday(dto.getFriday());
        if (dto.getSaturday() != null) recipe.setSaturday(dto.getSaturday());
        if (dto.getSunday() != null) recipe.setSunday(dto.getSunday());
        if (dto.getNutritionist() != null) recipe.setNutritionist(dto.getNutritionist());

        recipeMapper.updateById(recipe);
    }

    @Transactional
    public void publishRecipe(String recipeId) {
        FoodRecipe recipe = getRecipeById(recipeId);
        if (recipe == null) {
            throw new BusinessException(404, "食谱不存在");
        }
        recipe.setPublishStatus(1);
        recipeMapper.updateById(recipe);
    }

    public RecipeVO getRecipeDetail(String recipeId) {
        FoodRecipe recipe = getRecipeById(recipeId);
        if (recipe == null) {
            throw new BusinessException(404, "食谱不存在");
        }
        return convertToVO(recipe);
    }

    public RecipeVO getCurrentWeekRecipe(String kgId) {
        LocalDate weekStart = LocalDate.now().with(TemporaryAdjusters.previousOrSame(DayOfWeek.MONDAY));
        FoodRecipe recipe = getRecipeByKgAndWeek(kgId, weekStart);
        if (recipe == null) {
            return null;
        }
        return convertToVO(recipe);
    }

    public List<RecipeVO> getRecipeList(String kgId, Integer year, Integer month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);

        LambdaQueryWrapper<FoodRecipe> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FoodRecipe::getKgId, kgId);
        wrapper.ge(FoodRecipe::getWeekStart, startDate);
        wrapper.le(FoodRecipe::getWeekStart, endDate);
        wrapper.orderByDesc(FoodRecipe::getWeekStart);

        List<RecipeVO> voList = new ArrayList<>();
        for (FoodRecipe recipe : recipeMapper.selectList(wrapper)) {
            voList.add(convertToVO(recipe));
        }
        return voList;
    }

    private FoodRecipe getRecipeById(String recipeId) {
        return recipeMapper.selectOne(
                new LambdaQueryWrapper<FoodRecipe>().eq(FoodRecipe::getRecipeId, recipeId)
        );
    }

    private FoodRecipe getRecipeByKgAndWeek(String kgId, LocalDate weekStart) {
        return recipeMapper.selectOne(
                new LambdaQueryWrapper<FoodRecipe>()
                        .eq(FoodRecipe::getKgId, kgId)
                        .eq(FoodRecipe::getWeekStart, weekStart)
        );
    }

    private RecipeVO convertToVO(FoodRecipe recipe) {
        RecipeVO vo = new RecipeVO();
        vo.setRecipeId(recipe.getRecipeId());
        vo.setKgId(recipe.getKgId());
        vo.setWeekStart(recipe.getWeekStart());
        vo.setMonday(recipe.getMonday());
        vo.setTuesday(recipe.getTuesday());
        vo.setWednesday(recipe.getWednesday());
        vo.setThursday(recipe.getThursday());
        vo.setFriday(recipe.getFriday());
        vo.setSaturday(recipe.getSaturday());
        vo.setSunday(recipe.getSunday());
        vo.setNutritionist(recipe.getNutritionist());
        vo.setPublishStatus(recipe.getPublishStatus());
        return vo;
    }
}
