package com.kgms.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 视频类型枚举
 */
@Getter
@AllArgsConstructor
public enum VideoType {

    MONTHLY("monthly", "月度视频"),
    SEMESTER("semester", "学期视频"),
    YEAR("year", "学年视频"),
    ACTIVITY("activity", "活动视频");

    private final String code;
    private final String desc;

    public static VideoType getByCode(String code) {
        if (code == null) {
            return null;
        }
        for (VideoType type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
