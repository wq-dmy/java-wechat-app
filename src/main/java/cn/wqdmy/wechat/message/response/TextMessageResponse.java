/**
 * 
 */
package cn.wqdmy.wechat.message.response;

import cn.wqdmy.wechat.message.BaseMessage;
import cn.wqdmy.wechat.message.MessageEnum;
import cn.wqdmy.wechat.message.annotation.FieldLable;

/**
 * 文本消息
 * @author wb_dmy
 * 类名：TextMessageResponse
 * 时间：2015年10月31日下午3:04:10
 * @version 1.0
 */
public class TextMessageResponse extends BaseMessage {
	
	//文本内容
	@FieldLable(value="Content",isCDATA=true)
	private String content;
	
	public TextMessageResponse(){}
	
	/**
	 * 响应自动对调构造
	 * @param message
	 * @param msgType
	 */
	public TextMessageResponse(BaseMessage message,String content){
		super(message,MessageEnum.TEXT);
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}
