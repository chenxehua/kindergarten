package com.kgms.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 视频生成状态枚举
 */
@Getter
@AllArgsConstructor
public enum VideoGenStatus {

    QUEUING(0, "排队中"),
    GENERATING(1, "生成中"),
    COMPLETED(2, "已完成"),
    FAILED(3, "失败");

    private final Integer code;
    private final String desc;

    public static VideoGenStatus getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (VideoGenStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
}
