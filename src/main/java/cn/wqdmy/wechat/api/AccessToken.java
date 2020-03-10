/**
 * 
 */
package cn.wqdmy.wechat.api;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import cn.wqdmy.wechat.dao.domain.WechatApp;
import cn.wqdmy.wechat.util.ApiWebUtils;


/**
 * 获取凭证对象
 * @author wb_dmy
 * 时间：2015年11月2日下午5:00:22
 * @version 1.0
 */
public class AccessToken {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static AccessToken accessToken = new AccessToken();;
	
	private AccessToken(){}
	
	/**
	 * token对象key值
	 */
	public static final String ACCESS_TOKEN_KEY = "access_token";
	public static final String EXPIRES_IN_KEY  = "expires_in";
	
	/**
	 * 访问路径
	 */
	private static final String API_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET"; 
	
	private int count = 0;
	
	/**
	 * 获取access_token
	 * @param appid
	 * @param appsecret
	 * @return
	 */
	public JSONObject getToken(String appid,String appsecret){
		String url = API_URL.replace("APPID", appid).replace("APPSECRET", appsecret);
		try {
			String json = ApiWebUtils.doGet(url, null);
			JSONObject jsonObject = JSONObject.parseObject(json);
			if(jsonObject.containsKey("errcode")){
				throw new Exception();
			}
			return jsonObject;
		} catch (Exception e) {
			try {
				count++;
				if(count < 4){
					Thread.sleep(300);
					return getToken(appid,appsecret);
				}
			} catch (Exception e2) {}
		}
		return null;
	}
	
	/**
	 * 应用调用接口凭证(一天2000次，凭证生命2小时左右)
	 * 平台后台会保证在刷新短时间内，新老access_token都可用，这保证了第三方业务的平滑过渡
	 */
	private volatile static String access_token = null;
	/**
	 * 应用调用接口凭证刷新时间
	 */
	private volatile static long refresh_token_time = 0L;
	/**
	 * 应用调用接口凭证生命时长(秒)
	 */
	private volatile static long expires_in = 7200L;
	
	/**
	 * 获取access_token
	 * @return
	 */
	public String getToken(WechatApp wechatApp){
		long nowTime = System.currentTimeMillis();
		//当前时间距离上次刷新时间 超过过期时间
		if((nowTime - refresh_token_time) > (expires_in * 950)){
			synchronized (AccessToken.class) {
				if((nowTime - refresh_token_time) > (expires_in * 950)){
					JSONObject token = getToken(wechatApp.getAppId(), wechatApp.getAppSecret());;
					access_token = token.getString(AccessToken.ACCESS_TOKEN_KEY);
					expires_in = token.getLong(AccessToken.EXPIRES_IN_KEY);
					refresh_token_time = nowTime;
					logger.info("刷新应用凭证时间截：" + nowTime);
					logger.info("access_token：" + access_token);
				}
			}
		}
		return access_token;
	}

	/**
	 * 获取去实例
	 * @return
	 */
	public static AccessToken api(){
		return accessToken;
	}
}
