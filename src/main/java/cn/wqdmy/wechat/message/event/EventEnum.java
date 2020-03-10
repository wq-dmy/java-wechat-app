/**
 * 
 */
package cn.wqdmy.wechat.message.event;


/**
 * 事件枚举
 * @author wb_dmy
 * 类名：EventEnum
 * 时间：2015年11月2日上午8:52:37
 * @version 1.0
 */
public enum EventEnum {
	SUBSCRIBE("subscribe"),// 事件类型：subscribe(订阅)
	UNSUBSCRIBE("unsubscribe"),// 事件类型：unsubscribe(取消订阅)
	SCAN("SCAN"),// 事件类型：scan(用户已关注时的扫描带参数二维码)
	CLICK("CLICK"),// 事件类型：CLICK(自定义菜单)
	VIEW("VIEW"),// 事件类型：VIEW(自定义菜单)
	LOCATION("LOCATION"),// 事件类型：LOCATION(上报地理位置)
	TEMPLATESENDJOBFINISH("TEMPLATESENDJOBFINISH"),//事件类型：消息模版推送结果
	UNDEFINED("undefined");// 事件类型：undefined(系统未知事件)
	
	
	private final String type;
	
	private EventEnum(String type) {
		this.type = type;
	}
	
	public String toString() {
		return this.type;
	}

	public String value() {
		return this.type;
	}
	
	/**
	 * 获取对应的枚举
	 * @param name
	 * @return
	 */
	public static EventEnum getEnum(String name) {
		EventEnum[] values = values();
	    for (EventEnum value : values) {
	      if (value.value().equals(name)) {
	        return value;
	      }
	    }
	    return null;
	}
}
