package com.kgms.course.dto;

import lombok.Data;

@Data
public class ScheduleVO {
    private String scheduleId;
    private String classId;
    private String className;
    private Integer weekDay;
    private String timeSlot;
    private String courseId;
    private String courseName;
    private String courseType;
    private String teacherId;
    private String teacherName;
    private String classroom;
}