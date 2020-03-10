/**
 * 
 */
package cn.wqdmy.wechat.message;

import cn.wqdmy.wechat.message.event.UserEventMessage;
import cn.wqdmy.wechat.message.request.ImageMessageRequest;
import cn.wqdmy.wechat.message.request.LinkMessageRequest;
import cn.wqdmy.wechat.message.request.LocationMessageRequest;
import cn.wqdmy.wechat.message.request.TextMessageRequest;
import cn.wqdmy.wechat.message.request.VideoMessageRequest;
import cn.wqdmy.wechat.message.request.VoiceMessageRequest;


/**
 * 消息类型枚举
 * @author wb_dmy
 * 类名：MessageEnum
 * 时间：2015年11月2日上午8:55:09
 * @version 1.0
 */
public enum MessageEnum {
	TEXT("text"),// 消息类型：文本
	IMAGE("image"),// 消息类型：图片
	VOICE("voice"),// 消息类型：语音
	VIDEO("video"),// 消息类型：视频
	SHORTVIDEO("shortvideo"),// 消息类型：小视频
	LOCATION("location"),// 请求消息类型：地理位置(用户端)
	LINK("link"),// 请求消息类型：链接(用户端)
	MUSIC("music"),// 消息类型：音乐(服务端)
	NEWS("news"),// 响应消息类型：图文(服务端)
	EVENT("event"),// 消息类型:事件
	TRANSFER_CUSTOMER_SERVICE("transfer_customer_service");// 响应转发客服(服务端)
	
	private final String type;
	
	private MessageEnum(String type) {
		this.type = type;
	}
	
	public String toString() {
		return this.type;
	}

	public String value() {
		return this.type;
	}
	
	/**
	 * 获取对应的枚举
	 * @param name
	 * @return
	 */
	public static MessageEnum getEnum(String name) {
		MessageEnum[] values = values();
	    for (MessageEnum value : values) {
	      if (value.value().equals(name)) {
	        return value;
	      }
	    }
	    return null;
	}
	
	/**
	 * 获取请求的消息对象(不提供获取响应消息对象)
	 * @param messageEnum
	 * @return
	 */
	public static BaseMessage getReqMessage(MessageEnum messageEnum){
		BaseMessage message = null;
		if(messageEnum != null){
			 switch (messageEnum) {
			 	case TEXT: message = new TextMessageRequest();break;
			 	case IMAGE: message = new ImageMessageRequest();break;
			 	case VOICE: message = new VoiceMessageRequest();break;
			 	case VIDEO: message = new VideoMessageRequest();break;
			 	case SHORTVIDEO:message = new VideoMessageRequest();break;
			 	case LOCATION: message = new LocationMessageRequest();break;
			 	case LINK: message = new LinkMessageRequest();break;
			 	case EVENT: message = new UserEventMessage();break;
			 	default:break;
			 }
		}else {
			System.err.println("MessageEnum.getReqMessage()：未匹配到到消息对象");
		}
		return message;
	}

}
