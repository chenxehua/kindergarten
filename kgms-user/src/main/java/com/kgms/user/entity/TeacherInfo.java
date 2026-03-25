package com.kgms.user.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_teacher_info")
public class TeacherInfo extends BaseEntity {
    private String userId;
    private String teacherNo;
    private String position;
    private String department;
    private LocalDate hireDate;
}
