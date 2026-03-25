package com.kgms.course.dto;

import lombok.Data;

@Data
public class CourseVO {
    private String courseId;
    private String kgId;
    private String courseName;
    private String courseType;
    private String ageGroup;
    private String teacherId;
    private String courseDesc;
    private String courseGoal;
    private Integer duration;
}
