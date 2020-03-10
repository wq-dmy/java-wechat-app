/**
 * 
 */
package cn.wqdmy.wechat.message;

import cn.wqdmy.wechat.message.annotation.FieldLable;

/**
 * 消息事件基础类
 * @author wb_dmy
 * 项目：weixinWeb
 * 包名：net.wonbao.weixin.message
 * 类名：BaseMessage
 * 时间：2015年10月31日上午9:56:44
 * @version 1.0
 */
public class BaseMessage {

	//接收方帐号
	@FieldLable(value=MessageXmlMapper.MESSAGE_TOUSERNAME,isCDATA=true)
	private String toUserName;
	//发送方帐号
	@FieldLable(value="FromUserName",isCDATA=true)
	private String fromUserName;
	//消息创建时间 （整型）
	@FieldLable(value="CreateTime")
	private String createTime;
	//消息类型
	@FieldLable(value=MessageXmlMapper.MESSAGE_TYPE,isCDATA=true)
	private String msgType;
	
	
	public BaseMessage(){}
	
	/**
	 * 调换消息发送对象
	 * @param message
	 * @param msgType
	 */
	public BaseMessage(BaseMessage message,MessageEnum messageEnum){
		this.toUserName = message.getFromUserName();
		this.fromUserName = message.getToUserName();
		this.createTime = System.currentTimeMillis() + "";
		this.msgType = messageEnum.value();
	}
	
	
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	
}
