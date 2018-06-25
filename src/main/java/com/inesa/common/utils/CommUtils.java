package com.inesa.common.utils;

import java.math.BigDecimal;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import com.inesa.common.utils.JsonUtils;

public class CommUtils {

	// JSON格式化
	public static String printDataJason(HttpServletResponse response,
			Object item) {
		try {

			JsonUtils.renderString(response, item);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * 随机生成6位随机验证码
	 * 
	 */
	public static String createRandomVcode(int len) {
		// 验证码
		String vcode = "";
		for (int i = 0; i < len; i++) {
			vcode = vcode + (int) (Math.random() * 9);
		}
		return vcode;
	}

	// 数字判断
	public static boolean isNumeric(String str) {
		for (int i = 0; i < str.length(); i++) {			
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static boolean isDouble(String str) {
		if (null == str || "".equals(str)) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
		return pattern.matcher(str).matches();
	}

	public static boolean isDecimal(String str) {
		try {

			BigDecimal a = new BigDecimal(str);

		} catch (Exception e) {
			return false;
		}

		return true;
	}

}
