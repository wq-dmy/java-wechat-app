/**
 * 
 */
package cn.wqdmy.wechat.message.event;

import cn.wqdmy.wechat.message.BaseMessage;
import cn.wqdmy.wechat.message.annotation.FieldLable;

/**
 * 事件消息
 * @author wb_dmy
 * 项目：weixinWeb
 * 包名：net.wonbao.weixin.message.event
 * 类名：UserEventMessage
 * 时间：2015年10月31日下午6:04:25
 * @version 2.0
 */
public class UserEventMessage extends BaseMessage {

	//事件
	@FieldLable(value="Event",isCDATA=true)
	private String event;
	//事件key,value
	@FieldLable(value="EventKey",isCDATA=true)
	private String eventKey;
	//二维码的ticket
	@FieldLable(value="Ticket",isCDATA=true)
	private String ticket;
	//地理位置纬度 
	@FieldLable(value="Latitude")
	private String latitude;
	//地理位置经度
	@FieldLable(value="Longitude")
	private String longitude;
	//地理位置精度
	@FieldLable(value="Precision")
	private String precision;
	//发送状态(模版发送)
	@FieldLable(value="Status",isCDATA=true)
	private String status;
	//消息id(模版发送)
	@FieldLable(value="MsgID")
	private String msgId;
	
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getEventKey() {
		return eventKey;
	}
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getPrecision() {
		return precision;
	}
	public void setPrecision(String precision) {
		this.precision = precision;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
	
}
