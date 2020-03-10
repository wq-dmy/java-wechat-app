/**
 * 
 */
package cn.wqdmy.wechat.util;

import java.security.MessageDigest;
import java.util.Arrays;

import cn.wqdmy.wechat.aes.AesException;

/**
 * 微信签名加密
 * @author wb_dmy
 * 时间：2015年11月2日下午2:07:56
 * @version 1.0
 */
public class SHA1Utils {
	
	
	/**
	 * sha1基础加密方法
	 * @param strs
	 * @return
	 * @throws AesException
	 */
	public static String sha1(String... strs) throws AesException{
		try {
			StringBuffer buffer = new StringBuffer();
			// 字符串排序
			Arrays.sort(strs);
			for (int i = 0; i < strs.length; i++) {
				buffer.append(strs[i]);
			}
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(buffer.toString().getBytes());
			byte[] digest = md.digest();
			StringBuffer hexstr = new StringBuffer();
			String shaHex = "";
			for (int i = 0; i < digest.length; i++) {
				shaHex = Integer.toHexString(digest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexstr.append(0);
				}
				hexstr.append(shaHex);
			}
			return hexstr.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AesException(AesException.ComputeSignatureError);
		}
	}
	
	/**
	 * 用SHA1算法生成安全签名
	 * @param token 票据
	 * @param timestamp 时间戳
	 * @param nonce 随机字符串
	 * @param encrypt 密文
	 * @return 安全签名
	 * @throws AesException 
	 */
	public static String toSHA1(String token, String timestamp, String nonce, String encrypt) throws AesException{
		return sha1(token,timestamp,nonce,encrypt);
	}
	
	/**
	 * 用SHA1算法生成安全签名
	 * @param token
	 * @param timestamp
	 * @param nonce
	 * @return
	 * @throws AesException 
	 */
	public static String toSHA1(String token, String timestamp, String nonce) throws AesException{
		return sha1(token,timestamp,nonce);
	}
	
	
}
