package com.kgms.student.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 学生信息实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_student_info")
public class StudentInfo extends BaseEntity {
    /** 学生ID */
    private String studentId;
    /** 学生姓名 */
    private String studentName;
    /** 性别 */
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
    /** 状态 */
    private Integer status;
    /** 过敏信息 */
    private String allergyInfo;
    /** 既往病史 */
    private String medicalHistory;
    /** 身高体重 */
    private String heightWeight;
    /** 家庭住址 */
    private String homeAddress;
    /** 紧急联系人 */
    private String emergencyContact;
    /** 紧急联系电话 */
    private String emergencyPhone;
}
