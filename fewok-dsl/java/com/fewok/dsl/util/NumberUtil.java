package com.fewok.dsl.util;

import java.math.BigDecimal;

/**
 * @author notreami on 17/11/23.
 */
public class NumberUtil {
    public static String yuanToFenString(BigDecimal yuan) {
        return yuan.multiply(BigDecimal.valueOf(100)).setScale(0, BigDecimal.ROUND_HALF_UP).toPlainString();
    }

    public static String yuanToFenString(double yuan) {
        return BigDecimal.valueOf(yuan).multiply(BigDecimal.valueOf(100)).setScale(0, BigDecimal.ROUND_HALF_UP).toPlainString();
    }

    public static int yuanToFenInt(double yuan) {
        return BigDecimal.valueOf(yuan).multiply(BigDecimal.valueOf(100)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
    }

    public static int yuanToFenInt(BigDecimal yuan) {
        return yuan.multiply(BigDecimal.valueOf(100)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
    }

    public static double fenToYuanDouble(long fen) {
        return BigDecimal.valueOf(fen).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static BigDecimal fenToYuanBigDecimal(long fen) {
        return BigDecimal.valueOf(fen).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal multiplyToScale2(BigDecimal multiplicator, int multiplicand) {
        return multiplicator.multiply(BigDecimal.valueOf(multiplicand)).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
