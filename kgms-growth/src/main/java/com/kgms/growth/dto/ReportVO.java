package com.kgms.growth.dto;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 月度报告VO
 */
@Data
public class ReportVO {
    /** 报告ID */
    private String reportId;
    /** 学生ID */
    private String studentId;
    /** 学生姓名 */
    private String studentName;
    /** 班级名称 */
    private String className;
    /** 报告月份 */
    private String reportMonth;

    /** 基础统计 */
    private Integer attendanceDays;
    private Integer totalDays;
    private Double attendanceRate;
    private String attendanceTrend; // 上升/下降/持平

    /** 成长亮点 */
    private List<String> highlights;
    private List<Map<String, Object>> progressList;

    /** 各维度发展情况 */
    private Map<String, Object> dimensionData;

    /** AI分析 */
    private String aiSummary;
    /** 教师寄语 */
    private String teacherSummary;
    /** 老师签名 */
    private String teacherSignature;
    /** 老师姓名 */
    private String teacherName;

    /** 精选照片 */
    private List<String> featuredPhotos;
    private List<Map<String, Object>> photoDetails;

    /** 家长反馈 */
    private String parentFeedback;
    private String parentReplyTime;

    /** 报告状态 */
    private Integer status; // 0-生成中 1-待审核 2-已发布
    private String statusText;
    private String publishTime;

    /** H5页面URL */
    private String h5Url;
    /** PDF下载URL */
    private String pdfUrl;

    /** 创建时间 */
    private String createTime;
}
