package com.kgms.course.service;

import com.kgms.course.dto.NutritionAnalysisDTO;
import com.kgms.course.entity.FoodNutrition;
import com.kgms.course.entity.NutritionStandard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class NutritionService {

    // 标准参考值
    private static final Map<String, Map<String, BigDecimal>> NUTRIENT_STANDARDS = new HashMap<>();

    static {
        // 3-4岁儿童营养标准
        Map<String, BigDecimal> standard34 = new HashMap<>();
        standard34.put("calories", new BigDecimal("1200"));
        standard34.put("protein", new BigDecimal("45"));
        standard34.put("calcium", new BigDecimal("600"));
        standard34.put("iron", new BigDecimal("10"));
        standard34.put("vitaminA", new BigDecimal("300"));
        standard34.put("vitaminC", new BigDecimal("60"));
        NUTRIENT_STANDARDS.put("3-4岁", standard34);

        // 4-5岁儿童营养标准
        Map<String, BigDecimal> standard45 = new HashMap<>();
        standard45.put("calories", new BigDecimal("1300"));
        standard45.put("protein", new BigDecimal("50"));
        standard45.put("calcium", new BigDecimal("650"));
        standard45.put("iron", new BigDecimal("10"));
        standard45.put("vitaminA", new BigDecimal("350"));
        standard45.put("vitaminC", new BigDecimal("70"));
        NUTRIENT_STANDARDS.put("4-5岁", standard45);

        // 5-6岁儿童营养标准
        Map<String, BigDecimal> standard56 = new HashMap<>();
        standard56.put("calories", new BigDecimal("1400"));
        standard56.put("protein", new BigDecimal("55"));
        standard56.put("calcium", new BigDecimal("700"));
        standard56.put("iron", new BigDecimal("10"));
        standard56.put("vitaminA", new BigDecimal("400"));
        standard56.put("vitaminC", new BigDecimal("80"));
        NUTRIENT_STANDARDS.put("5-6岁", standard56);
    }

    /**
     * 分析单日营养
     */
    public NutritionAnalysisDTO analyzeDailyNutrition(String recipeId, String date, String mealType, List<String> foodNames) {
        NutritionAnalysisDTO analysis = new NutritionAnalysisDTO();
        analysis.setRecipeId(recipeId);
        analysis.setDate(date);
        analysis.setMealType(mealType);

        // 汇总营养成分
        BigDecimal totalCalories = BigDecimal.ZERO;
        BigDecimal totalProtein = BigDecimal.ZERO;
        BigDecimal totalFat = BigDecimal.ZERO;
        BigDecimal totalCarbohydrates = BigDecimal.ZERO;

        List<String> allergenWarnings = new ArrayList<>();

        // 模拟计算（实际应从食材表查询）
        for (String foodName : foodNames) {
            // 模拟营养数据
            totalCalories = totalCalories.add(new BigDecimal("80"));
            totalProtein = totalProtein.add(new BigDecimal("5"));
            totalFat = totalFat.add(new BigDecimal("3"));
            totalCarbohydrates = totalCarbohydrates.add(new BigDecimal("10"));

            // 检查过敏原
            if (foodName.contains("牛奶") || foodName.contains("鸡蛋")) {
                allergenWarnings.add("注意：" + foodName + "可能引起过敏");
            }
        }

        analysis.setTotalCalories(totalCalories);
        analysis.setTotalProtein(totalProtein);
        analysis.setTotalFat(totalFat);
        analysis.setTotalCarbohydrates(totalCarbohydrates);

        // 营养素占比
        Map<String, BigDecimal> percentages = new HashMap<>();
        if (totalCalories.compareTo(BigDecimal.ZERO) > 0) {
            percentages.put("protein", totalProtein.multiply(new BigDecimal("400"))
                    .divide(totalCalories, 2, RoundingMode.HALF_UP));
            percentages.put("fat", totalFat.multiply(new BigDecimal("900"))
                    .divide(totalCalories, 2, RoundingMode.HALF_UP));
            percentages.put("carbohydrates", totalCarbohydrates.multiply(new BigDecimal("400"))
                    .divide(totalCalories, 2, RoundingMode.HALF_UP));
        }
        analysis.setNutrientPercentages(percentages);

        // 与标准对比
        Map<String, Object> comparison = new HashMap<>();
        Map<String, BigDecimal> standards = NUTRIENT_STANDARDS.get("4-5岁"); // 默认4-5岁
        if (standards != null) {
            comparison.put("calories", calculatePercentage(totalCalories, standards.get("calories")));
            comparison.put("protein", calculatePercentage(totalProtein, standards.get("protein")));
        }
        analysis.setComparisonWithStandard(comparison);

        // 过敏提醒
        analysis.setAllergenWarnings(allergenWarnings);

        // 营养建议
        List<String> suggestions = generateSuggestions(analysis);
        analysis.setSuggestions(suggestions);

        // 评分
        int score = calculateScore(analysis);
        analysis.setScore(score);
        analysis.setLevel(getLevel(score));

        return analysis;
    }

    /**
     * 分析每周营养
     */
    public Map<String, Object> analyzeWeeklyNutrition(String recipeId, List<String> dates) {
        Map<String, Object> weeklyAnalysis = new HashMap<>();

        // 汇总每日数据
        BigDecimal totalCalories = BigDecimal.ZERO;
        BigDecimal totalProtein = BigDecimal.ZERO;
        BigDecimal totalCalcium = BigDecimal.ZERO;
        BigDecimal totalIron = BigDecimal.ZERO;

        for (String date : dates) {
            // 模拟每日汇总
            totalCalories = totalCalories.add(new BigDecimal("1200"));
            totalProtein = totalProtein.add(new BigDecimal("45"));
            totalCalcium = totalCalcium.add(new BigDecimal("600"));
            totalIron = totalIron.add(new BigDecimal("10"));
        }

        weeklyAnalysis.put("totalCalories", totalCalories);
        weeklyAnalysis.put("totalProtein", totalProtein);
        weeklyAnalysis.put("totalCalcium", totalCalcium);
        weeklyAnalysis.put("totalIron", totalIron);

        // 日均
        int days = dates.size();
        if (days > 0) {
            weeklyAnalysis.put("avgCalories", totalCalories.divide(new BigDecimal(days), 0, RoundingMode.HALF_UP));
            weeklyAnalysis.put("avgProtein", totalProtein.divide(new BigDecimal(days), 0, RoundingMode.HALF_UP));
        }

        // 营养达标评估
        Map<String, String> adequacy = new HashMap<>();
        adequacy.put("calories", totalCalories.compareTo(new BigDecimal("8400")) >= 0 ? "达标" : "不足");
        adequacy.put("protein", totalProtein.compareTo(new BigDecimal("315")) >= 0 ? "达标" : "不足");
        weeklyAnalysis.put("adequacy", adequacy);

        return weeklyAnalysis;
    }

    /**
     * 获取食材过敏原信息
     */
    public List<Map<String, Object>> getFoodAllergens(List<String> foodNames) {
        List<Map<String, Object>> allergens = new ArrayList<>();

        Map<String, String> allergenMap = new HashMap<>();
        allergenMap.put("牛奶", "牛奶蛋白");
        allergenMap.put("鸡蛋", "蛋清蛋白");
        allergenMap.put("小麦", "麸质");
        allergenMap.put("花生", "花生蛋白");
        allergenMap.put("海鲜", "海鲜蛋白");

        for (String foodName : foodNames) {
            for (Map.Entry<String, String> entry : allergenMap.entrySet()) {
                if (foodName.contains(entry.getKey())) {
                    Map<String, Object> allergen = new HashMap<>();
                    allergen.put("food", foodName);
                    allergen.put("allergen", entry.getValue());
                    allergens.add(allergen);
                }
            }
        }

        return allergens;
    }

    private BigDecimal calculatePercentage(BigDecimal actual, BigDecimal standard) {
        if (standard == null || standard.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return actual.multiply(new BigDecimal("100"))
                .divide(standard, 2, RoundingMode.HALF_UP);
    }

    private List<String> generateSuggestions(NutritionAnalysisDTO analysis) {
        List<String> suggestions = new ArrayList<>();

        if (analysis.getTotalCalories() != null && analysis.getTotalCalories().compareTo(new BigDecimal("600")) < 0) {
            suggestions.add("热量摄入偏低，建议增加主食摄入");
        }

        if (analysis.getNutrientPercentages() != null) {
            BigDecimal protein = analysis.getNutrientPercentages().get("protein");
            if (protein != null && protein.compareTo(new BigDecimal("15")) < 0) {
                suggestions.add("蛋白质占比偏低，建议增加蛋白质摄入");
            }
            BigDecimal fat = analysis.getNutrientPercentages().get("fat");
            if (fat != null && fat.compareTo(new BigDecimal("30")) > 0) {
                suggestions.add("脂肪占比偏高，建议减少高脂肪食物");
            }
        }

        if (suggestions.isEmpty()) {
            suggestions.add("营养搭配合理");
        }

        return suggestions;
    }

    private int calculateScore(NutritionAnalysisDTO analysis) {
        int score = 80; // 基础分

        if (analysis.getTotalCalories() != null) {
            if (analysis.getTotalCalories().compareTo(new BigDecimal("500")) > 0 &&
                    analysis.getTotalCalories().compareTo(new BigDecimal("700")) < 0) {
                score += 10;
            } else {
                score -= 10;
            }
        }

        return Math.min(100, Math.max(0, score));
    }

    private String getLevel(int score) {
        if (score >= 90) return "优秀";
        if (score >= 75) return "良好";
        if (score >= 60) return "及格";
        return "需改进";
    }
}