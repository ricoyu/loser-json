package com.loserico.json.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * <p>
 * Copyright: Copyright (c) 2019-10-15 9:54
 * <p>
 * Company: Sexy Uncle Inc.
 * <p>
 *
 * @author Rico Yu  ricoyu520@gmail.com
 * @version 1.0
 */
public class MathUtils {

	/**
	 * 四舍五入保留小数点后precision位
	 *
	 * @param v
	 * @param precision
	 * @return
	 */
	public static String format2Currency(BigDecimal v, int precision) {
		if (v == null) {
			v = BigDecimal.ZERO;
		}
		if (precision < 0) {
			throw new IllegalArgumentException("precision不能为负数");
		}
		StringBuilder format = new StringBuilder(v.compareTo(BigDecimal.ZERO) == 0 ? "0" : ",000");
		if (precision > 0) {
			format.append(".");
			for (int i = 0; i < precision; i++) {
				format.append("0");
			}
		}
		DecimalFormat df = new DecimalFormat(format.toString());
		return df.format(v);
	}
}