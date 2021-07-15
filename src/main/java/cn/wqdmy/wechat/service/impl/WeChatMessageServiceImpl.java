package cn.wqdmy.wechat.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wqdmy.wechat.aes.AesException;
import cn.wqdmy.wechat.api.AccessToken;
import cn.wqdmy.wechat.dao.domain.WechatApp;
import cn.wqdmy.wechat.dao.mapper.WechatAppMapper;
import cn.wqdmy.wechat.message.BaseMessage;
import cn.wqdmy.wechat.message.MessageEnum;
import cn.wqdmy.wechat.message.MessageXmlMapper;
import cn.wqdmy.wechat.message.event.EventEnum;
import cn.wqdmy.wechat.message.event.UserEventMessage;
import cn.wqdmy.wechat.message.request.LocationMessageRequest;
import cn.wqdmy.wechat.message.request.TextMessageRequest;
import cn.wqdmy.wechat.message.request.VoiceMessageRequest;
import cn.wqdmy.wechat.message.response.TextMessageResponse;
import cn.wqdmy.wechat.service.WeChatMessageService;
import cn.wqdmy.wechat.util.IPGetInfoUtils;
import cn.wqdmy.wechat.util.SHA1Utils;

@Service
public class WeChatMessageServiceImpl implements WeChatMessageService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private static String RESULT = "您好！微信号暂时只用于重要消息通知不提供咨询";

	private boolean isTransfer = false;
	
	/**
	 * 应用集合
	 */
	private Map<String, WechatApp> appMap = new HashMap<>();
	
	@Autowired
	private WechatAppMapper wechatAppMapper;
	
	
	/**
	 * 获取app应用
	 * @param appCode
	 * @return
	 */
	public WechatApp getWechatApp(String appCode) {
		if(!appMap.containsKey(appCode)) {
			synchronized (WeChatMessageServiceImpl.class) {
			  if(!appMap.containsKey(appCode)) { 
				  // 加载数据库引用
				  wechatAppMapper.findAll().forEach((app)->{
				  	appMap.put(app.getAppCode(), app); 
				  	try {
						MessageXmlMapper.createMsgCrypt(app.getAppToken(), app.getAppEncryptAesKey(), app.getAppId());
					} catch (AesException e) {
						logger.error(e.toString(), e);
					}
				}); 
			  }
			}
		}
		return appMap.get(appCode);
	}
	
	/**
	 * 获取凭证
	 * @return
	 */
	public String getAccessToken(String appCode){
		return AccessToken.api().getToken(getWechatApp(appCode));
	}

	/**
	 * 与平台验证签名 判断消息有效性
	 */
	@Override
	public boolean checkSignature(String signature, String timestamp, String nonce, String appCode) {
		if (signature == null || timestamp == null || nonce == null) {
			return false;
		}
		try {
			long ct = (System.currentTimeMillis() / 1000) - Long.valueOf(timestamp);
			if (ct > 600000) {// 时间误差超过10分钟视为无效
				logger.error("出现误差时间10分钟签名消息已被拦截");
				return false;
			}
			String result = SHA1Utils.toSHA1(getWechatApp(appCode).getAppToken(), timestamp, nonce);// 生成本地签名
			logger.debug("签名：{} =對比= {}", signature, result);
			return signature.equalsIgnoreCase(result);// 对比签名
		} catch (Exception e) {
			logger.error("签名异常：" + e.getMessage(), e);
		}
		return false;
	}

	/**
	 * 消息处理
	 */
	@Override
	public String handler(HttpServletRequest request) throws Exception {
		logger.info("微信平台服务器：{}", IPGetInfoUtils.getClientIp(request));
		boolean isEncrypt = "aes".equals(request.getParameter("encrypt_type"));
		WechatApp wechatApp = getWechatApp(request.getAttribute("appCode").toString());
		BaseMessage message = MessageXmlMapper.fromMessage(request, wechatApp.getAppToken(), isEncrypt);
		BaseMessage resultMessage = resultMessage(message, wechatApp);
		if (resultMessage != null) {
			return MessageXmlMapper.toWxXml(resultMessage, wechatApp.getAppToken(),  isEncrypt);
		}
		return "success";
	}

	
	/**
	 * 公众号消息处理
	 * @param message
	 * @return
	 */
	public BaseMessage resultMessage(BaseMessage message, WechatApp wechatApp) {
		BaseMessage resultMessage = null;
		MessageEnum messageEnum = MessageEnum.getEnum(message.getMsgType());
		// 事件消息处理
		if (MessageEnum.EVENT.equals(messageEnum)) {
			resultMessage = eventMsg(message);
		}
		// 消息转发客服
		if (isTransfer) {
			resultMessage = transferMsg(message);
			logger.info("客服转发：" + message.getFromUserName());
		} else {
			switch (messageEnum != null ? messageEnum : MessageEnum.TEXT) {
			case TEXT:
				resultMessage = textMsg(message);
				break;// 文本消息处理
			case IMAGE:
				resultMessage = imageMsg(message);
				break;// 图片消息处理
			case VOICE:
				resultMessage = voiceMsg(message);
				break;// 语音消息处理
			case VIDEO:
				resultMessage = videoMsg(message);
				break;// 视频消息处理
			case SHORTVIDEO:
				resultMessage = videoMsg(message);
				break;// 小视频消息处理
			case LOCATION:
				resultMessage = locationMsg(message);
				break;// 位置消息处理
			case LINK:
				resultMessage = linkMsg(message);
				break;// 连接消息处理
			case EVENT:
				resultMessage = eventMsg(message);
				break;// 事件消息处理
			default:
				break;// 未知消息处理
			}
		}
		return resultMessage;
	}

	/**
	 * 默认提示消息
	 * 
	 * @param message
	 * @return
	 */
	private BaseMessage defaultMsg(BaseMessage message) {
		return new TextMessageResponse(message, RESULT);
	}

	/**
	 * 消息转发
	 * 
	 * @param message
	 * @return
	 */
	private BaseMessage transferMsg(BaseMessage message) {
		return new BaseMessage(message, MessageEnum.TRANSFER_CUSTOMER_SERVICE);
	}

	/**
	 * 事件消息处理
	 * 
	 * @param message
	 * @return
	 */
	private BaseMessage eventMsg(BaseMessage message) {
		BaseMessage resultMessage = null;
		UserEventMessage eventMessage = (UserEventMessage) message;
		EventEnum eventEnum = EventEnum.getEnum(eventMessage.getEvent());
		switch (eventEnum != null ? eventEnum : EventEnum.UNDEFINED) {
		case SUBSCRIBE:
			resultMessage = subscribeEvent(eventMessage);
			break;// 订阅事件处理
		case UNSUBSCRIBE:
			resultMessage = unsubscribeEvent(eventMessage);
			break;// 取消订阅事件处理
		case SCAN:
			resultMessage = scanEvent(eventMessage);
			break;// 扫描带参数二维码事件处理
		case CLICK:
			resultMessage = clickEvent(eventMessage);
			break;// 点击事件处理
		case LOCATION:
			resultMessage = defaultMsg(message);
			break;// 上报位置事件处理
		case TEMPLATESENDJOBFINISH:
			resultMessage = templateSendJobFinish(eventMessage);
			break;// 模版任务结果事件处理
		case UNDEFINED:
			resultMessage = undefinedEvent(eventMessage);
			break;// 当前系统未知事件
		case VIEW:
			break;// 点击视图跳转
		}
		return resultMessage;
	}

	/**
	 * 当前系统未知事件
	 * 
	 * @param message
	 * @return
	 */
	private BaseMessage undefinedEvent(UserEventMessage message) {
		logger.error("当前系统未知事件->Event:" + message.getEvent());
		return null;
	}

	/**
	 * 订阅事件
	 * 
	 * @param eventMessage
	 * @return
	 */
	private BaseMessage subscribeEvent(UserEventMessage eventMessage) {
		logger.info(eventMessage.getFromUserName() + "订阅用户信息");
		return defaultMsg(eventMessage);
	}

	/**
	 * 取消订阅事件
	 * 
	 * @param eventMessage
	 * @return
	 */
	private BaseMessage unsubscribeEvent(UserEventMessage eventMessage) {
		logger.info(eventMessage.getFromUserName() + "取消订阅用户信息");
		return defaultMsg(eventMessage);
	}

	/**
	 * 模版消息推送事件
	 * 
	 * @param eventMessage
	 * @return
	 */
	private BaseMessage templateSendJobFinish(UserEventMessage eventMessage) {
		if (!"success".equals(eventMessage.getStatus())) {
			logger.error(eventMessage.getFromUserName() + ":" + eventMessage.getMsgId() + "->" + eventMessage.getStatus());
		}
		return null;
	}

	/**
	 * 扫描带参数二维码事件处理
	 * 
	 * @return
	 */
	private BaseMessage scanEvent(UserEventMessage eventMessage) {
		return new TextMessageResponse(eventMessage, RESULT);
	}

	/**
	 * 自定义点击事件处理
	 * 
	 * @param eventMessage
	 * @return
	 */
	private BaseMessage clickEvent(UserEventMessage eventMessage) {
		return new TextMessageResponse(eventMessage, RESULT);
	}

	/**
	 * 文本消息处理
	 * 
	 * @param message
	 * @return
	 */
	private BaseMessage textMsg(BaseMessage message) {
		TextMessageRequest textMessageRequest = (TextMessageRequest) message;
		logger.info("文本消息：" + textMessageRequest.getContent());
		return new TextMessageResponse(message, RESULT);
	}

	/**
	 * 图片消息处理
	 * 
	 * @param message
	 * @return
	 */
	private BaseMessage imageMsg(BaseMessage message) {
		return new TextMessageResponse(message, RESULT);
	}

	/**
	 * 语音消息处理
	 * 
	 * @param message
	 * @return
	 */
	private BaseMessage voiceMsg(BaseMessage message) {
		VoiceMessageRequest request = (VoiceMessageRequest) message;
		return new TextMessageResponse(message, "您好，您的语音内容为：" +request.getRecognition());
	}

	/**
	 * 视频消息处理
	 * 
	 * @param message
	 * @return
	 */
	private BaseMessage videoMsg(BaseMessage message) {
		return new TextMessageResponse(message, RESULT);
	}

	/**
	 * 地理位置消息处理
	 * 
	 * @param message
	 * @return
	 */
	private BaseMessage locationMsg(BaseMessage message) {
		LocationMessageRequest request = (LocationMessageRequest) message;
		return new TextMessageResponse(message, "您好，您当前所处：\n\r经度（" + request.getLocationY() + "）\n\r纬度（"
				+ request.getLocationX() + "）；\n\r地处：" + request.getLabel());
	}

	/**
	 * 连接消息处理
	 * 
	 * @param message
	 * @return
	 */
	private BaseMessage linkMsg(BaseMessage message) {
		return new TextMessageResponse(message, RESULT);
	}



}
