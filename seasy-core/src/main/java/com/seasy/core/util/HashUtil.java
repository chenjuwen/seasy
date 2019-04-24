package com.seasy.core.util;

import java.security.MessageDigest;

import org.apache.commons.lang3.RandomStringUtils;

public class HashUtil {
	/**
	 * MD5算法：是一种散列函数，用以提供消息的完整性保护。
	 * 不可逆，存在“磕碰”现象
	 */
	public static String md5(String text){
		return doHash(text, "MD5");
	}
	
	/**
	 * SHA1算法：安全哈希算法（Secure Hash Algorithm）主要适用于数字签名标准里面定义的数字签名算法。
	 * 对于长度小于2^64位的消息，SHA1会产生一个160位的消息摘要。
	 * 
	 * 不可逆
	 */
	public static String sha1(String text){
		return doHash(text, "SHA1");
	}
	
	/**
	 * SHA-256算法
	 */
	public static String sha256(String text){
		return doHash(text, "SHA-256");
	}

	/**
	 * SHA-384算法
	 */
	public static String sha384(String text){
		return doHash(text, "SHA-384");
	}

	private static String doHash(String text, String algorithm) {
		if(StringUtil.isEmpty(text)){
			return "";
		}
		
		try{
			MessageDigest md = MessageDigest.getInstance(algorithm);
			byte[] bytes = md.digest(text.getBytes("UTF-8"));
			
			StringBuilder sb = new StringBuilder();
			for(byte b : bytes){
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                sb.append(temp);
            }
			return sb.toString();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return "";
	}
	
	public static void main(String[] args) {
		System.out.println(md5(RandomStringUtils.randomAlphanumeric(100000)));
		System.out.println(sha1(RandomStringUtils.randomAlphanumeric(1000000)));
		System.out.println(sha256(RandomStringUtils.randomAlphanumeric(1000000)));
		System.out.println(sha384(RandomStringUtils.randomAlphanumeric(1000000)));
		
//		System.out.println(RandomStringUtils.randomAlphabetic(5));
//		System.out.println(RandomStringUtils.randomAlphanumeric(5));
//		System.out.println(RandomStringUtils.randomAscii(5));
//		System.out.println(RandomStringUtils.randomNumeric(5));
//		System.out.println(RandomStringUtils.random(5, "0"));
	}
	
}
