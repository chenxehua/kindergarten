package com.kgms.data.entity;

import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 看板数据快照实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DashboardSnapshot extends BaseEntity {

    private String snapshotId;
    private String kgId;
    private String snapshotDate;
    private String snapshotType;

    // 学生统计
    private Integer totalStudents;
    private Integer newStudents;
    private Integer leftStudents;

    // 考勤统计
    private BigDecimal attendanceRate;
    private Integer absentCount;
    private Integer leaveCount;
    private Integer lateCount;

    // 成长记录统计
    private Integer totalRecords;
    private Integer publishedRecords;
    private Integer totalPhotos;

    // 家长活跃度
    private Integer activeParents;
    private Integer messageCount;

    // 课程统计
    private Integer totalCourses;
    private Integer completedCourses;
}
