/**
 * 
 */
package cn.wqdmy.wechat.service;

import javax.servlet.http.HttpServletRequest;

import cn.wqdmy.wechat.dao.domain.WechatApp;

/**
 * 微信公众号消息处理服务
 * @author dmy
 *
 */
public interface WeChatMessageService {
	
	/**
	 * 获取app配置信息
	 * @param appCode
	 * @return
	 */
	public WechatApp getWechatApp(String appCode);
	
	/**
	 * 获取app对应的接口令牌
	 * @param appCode
	 * @return
	 */
	public String getAccessToken(String appCode);

	/**
	 * 与平台验证签名 判断消息有效性
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param appCode
	 * @return
	 */
	public boolean checkSignature(String signature, String timestamp, String nonce, String appCode);
	
	/**
	 * 消息体解析处理
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String handler(HttpServletRequest request) throws Exception;
}
