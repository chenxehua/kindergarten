package com.kgms.classs.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClassDTO {
    private String kgId;
    @NotBlank(message = "班级名称不能为空")
    private String className;
    @NotBlank(message = "年级不能为空")
    private String grade;
    private String headTeacherId;
    private Integer capacity;
}
