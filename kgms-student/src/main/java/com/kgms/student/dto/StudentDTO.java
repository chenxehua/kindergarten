package com.kgms.student.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

/**
 * 学生信息DTO
 */
@Data
public class StudentDTO {

    /** 学生姓名 */
    @NotBlank(message = "学生姓名不能为空")
    private String studentName;

    /** 性别 */
    @NotNull(message = "性别不能为空")
    private Integer gender;

    /** 出生日期 */
    private LocalDate birthday;

    /** 身份证号 */
    private String idCard;

    /** 头像URL */
    private String avatar;

    /** 班级ID */
    private String classId;

    /** 入园日期 */
    private LocalDate enrollDate;

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
}
