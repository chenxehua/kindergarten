package com.kgms.classs.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_class_info")
public class ClassInfo extends BaseEntity {
    private String classId;
    private String kgId;
    private String className;
    private String grade;
    private String headTeacherId;
    private Integer capacity;
    private Integer studentCount;
    private Integer status;
}
