package com.kgms.user.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * 孩子列表VO
 */
@Data
public class ChildVO {
    private String studentId;
    private String studentName;
    private String avatar;
    private String className;
    private String classId;
    private Integer status;
}
