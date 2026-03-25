package com.kgms.course.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_course")
public class Course extends BaseEntity {
    private String courseId;
    private String kgId;
    private String courseName;
    private String courseType;
    private String ageGroup;
    private String teacherId;
    private String courseDesc;
    private String courseGoal;
    private Integer duration;
    private Integer status;
}
