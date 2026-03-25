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

    private String studentId;
    private String studentName;
    private Integer gender;
    private String genderDesc;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private String idCard;
    private String avatar;
    private String classId;
    private String className;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate enrollDate;

    private Integer status;
    private String statusDesc;
    private String allergyInfo;
    private String medicalHistory;
    private String homeAddress;
    private String emergencyContact;
    private String emergencyPhone;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
