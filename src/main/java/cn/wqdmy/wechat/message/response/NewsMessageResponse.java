/**
 * 
 */
package cn.wqdmy.wechat.message.response;

import java.util.List;

import cn.wqdmy.wechat.message.BaseMessage;
import cn.wqdmy.wechat.message.MessageEnum;
import cn.wqdmy.wechat.message.annotation.FieldLable;
import cn.wqdmy.wechat.message.response.sub.Article;

/**
 * 图文消息
 * @author wb_dmy
 * 类名：NewsMessageResponse
 * 时间：2015年11月2日上午8:54:12
 * @version 1.0
 */
public class NewsMessageResponse extends BaseMessage {

	//图文消息个数
	@FieldLable(value="ArticleCount")
	private Integer articleCount;
	
	//图文消息信息的集合，最多不能超过10条
	@FieldLable(value="Articles",isDepth=true,itemName="item")
	private List<Article>  articles;
	
	public NewsMessageResponse(){}
	
	/**
	 * 自动对调发送方
	 * @param message
	 * @param articles
	 */
	public NewsMessageResponse(BaseMessage message,List<Article> articles){
		super(message, MessageEnum.NEWS);
		this.articleCount = articles.size();
		this.articles = articles;
	}

	public int getArticleCount() {
		return articleCount;
	}

	public void setArticleCount(int articleCount) {
		this.articleCount = articleCount;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

}
