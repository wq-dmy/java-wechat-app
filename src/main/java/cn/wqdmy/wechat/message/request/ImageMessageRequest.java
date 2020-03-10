/**
 * 
 */
package cn.wqdmy.wechat.message.request;

import cn.wqdmy.wechat.message.BaseMessage;
import cn.wqdmy.wechat.message.annotation.FieldLable;


/**
 * 图片消息请求
 * @author wb_dmy
 * 项目：weixinWeb
 * 包名：net.wonbao.weixin.message.request
 * 类名：ImageMessageRequest
 * 时间：2015年11月3日下午12:24:17
 * @version 1.0
 */
public class ImageMessageRequest extends BaseMessage {
	//消息id
	@FieldLable(value="MsgId")
	private String msgId;
	//消息图片路径
	@FieldLable(value="PicUrl",isCDATA=true)
	private String picUrl;
	//图片消息媒体id
	@FieldLable(value="MediaId",isCDATA=true)
	private String mediaId;

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
	
	

}
