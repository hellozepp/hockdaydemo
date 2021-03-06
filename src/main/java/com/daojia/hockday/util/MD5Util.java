package com.daojia.hockday.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.security.MessageDigest;


public class MD5Util {

	private static final Logger logger = LoggerFactory.getLogger(MD5Util.class);

	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = origin;
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes(charsetname)));
		} catch (Exception exception) {
		    logger.error("MDBEncode error", exception);
		}
		return resultString;
	}

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };





	public static String userToken(String token) {
		if (!StringUtils.isEmpty(token)) {
			String tempToken = token;
			int length = token.length();
			if(length > 17) {
				tempToken = token.substring(length - 17, length);
			}
			return  MD5Encode(tempToken, "utf-8");
		}
		return "12345678";
	}
}
