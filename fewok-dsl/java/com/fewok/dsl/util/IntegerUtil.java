package com.fewok.dsl.util;

/**
 * @author notreami on 17/11/23.
 */
public class IntegerUtil {
    public static int toInt(Integer integer, int... defaultValue) {
        if (integer == null) {
            if (defaultValue == null || defaultValue.length == 0) {
                return 0;
            }
            return defaultValue[0];
        }
        return integer.intValue();
    }
}
