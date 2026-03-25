package com.kgms.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 成长记录状态枚举
 */
@Getter
@AllArgsConstructor
public enum RecordStatus {

    DRAFT(0, "草稿"),
    PUBLISHED(1, "已发布");

    private final Integer code;
    private final String desc;

    public static RecordStatus getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (RecordStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
}
