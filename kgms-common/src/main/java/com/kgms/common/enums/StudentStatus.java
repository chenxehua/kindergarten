package com.kgms.common.enums;

public enum StudentStatus {
    LEAVE(0, "离园"),
    IN_SCHOOL(1, "在园"),
    SUSPEND(2, "休学");

    private final Integer code;
    private final String desc;

    StudentStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() { return code; }
    public String getDesc() { return desc; }

    public static StudentStatus getByCode(Integer code) {
        if (code == null) return null;
        for (StudentStatus s : values()) {
            if (s.getCode().equals(code)) return s;
        }
        return null;
    }
}
