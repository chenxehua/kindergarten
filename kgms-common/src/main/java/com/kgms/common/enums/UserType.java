package com.kgms.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户类型枚举
 */
@Getter
@AllArgsConstructor
public enum UserType {

    PARENT(1, "家长"),
    TEACHER(2, "老师"),
    PRINCIPAL(3, "园长"),
    ADMIN(4, "管理员");

    private final Integer code;
    private final String desc;

    public static UserType getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (UserType type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
