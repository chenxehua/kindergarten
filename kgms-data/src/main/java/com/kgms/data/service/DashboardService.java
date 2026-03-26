package com.kgms.data.service;

import com.kgms.common.util.IdGenerator;
import com.kgms.data.dto.ClassDashboardDTO;
import com.kgms.data.dto.GrowthDashboardDTO;
import com.kgms.data.dto.SchoolDashboardDTO;
import com.kgms.data.entity.DashboardSnapshot;
import com.kgms.data.mapper.DashboardSnapshotMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class DashboardService {

    @Autowired
    private DashboardSnapshotMapper dashboardSnapshotMapper;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 获取班级看板数据
     */
    public ClassDashboardDTO getClassDashboard(String classId) {
        ClassDashboardDTO dashboard = new ClassDashboardDTO();
        dashboard.setClassId(classId);

        // TODO: 从数据库查询实际数据，这里构建示例数据
        // 人数统计
        dashboard.setTotalStudents(25);
        dashboard.setBoyCount(13);
        dashboard.setGirlCount(12);

        // 今日考勤
        dashboard.setPresentCount(22);
        dashboard.setAbsentCount(1);
        dashboard.setLeaveCount(2);
        dashboard.setLateCount(0);
        dashboard.setAttendanceRate(88.0);

        // 考勤趋势（最近7天）
        List<Map<String, Object>> attendanceTrend = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            LocalDate date = LocalDate.now().minusDays(i);
            Map<String, Object> day = new HashMap<>();
            day.put("date", date.format(dateFormatter));
            day.put("rate", 85.0 + Math.random() * 10);
            attendanceTrend.add(day);
        }
        dashboard.setAttendanceTrend(attendanceTrend);

        // 成长记录统计
        dashboard.setTodayRecordCount(15);
        dashboard.setWeekRecordCount(85);
        dashboard.setMonthRecordCount(320);
        dashboard.setPhotoCount(45);

        // 家长互动
        dashboard.setMessageCount(28);
        dashboard.setReplyRate(92.5);

        return dashboard;
    }

    /**
     * 获取园所看板数据
     */
    public SchoolDashboardDTO getSchoolDashboard(String kgId) {
        SchoolDashboardDTO dashboard = new SchoolDashboardDTO();

        // 运营概览
        dashboard.setTotalStudents(120);
        dashboard.setTotalClasses(6);
        dashboard.setTotalTeachers(15);
        dashboard.setNewStudentsThisMonth(3);
        dashboard.setLeftStudentsThisMonth(1);

        // 全园考勤
        dashboard.setAttendanceRate(92.5);

        // 各班级考勤排名
        List<Map<String, Object>> attendanceRanking = new ArrayList<>();
        Map<String, Object> class1 = new HashMap<>();
        class1.put("className", "星星班");
        class1.put("rate", 95.2);
        class1.put("rank", 1);
        attendanceRanking.add(class1);
        Map<String, Object> class2 = new HashMap<>();
        class2.put("className", "月亮班");
        class2.put("rate", 93.8);
        class2.put("rank", 2);
        attendanceRanking.add(class2);
        Map<String, Object> class3 = new HashMap<>();
        class3.put("className", "太阳班");
        class3.put("rate", 88.5);
        class3.put("rank", 3);
        attendanceRanking.add(class3);
        dashboard.setAttendanceRanking(attendanceRanking);

        // 考勤趋势
        List<Map<String, Object>> attendanceTrend = new ArrayList<>();
        for (int i = 29; i >= 0; i--) {
            LocalDate date = LocalDate.now().minusDays(i);
            Map<String, Object> day = new HashMap<>();
            day.put("date", date.format(dateFormatter));
            day.put("rate", 88.0 + Math.random() * 10);
            attendanceTrend.add(day);
        }
        dashboard.setAttendanceTrend(attendanceTrend);

        // 数据统计
        dashboard.setTotalRecordsThisMonth(850);
        dashboard.setTotalPhotosThisMonth(1250);
        dashboard.setTotalVideosThisMonth(45);
        dashboard.setActiveParents(98);
        dashboard.setMessageCount(320);

        // 招生分析
        dashboard.setNewEnrollments(5);
        dashboard.setConsultConversionRate(68.5);
        dashboard.setRenewalRate(95.0);
        dashboard.setChurnRate(2.1);

        // 运营指标
        dashboard.setParentSatisfaction(4.7);
        dashboard.setTeacherWorkload(75.0);
        dashboard.setCourseCoverage(100.0);
        dashboard.setActivityCount(12);

        return dashboard;
    }

    /**
     * 获取成长看板数据
     */
    public GrowthDashboardDTO getGrowthDashboard(String kgId) {
        GrowthDashboardDTO dashboard = new GrowthDashboardDTO();

        // 各维度平均分
        dashboard.setAvgEmotionScore(85.5);
        dashboard.setAvgSocialScore(82.3);
        dashboard.setAvgLearningScore(88.7);
        dashboard.setAvgSportScore(80.2);
        dashboard.setAvgDietScore(86.8);

        // 各班级发展对比
        List<Map<String, Object>> classComparison = new ArrayList<>();
        Map<String, Object> comp = new HashMap<>();
        comp.put("className", "星星班");
        comp.put("emotion", 86.0);
        comp.put("social", 83.5);
        comp.put("learning", 89.2);
        comp.put("sport", 81.0);
        comp.put("diet", 87.5);
        classComparison.add(comp);
        dashboard.setClassComparison(classComparison);

        // 发展趋势
        List<Map<String, Object>> growthTrend = new ArrayList<>();
        for (int i = 5; i >= 0; i--) {
            LocalDate date = LocalDate.now().minusMonths(i);
            Map<String, Object> month = new HashMap<>();
            month.put("month", date.format(DateTimeFormatter.ofPattern("yyyy-MM")));
            month.put("emotion", 80.0 + Math.random() * 10);
            month.put("social", 78.0 + Math.random() * 10);
            month.put("learning", 82.0 + Math.random() * 10);
            month.put("sport", 76.0 + Math.random() * 10);
            month.put("diet", 80.0 + Math.random() * 10);
            growthTrend.add(month);
        }
        dashboard.setGrowthTrend(growthTrend);

        // 突出进步学生
        List<Map<String, Object>> progressStudents = new ArrayList<>();
        Map<String, Object> student = new HashMap<>();
        student.put("studentId", "stu_001");
        student.put("studentName", "张小明");
        student.put("progressDimension", "学习能力");
        student.put("progressScore", 15);
        progressStudents.add(student);
        dashboard.setProgressStudents(progressStudents);

        // 预警名单
        List<Map<String, Object>> warningList = new ArrayList<>();
        Map<String, Object> warning = new HashMap<>();
        warning.put("studentId", "stu_002");
        warning.put("studentName", "李小红");
        warning.put("warningType", "情绪低落");
        warning.put("warningLevel", "MEDIUM");
        warning.put("description", "连续3天情绪低落");
        warningList.add(warning);
        dashboard.setWarningList(warningList);

        // 预警级别统计
        Map<String, Integer> warningLevelStats = new HashMap<>();
        warningLevelStats.put("HIGH", 2);
        warningLevelStats.put("MEDIUM", 5);
        warningLevelStats.put("LOW", 8);
        dashboard.setWarningLevelStats(warningLevelStats);

        return dashboard;
    }

    /**
     * 生成每日数据快照
     */
    public void generateDailySnapshot(String kgId) {
        LocalDate today = LocalDate.now();
        String snapshotId = "snapshot_" + kgId + "_" + today.format(dateFormatter);

        DashboardSnapshot existing = dashboardSnapshotMapper.selectBySnapshotId(snapshotId);
        if (existing != null) {
            log.info("Daily snapshot already exists for {}", snapshotId);
            return;
        }

        DashboardSnapshot snapshot = new DashboardSnapshot();
        snapshot.setSnapshotId(snapshotId);
        snapshot.setKgId(kgId);
        snapshot.setSnapshotDate(today.format(dateFormatter));
        snapshot.setSnapshotType("DAILY");

        // TODO: 从数据库查询实际统计数据
        snapshot.setTotalStudents(120);
        snapshot.setNewStudents(3);
        snapshot.setLeftStudents(1);
        snapshot.setAttendanceRate(new BigDecimal("92.5").setScale(2, RoundingMode.HALF_UP));
        snapshot.setAbsentCount(3);
        snapshot.setLeaveCount(5);
        snapshot.setLateCount(2);
        snapshot.setTotalRecords(28);
        snapshot.setPublishedRecords(25);
        snapshot.setTotalPhotos(42);
        snapshot.setActiveParents(98);
        snapshot.setMessageCount(35);
        snapshot.setTotalCourses(8);
        snapshot.setCompletedCourses(6);

        dashboardSnapshotMapper.insert(snapshot);
        log.info("Generated daily snapshot: {}", snapshotId);
    }

    /**
     * 获取历史快照数据
     */
    public List<DashboardSnapshot> getSnapshotHistory(String kgId, String startDate, String endDate, String snapshotType) {
        return dashboardSnapshotMapper.selectByKgIdAndDateRange(kgId, startDate, endDate, snapshotType);
    }
}
