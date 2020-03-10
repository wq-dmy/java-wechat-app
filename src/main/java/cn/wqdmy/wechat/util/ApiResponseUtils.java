/**
 * 
 */
package cn.wqdmy.wechat.util;

import java.lang.reflect.Field;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author wb_dmy
 * 项目：weixinWeb
 * 包名：net.wonbao.weixin.util
 * 类名：ApiResponseUtils
 * 时间：2015年11月5日下午1:59:58
 * @version 1.0
 */
public class ApiResponseUtils {
	
	
	/**
	 * 填充数据
	 * @param object
	 * @param jsonObject
	 * @throws Exception
	 */
	public static void fillObject(Object object,JSONObject jsonObject,int modifiers) throws Exception{
		Field[] fields= object.getClass().getDeclaredFields();
		for (Field field : fields) {
			if(field.getModifiers() == modifiers){
				field.setAccessible(true);
				Object value = containsKey(field,jsonObject);
				if(value != null){
					field.set(object, value(field,value));
				}
			}
		}
	}
	
	/**
	 * 匹配
	 * @return
	 */
	public static Object containsKey(Field field,JSONObject jsonObject){
		for (Object k : jsonObject.keySet()) {
			if(field.getName().equalsIgnoreCase(k.toString().replace("_", ""))){
				return jsonObject.get(k);
			}
		}
		return null;
	}
	
	/**
	 * 数据类型对应
	 * @param field
	 * @param jsonObject
	 * @return
	 */
	public static Object value(Field field,Object value) {
		Object reslut = null;
		if(field.getType().isAssignableFrom(value.getClass())){
			reslut = value;
		}else if(field.getType().isAssignableFrom(Integer.class)){
			reslut = Integer.valueOf(value.toString());
		}else if (field.getType().isAssignableFrom(Long.class)) {
			reslut = Long.valueOf(value.toString());
		}else if (field.getType().isAssignableFrom(Double.class)) {
			reslut = Double.valueOf(value.toString());
		}else if (field.getType().isAssignableFrom(Boolean.class)) {
			reslut = Boolean.valueOf(value.toString());
		}else {
			reslut = value.toString();
		}
		return reslut;
	}
	
	/**
	 * 填充对象
	 * @param object
	 * @param jsonObject
	 * @throws Exception
	 */
	public static void fillObject(Object object,JSONObject jsonObject) throws Exception{
		fillObject(object,jsonObject,2);
	}
	
	
	

	

}
