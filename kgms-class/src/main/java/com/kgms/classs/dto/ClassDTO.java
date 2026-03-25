package com.kgms.classs.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 班级DTO
 */
@Data
public class ClassDTO {
    /** 幼儿园ID */
    private String kgId;
    /** 班级名称 */
    @NotBlank(message = "班级名称不能为空")
    private String className;
    /** 年级 */
    @NotBlank(message = "年级不能为空")
    private String grade;
    /** 班主任ID */
    private String headTeacherId;
    /** 容量 */
    private Integer capacity;
}
