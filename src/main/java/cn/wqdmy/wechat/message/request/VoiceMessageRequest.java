/**
 * 
 */
package cn.wqdmy.wechat.message.request;

import cn.wqdmy.wechat.message.BaseMessage;
import cn.wqdmy.wechat.message.annotation.FieldLable;

/**
 * 语音消息
 * @author wb_dmy
 * 项目：weixinWeb
 * 包名：net.wonbao.weixin.message.request
 * 类名：VoiceMessageRequest
 * 时间：2015年11月3日下午12:26:17
 * @version 1.0
 */
public class VoiceMessageRequest extends BaseMessage {

	//消息id
	@FieldLable(value="MsgId")
	private String msgId;
	//语音格式，如amr，speex
	@FieldLable(value="Format",isCDATA=true)
	private String format;
	//消息媒体id
	@FieldLable(value="MediaId",isCDATA=true)
	private String mediaId;
	//语音识别信息
	@FieldLable(value="Recognition",isCDATA=true)
	private String recognition;
	
	
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getRecognition() {
		return recognition;
	}
	public void setRecognition(String recognition) {
		this.recognition = recognition;
	}
	

}
