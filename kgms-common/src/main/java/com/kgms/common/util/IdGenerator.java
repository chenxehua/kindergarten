package com.kgms.common.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * ID生成器
 */
public class IdGenerator {

    private static final Snowflake snowflake = IdUtil.getSnowflake(1, 1);

    /**
     * 生成ID
     */
    public static Long generateId() {
        return snowflake.nextId();
    }

    /**
     * 生成字符串ID
     */
    public static String generateStrId() {
        return snowflake.nextIdStr();
    }

    /**
     * 生成指定前缀的ID
     */
    public static String generateIdWithPrefix(String prefix) {
        return prefix + snowflake.nextIdStr();
    }
}
