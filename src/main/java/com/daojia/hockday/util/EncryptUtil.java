package com.daojia.hockday.util;

import java.math.BigInteger;


/**
 * @author by Dawei on 2018/8/23.
 * 可逆的加密解密
 */
public class EncryptUtil {

	private static final int RADIX = 16;
	/* 加密盐 */
	private static final String SEED = "835459200000";

	/* 加密 */
	public static String encrypt(String password) {
		if (password == null || password.length() == 0) {
			return "";
		}
		BigInteger bytePasswd = new BigInteger(password.getBytes());
		BigInteger turn0 = new BigInteger(SEED);
		BigInteger turn1 = turn0.xor(bytePasswd);
		return turn1.toString(RADIX);
	}

	/* 解密 */
	public static String decrypt(String encrypted) {
		if (encrypted == null || encrypted.length() == 0) {
			return "";
		}
 		BigInteger seedConfuse = new BigInteger(SEED);
 		try {
			BigInteger turn1 = new BigInteger(encrypted, RADIX);
			BigInteger turn0 = turn1.xor(seedConfuse);
			return new String(turn0.toByteArray());
		} catch (Exception e) {
			return "";
		}
	}

	
}
