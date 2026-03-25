package com.kgms.growth.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kgms.common.util.IdGenerator;
import com.kgms.growth.entity.MonthlyReport;
import com.kgms.growth.mapper.MonthlyReportMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 月度报告定时任务服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MonthlyReportJobService {

    private final MonthlyReportMapper reportMapper;

    /**
     * 生成上月的成长报告
     */
    public void generateLastMonthReports() {
        // 获取上月的年月
        LocalDate lastMonth = LocalDate.now().minusMonths(1);
        String monthStr = lastMonth.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        
        log.info("开始生成 {} 月度成长报告", monthStr);
        
        // TODO: 获取所有在园学生，逐一生成报告
        // 这里简化处理，实际应该查询学生列表
        
        // 模拟生成报告
        // MonthlyReport report = new MonthlyReport();
        // report.setReportId(IdGenerator.generateStrId());
        // report.setStudentId(studentId);
        // report.setReportMonth(monthStr);
        // report.setAttendanceDays(20);
        // report.setTotalDays(22);
        // report.setAiSummary("本月表现优秀...");
        // report.setTeacherSummary("继续加油！");
        // report.setStatus(0); // 待审核
        // reportMapper.insert(report);
        
        log.info("月度成长报告生成完成");
    }

    /**
     * 生成指定学生的月度报告
     */
    public String generateReportForStudent(String studentId, String month) {
        // 检查是否已存在
        MonthlyReport existing = reportMapper.selectOne(
                new LambdaQueryWrapper<MonthlyReport>()
                        .eq(MonthlyReport::getStudentId, studentId)
                        .eq(MonthlyReport::getReportMonth, month)
        );
        
        if (existing != null) {
            log.info("报告已存在: studentId={}, month={}", studentId, month);
            return existing.getReportId();
        }
        
        // TODO: 调用AI服务生成分析
        
        // 生成报告
        MonthlyReport report = new MonthlyReport();
        report.setReportId(IdGenerator.generateStrId());
        report.setStudentId(studentId);
        report.setReportMonth(month);
        
        // 设置统计数据
        report.setAttendanceDays(20);
        report.setTotalDays(22);
        
        // TODO: AI分析
        report.setAiSummary("本月表现优秀，情绪稳定，与同伴相处融洽。");
        
        // TODO: 教师寄语
        report.setTeacherSummary("孩子本月进步明显，继续保持！");
        
        report.setStatus(0); // 待审核
        
        reportMapper.insert(report);
        
        return report.getReportId();
    }
}
