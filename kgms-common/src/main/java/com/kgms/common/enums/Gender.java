package com.kgms.common.enums;

public enum Gender {
    MALE(1, "男"),
    FEMALE(2, "女");

    private final Integer code;
    private final String desc;

    Gender(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() { return code; }
    public String getDesc() { return desc; }

    public static Gender getByCode(Integer code) {
        if (code == null) return null;
        for (Gender g : values()) {
            if (g.getCode().equals(code)) return g;
        }
        return null;
    }
}
