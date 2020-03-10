/**
 * 
 */
package cn.wqdmy.wechat.util;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;

/**
 * IP客户端信息获取工具
 * @author wb_dmy
 * 时间：2017年2月4日上午9:08:25
 * @version 1.0
 */
public class IPGetInfoUtils {
	
	public static final String IPV4 = "ipv4";
	public static final String IPV6 = "ipv6";
	
	/**
	 * 获取客户端IP
	 * @param request
	 * @return
	 * @author wb_dmy
	 * 时间：2016年8月11日下午12:14:41
	 * @version 1.0
	 */
	public static String getClientIp(HttpServletRequest request){
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		//多个IP获取最后一个
		if(ip != null && ip.contains(",")) {
			String[] ips = ip.split(",");
			ip = ips[ips.length-1].trim();
		}
		return ip;
	}
	
	/**
	 * 获取eth1 的ipv4
	 * @return
	 */
	public static String getHostEth1Inet4Address(){
		String ipv4 = getHostEthInetAddress("eth1", IPV4);
		return ipv4;
	}
	
	/**
	 * 获取指定类型的ip
	 * @param eth
	 * @param ipType
	 * @return
	 */
	public static String getHostEthInetAddress(String eth, String ipType){
		Enumeration<InetAddress> inetAddresses = getHostEth(eth);
		if(inetAddresses != null){
			while (inetAddresses.hasMoreElements()) {
				InetAddress inetAddress = inetAddresses.nextElement();
				if(IPV6.equals(ipType)){
					if(inetAddress instanceof Inet6Address){
						return inetAddress.getHostAddress();
					}
				}else{
					if(inetAddress instanceof Inet4Address){
						return inetAddress.getHostAddress();
					}
				}
				
			}
		}
		return null;
	}
	
	/**
	 * 获取网卡的地址集
	 * @param eth
	 * @return
	 */
	public static Enumeration<InetAddress> getHostEth(String eth){
		try {
			Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
			while (nets.hasMoreElements()) {
				NetworkInterface netint = nets.nextElement();
				String displayName = netint.getDisplayName();
				if(displayName.equals(eth)){
					return netint.getInetAddresses();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return null;
	}
	
	
	/**
	 * 获取ip地址信息
	 * @param ip
	 * @return {"country":"中国","country_id":"CN","area":"华南","area_id":"800000","region":"广东省","region_id":"440000","city":"茂名市","city_id":"440900","county":"","county_id":"-1","isp":"电信","isp_id":"100017","ip":"125.94.170.95"}
	 */
	public static JSONObject getIpInfo(String ip){
		return getIpInfo("http://ip.taobao.com/service/getIpInfo.php?ip={IP}", ip);
	}
	
	/**
	 * 获取ip地址信息
	 * @param servreUrl
	 * @param ip
	 * @return {"country":"中国","country_id":"CN","area":"华南","area_id":"800000","region":"广东省","region_id":"440000","city":"茂名市","city_id":"440900","county":"","county_id":"-1","isp":"电信","isp_id":"100017","ip":"125.94.170.95"}
	 */
	public static JSONObject getIpInfo(String servreUrl, String ip){
		JSONObject info = new JSONObject();
		try {
			String doc = ApiWebUtils.doGet(servreUrl.replace("{IP}", ip), null);
			if (doc != null) {
				info = JSONObject.parseObject(doc);
				if (info != null && info.getString("code").equals("0")) {
					info = info.getJSONObject("data");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return info;
	}
	
	/**
	 * 客户端类型 
	 * @param userAgent
	 * @return
	 */
	public static String getClientType(String userAgent){
		if(userAgent != null){
			String clientInfo = userAgent.toLowerCase();
			//chrome 为极速浏览器统一标志
			if(clientInfo.contains("android")){
				return "Android";
			}
			if (clientInfo.contains("iphone")) {
				return "iPhone";
			}
			if (clientInfo.contains("msie")) {
				return "MSIE";
			}
			if (clientInfo.contains("qqbrowser")) {
				return "QQBrowser";
			}
			if (clientInfo.contains("ubrowser")) {
				return "UBrowser";
			}
			if (clientInfo.contains("firefox")) {
				return "Firefox";
			}
			if (clientInfo.contains("chrome")) {
				return "chrome";
			}
		}
		return "other-client";
	}
	
}
