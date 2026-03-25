package com.kgms.common.enums;

public enum VideoGenStatus {
    QUEUING(0, "排队中"),
    GENERATING(1, "生成中"),
    COMPLETED(2, "已完成"),
    FAILED(3, "失败");

    private final Integer code;
    private final String desc;

    VideoGenStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() { return code; }
    public String getDesc() { return desc; }

    public static VideoGenStatus getByCode(Integer code) {
        if (code == null) return null;
        for (VideoGenStatus s : values()) {
            if (s.getCode().equals(code)) return s;
        }
        return null;
    }
}
