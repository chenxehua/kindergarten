package com.kgms.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 性别枚举
 */
@Getter
@AllArgsConstructor
public enum Gender {

    MALE(1, "男"),
    FEMALE(2, "女");

    private final Integer code;
    private final String desc;

    public static Gender getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (Gender gender : values()) {
            if (gender.getCode().equals(code)) {
                return gender;
            }
        }
        return null;
    }
}
