package com.kgms.common.enums;

public enum VideoType {
    MONTHLY("monthly", "月度视频"),
    SEMESTER("semester", "学期视频"),
    YEAR("year", "学年视频"),
    ACTIVITY("activity", "活动视频");

    private final String code;
    private final String desc;

    VideoType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() { return code; }
    public String getDesc() { return desc; }

    public static VideoType getByCode(String code) {
        if (code == null) return null;
        for (VideoType t : values()) {
            if (t.getCode().equals(code)) return t;
        }
        return null;
    }
}
