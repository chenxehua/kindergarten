package com.kgms.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 老师信息表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_teacher_info")
public class TeacherInfo extends BaseEntity {

    /** 关联用户ID */
    private String userId;

    /** 工号 */
    private String teacherNo;

    /** 职位: 主班/副班/保育员/保健医 */
    private String position;

    /** 所属部门 */
    private String department;

    /** 入职日期 */
    private LocalDate hireDate;
}
