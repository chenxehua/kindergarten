package com.kgms.classs.dto;

import lombok.Data;

@Data
public class ClassVO {
    private String classId;
    private String kgId;
    private String className;
    private String grade;
    private String headTeacherId;
    private Integer capacity;
    private Integer studentCount;
    private Integer status;
}
