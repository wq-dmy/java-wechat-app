/**
 * 
 */
package cn.wqdmy.wechat.message.response.sub;

import cn.wqdmy.wechat.message.annotation.FieldLable;

/**
 * 图文消息子类
 * @author wb_dmy
 * 类名：Article
 * 时间：2015年11月2日上午8:53:30
 * @version 1.0
 */
public class Article {

	//图文消息标题
	@FieldLable(value="Title",isCDATA=true)
	private String title;
	
	//图文消息描述
	@FieldLable(value="Description",isCDATA=true)
	private String description;
	
	//图片链接
	@FieldLable(value="PicUrl",isCDATA=true)
	private String picUrl;
	
	//点击图文消息跳转链接
	@FieldLable(value="Url",isCDATA=true)
	private String url;
	

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

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
}
