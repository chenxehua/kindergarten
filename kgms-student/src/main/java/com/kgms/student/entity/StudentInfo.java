package com.kgms.student.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 学生信息表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_student_info")
public class StudentInfo extends BaseEntity {

    /** 学生ID */
    private String studentId;

    /** 学生姓名 */
    private String studentName;

    /** 性别: 1-男 2-女 */
    private Integer gender;

    /** 出生日期 */
    private LocalDate birthday;

    /** 身份证号 */
    private String idCard;

    /** 头像URL */
    private String avatar;

    /** 当前班级ID */
    private String classId;

    /** 入园日期 */
    private LocalDate enrollDate;

    /** 状态: 0-离园 1-在园 2-休学 */
    private Integer status;

    /** 过敏信息(JSON) */
    private String allergyInfo;

    /** 既往病史(JSON) */
    private String medicalHistory;

    /** 身高体重记录(JSON) */
    private String heightWeight;

    /** 家庭住址 */
    private String homeAddress;

    /** 紧急联系人 */
    private String emergencyContact;

    /** 紧急联系电话 */
    private String emergencyPhone;
}
