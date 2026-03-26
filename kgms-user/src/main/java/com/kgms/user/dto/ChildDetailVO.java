package com.kgms.user.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * 孩子详情VO
 */
@Data
public class ChildDetailVO {
    // 基本信息
    private String studentId;
    private String studentName;
    private Integer gender;
    private LocalDate birthday;
    private String avatar;
    private String className;
    private String classId;
    private LocalDate enrollDate;
    
    // 健康信息
    private String allergyInfo;
    private String medicalHistory;
    private String heightWeight;
    
    // 家庭信息
    private String homeAddress;
    private String emergencyContact;
    private String emergencyPhone;
}
