package cn.wqdmy.wechat.dao.domain;

public class WechatApp {
	
	private String appId;
	private String appToken;
	private String appSecret;
	private String appCode;
	private String appName;
	private String appEncrypt;
	private String appEncryptAesKey;
	private Integer appStatus;
	private Integer isIpWhite;
	private String remark;
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppToken() {
		return appToken;
	}
	public void setAppToken(String appToken) {
		this.appToken = appToken;
	}
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppEncrypt() {
		return appEncrypt;
	}
	public void setAppEncrypt(String appEncrypt) {
		this.appEncrypt = appEncrypt;
	}
	public String getAppEncryptAesKey() {
		return appEncryptAesKey;
	}
	public void setAppEncryptAesKey(String appEncryptAesKey) {
		this.appEncryptAesKey = appEncryptAesKey;
	}
	public Integer getAppStatus() {
		return appStatus;
	}
	public void setAppStatus(Integer appStatus) {
		this.appStatus = appStatus;
	}
	public Integer getIsIpWhite() {
		return isIpWhite;
	}
	public void setIsIpWhite(Integer isIpWhite) {
		this.isIpWhite = isIpWhite;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

}
