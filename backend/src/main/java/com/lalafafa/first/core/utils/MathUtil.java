package com.lalafafa.first.core.utils;

import java.math.BigDecimal;

public class MathUtil {

    /**
     * 比较运算
     * 
     * @param d1
     * @param d2
     * @return double 运算结果
     */
    public static int compareTo(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.compareTo(b2);
    }

    /**
     * 加法运算
     * 
     * @param d1
     * @param d2
     * @return double 运算结果
     */
    public static double add(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.add(b2).doubleValue();
    }

    /**
     * 减法运算
     * 
     * @param d1
     * @param d2
     * @return double 运算结果
     */
    public static double sub(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 乘法运算
     * 
     * @param d1
     * @param d2
     * @return double 运算结果
     */
    public static double mul(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 除法运算
     * 
     * @param d1
     * @param d2
     * @param len
     * @return double 运算结果
     */
    public static double div(double d1, double d2, int len) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 四舍五入
     * 
     * @param d
     * @param len
     * @return double 运算结果
     */
    public static double round(double d, int len) {
        BigDecimal b1 = new BigDecimal(d);
        BigDecimal b2 = new BigDecimal(1);
        // 任何一个数字除以1都是原数字
        // ROUND_HALF_UP是BigDecimal的一个常量， 表示进行四舍五入的操作
        return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}