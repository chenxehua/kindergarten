package com.kgms.common.enums;

public enum RecordStatus {
    DRAFT(0, "草稿"),
    PUBLISHED(1, "已发布");

    private final Integer code;
    private final String desc;

    RecordStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() { return code; }
    public String getDesc() { return desc; }

    public static RecordStatus getByCode(Integer code) {
        if (code == null) return null;
        for (RecordStatus r : values()) {
            if (r.getCode().equals(code)) return r;
        }
        return null;
    }
}
