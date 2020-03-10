/**
 * 
 */
package cn.wqdmy.wechat.message.request;

import cn.wqdmy.wechat.message.BaseMessage;
import cn.wqdmy.wechat.message.annotation.FieldLable;

/**
 * 地理位置消息请求
 * @author wb_dmy
 * 类名：LocationMessageRequest
 * 时间：2015年11月3日下午12:25:29
 * @version 1.0
 */
public class LocationMessageRequest extends BaseMessage {
	
	//消息id
	@FieldLable(value="MsgId")
	private String msgId;
	//地图缩放大小 
	@FieldLable(value="Scale")
	private String scale;
	//地理位置维度 
	@FieldLable(value="Location_X")
	private String locationX;
	//地理位置经度
	@FieldLable(value="Location_Y")
	private String locationY;
	//地理位置信息
	@FieldLable(value="Label",isCDATA=true)
	private String label;
	
	
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getScale() {
		return scale;
	}
	public void setScale(String scale) {
		this.scale = scale;
	}
	public String getLocationX() {
		return locationX;
	}
	public void setLocationX(String locationX) {
		this.locationX = locationX;
	}
	public String getLocationY() {
		return locationY;
	}
	public void setLocationY(String locationY) {
		this.locationY = locationY;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	
}
