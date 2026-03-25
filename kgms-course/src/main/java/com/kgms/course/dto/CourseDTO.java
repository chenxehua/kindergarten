package com.kgms.course.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CourseDTO {
    private String kgId;
    @NotBlank(message = "课程名称不能为空")
    private String courseName;
    @NotBlank(message = "课程类型不能为空")
    private String courseType;
    private String ageGroup;
    private String teacherId;
    private String courseDesc;
    private String courseGoal;
    private Integer duration;
}
