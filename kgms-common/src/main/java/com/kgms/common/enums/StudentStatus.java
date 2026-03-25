package com.kgms.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 学生状态枚举
 */
@Getter
@AllArgsConstructor
public enum StudentStatus {

    LEAVE(0, "离园"),
    IN_SCHOOL(1, "在园"),
    SUSPEND(2, "休学");

    private final Integer code;
    private final String desc;

    public static StudentStatus getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (StudentStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
}
