package xn.core.util.bigDeciml;

import java.math.BigDecimal;
import java.math.MathContext;

public class BigDecimalUtil {
    // 精确计算（加法）
    public static Double bigDecimalAdd(Double d1, Double d2) {
        BigDecimal b1 = BigDecimal.valueOf(d1);
        BigDecimal b2 = BigDecimal.valueOf(d2);
        return b1.add(b2).doubleValue();
    }

    // 精确计算（减法）
    public static Double bigDecimalSubtract(Double d1, Double d2) {
        BigDecimal b1 = BigDecimal.valueOf(d1);
        BigDecimal b2 = BigDecimal.valueOf(d2);
        return b1.subtract(b2).doubleValue();
    }

    // 精确计算（乘法）
    public static Double bigDecimalMultiply(Double d1, Double d2) {
        BigDecimal b1 = BigDecimal.valueOf(d1);
        BigDecimal b2 = BigDecimal.valueOf(d2);
        return b1.multiply(b2).doubleValue();
    }

    // 精确计算（计算百分比（已加百分号））
    public static Double bigDecimalDividePercent(Double d1, Double d2, MathContext mc) {
        if (d2 == 0D) {
            return 100D;
        }
        BigDecimal b1 = BigDecimal.valueOf(d1);
        BigDecimal b2 = BigDecimal.valueOf(d2);
        return b1.divide(b2, mc).multiply(new BigDecimal("100")).doubleValue();
    }

    // 精确计算（保留整数）
    public static Double bigDecimalDivide(Double d1, Double d2) {
        return bigDecimalDivide(d1, d2, 0);
    }

    // 精确计算（除法 scale指定小数）
    public static Double bigDecimalDivide(Double d1, Double d2, int scale) {
        BigDecimal b1 = BigDecimal.valueOf(d1);
        BigDecimal b2 = BigDecimal.valueOf(d2);
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}