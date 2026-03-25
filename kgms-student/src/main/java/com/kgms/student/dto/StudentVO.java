package com.kgms.student.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 学生信息VO
 */
@Data
public class StudentVO {

    /** 学生ID */
    private String studentId;
    /** 学生姓名 */
    private String studentName;
    /** 性别 */
    private Integer gender;
    /** 性别描述 */
    private String genderDesc;

    /** 出生日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    /** 身份证号 */
    private String idCard;
    /** 头像URL */
    private String avatar;
    /** 班级ID */
    private String classId;
    /** 班级名称 */
    private String className;

    /** 入园日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate enrollDate;

    /** 状态 */
    private Integer status;
    /** 状态描述 */
    private String statusDesc;
    /** 过敏信息 */
    private String allergyInfo;
    /** 既往病史 */
    private String medicalHistory;
    /** 家庭住址 */
    private String homeAddress;
    /** 紧急联系人 */
    private String emergencyContact;
    /** 紧急联系电话 */
    private String emergencyPhone;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
