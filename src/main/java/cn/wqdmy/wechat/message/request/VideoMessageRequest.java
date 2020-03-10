/**
 * 
 */
package cn.wqdmy.wechat.message.request;

import cn.wqdmy.wechat.message.BaseMessage;
import cn.wqdmy.wechat.message.annotation.FieldLable;

/**
 * 视频消息请求
 * @author wb_dmy
 * 类名：VideoMessageRequest
 * 时间：2015年11月3日下午12:25:57
 * @version 1.0
 */
public class VideoMessageRequest extends BaseMessage {

	//消息id
	@FieldLable(value="MsgId")
	private String msgId;
	//视频消息缩略图的媒体id
	@FieldLable(value="ThumbMediaId",isCDATA=true)
	private String thumbMediaId;
	//消息媒体id
	@FieldLable(value="MediaId",isCDATA=true)
	private String mediaId;
	
	
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getThumbMediaId() {
		return thumbMediaId;
	}
	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

}
