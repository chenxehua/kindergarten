package com.kgms.food.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kgms.common.exception.BusinessException;
import com.kgms.common.util.IdGenerator;
import com.kgms.food.dto.RecipeDTO;
import com.kgms.food.dto.RecipeVO;
import com.kgms.food.entity.FoodRecipe;
import com.kgms.food.mapper.FoodRecipeMapper;
import com.kgms.student.entity.StudentInfo;
import com.kgms.student.mapper.StudentInfoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeService {

    private final FoodRecipeMapper recipeMapper;
    private final StudentInfoMapper studentInfoMapper;

    @Transactional
    public String addRecipe(RecipeDTO dto) {
        // 获取本周一
        LocalDate weekStart = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
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
        LocalDate weekStart = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
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

    /**
     * 检查过敏食物
     * 根据学生过敏信息和食谱内容，判断是否有过敏风险
     */
    public boolean checkAllergy(String studentId, String recipeId) {
        // 获取学生过敏信息
        StudentInfo student = studentInfoMapper.selectOne(
                new LambdaQueryWrapper<StudentInfo>().eq(StudentInfo::getStudentId, studentId)
        );

        if (student == null || student.getAllergyInfo() == null || student.getAllergyInfo().isEmpty()) {
            return false; // 无过敏信息
        }

        // 获取食谱内容
        FoodRecipe recipe = getRecipeById(recipeId);
        if (recipe == null) {
            return false;
        }

        // 解析学生过敏信息
        Set<String> allergies = parseAllergies(student.getAllergyInfo());

        // 检查每日食谱是否包含过敏食物
        boolean hasAllergy = checkRecipeContainsAllergy(recipe, allergies);

        log.info("过敏检查: studentId={}, recipeId={}, hasAllergy={}", studentId, recipeId, hasAllergy);
        return hasAllergy;
    }

    private Set<String> parseAllergies(String allergyInfo) {
        // 过敏信息格式: "鸡蛋,牛奶,花生" 或 JSON数组
        if (allergyInfo == null || allergyInfo.isEmpty()) {
            return java.util.Collections.emptySet();
        }
        return java.util.Arrays.stream(allergyInfo.split("[,，]"))
                .map(s -> s.trim().toLowerCase())
                .filter(s -> !s.isEmpty())
                .collect(java.util.stream.Collectors.toSet());
    }

    private boolean checkRecipeContainsAllergy(FoodRecipe recipe, Set<String> allergies) {
        if (allergies.isEmpty()) {
            return false;
        }

        // 检查每天的食谱
        String[] days = {recipe.getMonday(), recipe.getTuesday(), recipe.getWednesday(),
                recipe.getThursday(), recipe.getFriday(), recipe.getSaturday(), recipe.getSunday()};

        for (String dayRecipe : days) {
            if (dayRecipe != null && !dayRecipe.isEmpty()) {
                String recipeLower = dayRecipe.toLowerCase();
                for (String allergy : allergies) {
                    if (recipeLower.contains(allergy)) {
                        log.warn("检测到过敏食物: allergy={}, recipe={}", allergy, dayRecipe);
                        return true;
                    }
                }
            }
        }
        return false;
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
