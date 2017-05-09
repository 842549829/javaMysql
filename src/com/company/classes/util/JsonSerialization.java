package com.company.classes.util;

import com.alibaba.fastjson.JSON;

public final class JsonSerialization {

    public static String toJson(Object obj) {
        String jsonString = JSON.toJSONString(obj);
        return jsonString;

    }
    /*
    * Apple a = JSON.parseObject(obj, Apple.class);
    */
    public static <T> T parentJson(String jsonString, Class<T> clazz) {
        return JSON.parseObject(jsonString, clazz);
    }
}
