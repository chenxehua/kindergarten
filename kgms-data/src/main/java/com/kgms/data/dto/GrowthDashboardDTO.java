package com.kgms.data.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 成长看板数据
 */
@Data
public class GrowthDashboardDTO {

    // 发展概况
    private Double avgEmotionScore;
    private Double avgSocialScore;
    private Double avgLearningScore;
    private Double avgSportScore;
    private Double avgDietScore;

    // 各班级发展对比
    private List<Map<String, Object>> classComparison;

    // 发展趋势
    private List<Map<String, Object>> growthTrend;

    // 突出进步学生
    private List<Map<String, Object>> progressStudents;

    // 预警名单
    private List<Map<String, Object>> warningList;

    // 预警级别统计
    private Map<String, Integer> warningLevelStats;
}
