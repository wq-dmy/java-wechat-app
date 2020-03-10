/**
 * 
 */
package cn.wqdmy.wechat.message;

import cn.wqdmy.wechat.message.annotation.FieldLable;

/**
 * 加密消息类
 * @author wb_dmy
 * 类名：EncryptMessage
 * 时间：2015年11月2日下午2:35:28
 * @version 1.0
 */
public class EncryptMessage {

	@FieldLable(value=MessageXmlMapper.MESSAGE_ENCRYPT,isCDATA=true)
	private String encrypt;
	
	@FieldLable(value="MsgSignature",isCDATA=true)
	private String msgSignature;
	
	@FieldLable(value="TimeStamp")
	private String timeStamp;
	
	@FieldLable(value="Nonce",isCDATA=true)
	private String nonce;
	
	public EncryptMessage(){}
	
	public EncryptMessage(String encrypt,String msgSignature,String timeStamp,String nonce){
		this.encrypt = encrypt;
		this.msgSignature = msgSignature;
		this.timeStamp = timeStamp;
		this.nonce = nonce;
	}
	
	public String getEncrypt() {
		return encrypt;
	}
	public void setEncrypt(String encrypt) {
		this.encrypt = encrypt;
	}
	public String getMsgSignature() {
		return msgSignature;
	}
	public void setMsgSignature(String msgSignature) {
		this.msgSignature = msgSignature;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getNonce() {
		return nonce;
	}
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
	
	
}
