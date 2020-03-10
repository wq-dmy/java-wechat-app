package cn.wqdmy.wechat.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <b>日期转换辅助类</b>
 */
public class DateUtils {
	private static String defaultDatePattern = "yyyy-MM-dd";

	/**
	 * 获得默认的 date pattern
	 */
	public static String getDatePattern() {
		return defaultDatePattern;
	}

	/**
	 * 返回预设Format的当前日期字符串
	 */
	public static String getToday() {
		Date today = new Date();
		return format(today);
	}

	/**
	 * 使用预设Format格式化Date成字符串
	 */
	public static String format(Date date) {
		return date == null ? "" : format(date, getDatePattern());
	}

	/**
	 * 使用参数Format格式化Date成字符串
	 */
	public static String format(Date date, String pattern) {
		return date == null ? "" : new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 使用参数Format将字符串转为Date
	 */
	public static Date parse(String strDate, String pattern)
			throws ParseException {
		return strDate == null || strDate.equals("") ? null
				: new SimpleDateFormat(pattern).parse(strDate);
	}

	/**
	 * 使用参数Format将字符串转为Date
	 */
	public static Date parse(String strDate) throws ParseException {
		return strDate == null || strDate.equals("") ? null
				: new SimpleDateFormat(defaultDatePattern).parse(strDate);
	}


	/**
	 * 在日期上增加或减去数个整月
	 */
	public static Date addMonth(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, n);
		return cal.getTime();
	}

	/**
	 * 在日期上增加n分钟
	 */
	public static Date addMinute(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, n);
		return cal.getTime();
	}
	
	/**
	 * 获取增加天数日期字符
	 * @param n
	 * @return
	 */
	public static String addDayStr(int n){
		return addDayStr(new Date(),n);
	}
	
	/**
	 * 获取增加天数日期字符
	 * @param date
	 * @param n
	 * @return
	 */
	public static String addDayStr(Date date, int n){
		return format(addDay(date,n));
	}


	/**
	 * 在日期上增加n个整天
	 */
	public static Date addDay(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, n);
		return cal.getTime();
	}

	/**
	 * 计算两个日期相隔的天数
	 */
	public static long cutDay(Date date1, Date date2) {
		long quot = date2.getTime() - date1.getTime();
		return quot / 1000 / 60 / 60 / 24;
	}
	
	public static long cutHours(Date date1, Date date2) {
		long quot = date2.getTime() - date1.getTime();
		return quot / 1000 / 60 / 60;
	}
	

}