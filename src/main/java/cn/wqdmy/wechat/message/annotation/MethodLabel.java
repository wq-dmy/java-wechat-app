package cn.wqdmy.wechat.message.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 增强方法信息标记
 * @author wb_dmy
 * 类名：MethodLabel
 * 时间：2015年5月13日上午9:45:39
 * @version 1.0
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodLabel {
	/**
	 * 方法安全级别
	 * @return
	 */
	public int level() default 0;
	/**
	 * 方法说明
	 * @return
	 */
	public String value() default "";
	/**
	 * 是否计入统计
	 * @return
	 */
	public boolean isCount() default false;
	/**
	 * 是否记录日志
	 * @return
	 */
	public boolean isLog() default false;
}
