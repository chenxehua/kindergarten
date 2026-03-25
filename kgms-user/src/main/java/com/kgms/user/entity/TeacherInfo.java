package com.kgms.user.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;

/**
 * 教师信息实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_teacher_info")
public class TeacherInfo extends BaseEntity {
    /** 用户ID */
    private String userId;
    /** 教师编号 */
    private String teacherNo;
    /** 职位 */
    private String position;
    /** 部门 */
    private String department;
    /** 入职日期 */
    private LocalDate hireDate;
}
