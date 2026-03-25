package com.kgms.classs.dto;

import lombok.Data;

/**
 * 班级信息VO
 */
@Data
public class ClassVO {
    /** 班级ID */
    private String classId;
    /** 幼儿园ID */
    private String kgId;
    /** 班级名称 */
    private String className;
    /** 年级 */
    private String grade;
    /** 班主任ID */
    private String headTeacherId;
    /** 容量 */
    private Integer capacity;
    /** 学生数量 */
    private Integer studentCount;
    /** 状态 */
    private Integer status;
}
