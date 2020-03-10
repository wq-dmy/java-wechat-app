/**
 * 
 */
package cn.wqdmy.wechat.message.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wb_dmy
 * 类名：FieldLable
 * 时间：2015年10月31日上午10:08:34
 * @version 1.0
 */
@Documented
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldLable {
	
	/**
	 * xml节点名称
	 * @return
	 */
	public String value() default "";
	
	/**
	 * 有可能包含特殊字符的 用CDATA包裹
	 * @return
	 */
	public boolean isCDATA() default false;
	
	/**
	 * 生成xml字段内容是否深度解析
	 * @return
	 */
	public boolean isDepth() default false;
	
	/**
	 * 生成xml字段是集合子项根节点名
	 * @return
	 */
	public String itemName() default "item";
}
