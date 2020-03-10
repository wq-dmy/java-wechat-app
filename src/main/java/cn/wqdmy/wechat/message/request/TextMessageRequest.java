/**
 * 
 */
package cn.wqdmy.wechat.message.request;

import cn.wqdmy.wechat.message.BaseMessage;
import cn.wqdmy.wechat.message.annotation.FieldLable;

/**
 * 文本消息请求
 * @author wb_dmy
 * 类名：TextMessage
 * 时间：2015年10月31日上午10:24:30
 * @version 1.0
 */
public class TextMessageRequest extends BaseMessage {
	
	//消息id
	@FieldLable(value="MsgId")
	private String msgId;
	//文本内容
	@FieldLable(value="Content",isCDATA=true)
	private String content;
	

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
