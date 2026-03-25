package com.kgms.course.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ScheduleDTO {

    @NotBlank(message = "班级ID不能为空")
    private String classId;

    @NotNull(message = "星期不能为空")
    private Integer weekDay;

    @NotBlank(message = "时间段不能为空")
    private String timeSlot;

    @NotBlank(message = "课程ID不能为空")
    private String courseId;

    private String teacherId;
    private String classroom;
}

@Data
class ScheduleVO {
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