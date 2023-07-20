package com.smallc.xiwenlejian.common.recommender.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yj8023xx
 * @version 1.0
 * @date 2023/7/14
 * @since com.smallc.xiwenlejian.common.recommender.constant
 */
public enum RecModelType {

    Embedding(1, "embedding"),
    NCF(2, "ncf"),
    xDeepFM(3, "xdeepfm");

    private static Map<Integer, RecModelType> types = new HashMap<>();
    private static Map<Integer, String> valToStr = new HashMap<>();
    private static Map<String, Integer> strToVal = new HashMap<>();
    private int value;
    private String str;

    static {
        for (RecModelType type : RecModelType.values()) {
            types.put(type.value, type);
            valToStr.put(type.value, type.str);
            strToVal.put(type.str, type.value);
        }
    }

    RecModelType(int value, String str) {
        this.value = value;
        this.str = str;
    }

    public static RecModelType getType(int value) {
        return types.get(value);
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return str;
    }

    public static int getValue(String str) {
        return strToVal.get(str);
    }

    public static String toString(int value) {
        return valToStr.get(value);
    }

}
