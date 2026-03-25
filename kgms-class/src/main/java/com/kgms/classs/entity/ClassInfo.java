package com.kgms.classs.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 班级信息实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_class_info")
public class ClassInfo extends BaseEntity {
    /** 班级ID */
    private String classId;
    /** 幼儿园ID */
    private String kgId;
    /** 班级名称 */
    private String className;
    /** 年级 */
    private String grade;
    /** 班主任ID */
    private String headTeacherId;
    /** 容量 */
    private Integer capacity;
    /** 学生数量 */
    private Integer studentCount;
    /** 状态 */
    private Integer status;
}
