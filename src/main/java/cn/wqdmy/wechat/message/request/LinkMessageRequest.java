package cn.wqdmy.wechat.message.request;

import cn.wqdmy.wechat.message.BaseMessage;
import cn.wqdmy.wechat.message.annotation.FieldLable;

/**
 * 链接消息请求
 * @author wb_dmy
 * 项目：weixinWeb
 * 包名：net.wonbao.weixin.message.request
 * 类名：LinkMessageRequest
 * 时间：2015年11月3日下午12:25:05
 * @version 1.0
 */
public class LinkMessageRequest extends BaseMessage {
	//消息id
	@FieldLable(value="MsgId")
	private String msgId;
	//标题
	@FieldLable(value="Title",isCDATA=true)
	private String title;
	//描述
	@FieldLable(value="Description",isCDATA=true)
	private String description;
	//URL
	@FieldLable(value="Url",isCDATA=true)
	private String url;
	
	
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
