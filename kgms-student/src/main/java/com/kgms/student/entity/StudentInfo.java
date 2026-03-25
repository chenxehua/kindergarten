package com.kgms.student.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_student_info")
public class StudentInfo extends BaseEntity {
    private String studentId;
    private String studentName;
    private Integer gender;
    private LocalDate birthday;
    private String idCard;
    private String avatar;
    private String classId;
    private LocalDate enrollDate;
    private Integer status;
    private String allergyInfo;
    private String medicalHistory;
    private String heightWeight;
    private String homeAddress;
    private String emergencyContact;
    private String emergencyPhone;
}
