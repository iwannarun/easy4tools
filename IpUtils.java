
package com.luckin.stock.utils;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * 描述:
 *
 * @author  boyce
 * @created 2015年5月18日 下午2:26:18
 * @since   v1.0.0
 */
public class IpUtils {
	public static String getUserIP(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
