package cn.wqdmy.wechat.util;

import java.security.MessageDigest;

/**
 * @author Administrator
 *
 */
public class MD5Utils {

	/**
	 * Md5加密
	 * @param str
	 * @return
	 */
	public static String toMD5(String str){
		String result = null;
		try { 
			if(str == null){return result;}
			MessageDigest md = MessageDigest.getInstance("MD5"); 
			md.update(str.getBytes()); 
			byte b[] = md.digest(); 
			int i; 
			StringBuffer buf = new StringBuffer(""); 
			for (int offset = 0; offset < b.length; offset++) { 
				i = b[offset]; 
				if(i<0) i+= 256; if(i<16) buf.append("0"); 
				buf.append(Integer.toHexString(i)); 
			}
			result = buf.toString();
		} catch (Exception e) { 
			e.printStackTrace(); 
		} 
		return result;
	}
}
