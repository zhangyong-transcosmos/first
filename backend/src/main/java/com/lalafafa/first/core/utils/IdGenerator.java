package com.lalafafa.first.core.utils;

import java.util.UUID;

public class IdGenerator {

    /**
     * 生成唯一值，多用于生成主键
     * 
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}