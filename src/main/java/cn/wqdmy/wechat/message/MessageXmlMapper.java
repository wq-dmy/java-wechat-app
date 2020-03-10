package cn.wqdmy.wechat.message;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.wqdmy.wechat.aes.AesException;
import cn.wqdmy.wechat.aes.WXBizMsgCrypt;
import cn.wqdmy.wechat.message.annotation.FieldLable;

/**
 * @author wb_dmy 时间：2015年10月31日上午11:28:58
 * @version 1.0
 */
public class MessageXmlMapper {

	/**
	 * 消息类型节点名
	 */
	public static final String MESSAGE_TYPE = "MsgType";

	public static final String MESSAGE_ROOT_NAME = "xml";

	public static final String MESSAGE_TOUSERNAME = "ToUserName";

	public static final String MESSAGE_ENCRYPT = "Encrypt";

	/**
	 * 微信消息加密对象 应用令牌分组
	 */
	public static Map<String, WXBizMsgCrypt> wxMsgCryptMap = new HashMap<>();

	/**
	 * 创建加密对象
	 * 
	 * @param token
	 * @param encodingAesKey
	 * @param appId
	 * @throws AesException
	 */
	public static void createMsgCrypt(String token, String encodingAesKey, String appId) throws AesException {
		wxMsgCryptMap.put(token, new WXBizMsgCrypt(token, encodingAesKey, appId));
	}

	/**
	 * 转成xml数据包
	 * @param message
	 * @param token
	 * @param isEncrypt
	 * @return
	 * @throws Exception
	 */
	public static String toWxXml(BaseMessage message, String token, boolean isEncrypt) throws Exception {
		return toWxXml(message, MESSAGE_ROOT_NAME, token, isEncrypt);
	}

	/**
	 * 加密xml包
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public static String toWxXml(EncryptMessage message) throws Exception {
		Element root = DocumentHelper.createElement(MESSAGE_ROOT_NAME);
		toFieldsXml(message, message.getClass().getDeclaredFields(), root);
		Document document = DocumentHelper.createDocument(root);
		document.setXMLEncoding("utf-8");
		return document.asXML();
	}

	/**
	 * 转成xml数据包
	 * @param message
	 * @param rootName
	 * @param token
	 * @param isEncrypt
	 * @return
	 * @throws Exception
	 */
	public static String toWxXml(BaseMessage message, String rootName, String token, boolean isEncrypt)
			throws Exception {
		Element root = DocumentHelper.createElement(rootName);
		toFieldsXml(message, getFields(message), root);
		if (isEncrypt) {
			return wxMsgCryptMap.get(token).encryptMsg(root.asXML());
		} else {
			Document document = DocumentHelper.createDocument(root);
			document.setXMLEncoding("utf-8");
			return document.asXML();
		}
	}

	/**
	 * 解析对象字段
	 * 
	 * @param message
	 * @param elements
	 * @throws Exception
	 */
	public static Field[] getFields(BaseMessage message) throws Exception {
		Field[] myFields = message.getClass().getDeclaredFields();
		Field[] fields = null;
		int myLength = myFields.length, temp = 0;
		if (!message.getClass().getName().equals(BaseMessage.class.getName())) {
			Field[] superFields = message.getClass().getSuperclass().getDeclaredFields();
			temp = superFields.length;
			fields = new Field[myLength + temp];
			for (int i = 0; i < temp; i++) {
				fields[i] = superFields[i];
			}
		}
		if (fields != null && temp > 0) {
			for (int i = 0; i < myLength; i++) {
				fields[temp + i] = myFields[i];
			}
		} else {
			fields = myFields;
		}
		return fields;
	}

	/**
	 * 添加字段到xml
	 * 
	 * @param fields
	 * @param root
	 */
	public static void toFieldsXml(Object message, Field[] fields, Element root) throws Exception {
		if (fields == null || fields.length == 0) {
			return;
		}
		for (Field field : fields) {
			if (field.getModifiers() == 2) {
				field.setAccessible(true);
				FieldLable lable = field.getAnnotation(FieldLable.class);
				if (lable != null) {
					Element element = root.addElement(lable.value());
					Object obj = field.get(message);
					if (obj != null) {
						if (lable.isDepth()) {
							if (obj instanceof List) {
								List<?> list = ((List<?>) obj);
								for (Object subObj : list) {
									Element item = element.addElement(lable.itemName());
									toFieldsXml(subObj, subObj.getClass().getDeclaredFields(), item);
								}
							} else {
								toFieldsXml(obj, obj.getClass().getDeclaredFields(), element);
							}
						} else if (lable.isCDATA()) {
							element.addCDATA(obj.toString());
						} else {
							element.setText(obj.toString());
						}
					}
				}
			}
		}
	}

	/**
	 * 解析xml数据到对象
	 * 
	 * @param message
	 * @param request
	 * @throws Exception
	 */
	public static BaseMessage fromMessage(HttpServletRequest request, String token, boolean isEncrypt)
			throws Exception {
		if (isEncrypt) {
			String msg_signature = request.getParameter("msg_signature");
			// 时间戳
			String timestamp = request.getParameter("timestamp");
			// 随机数
			String nonce = request.getParameter("nonce");
			// System.out.println("加密消息参数："+msg_signature +"=="+timestamp+"=="+nonce);
			String postData = IOUtils.toString(request.getInputStream(), "utf-8");
			String xmlData = wxMsgCryptMap.get(token).decryptMsg(msg_signature, timestamp, nonce, postData);
			return fromMessage(xmlData);
		} else {
			SAXReader reader = new SAXReader();
			// 读入流，解析出文档
			Document document = reader.read(request.getInputStream());
			Element root = document.getRootElement();
			// 获取对应的消息对象
			BaseMessage message = MessageEnum
					.getReqMessage(MessageEnum.getEnum(root.element(MESSAGE_TYPE).getTextTrim()));
			List<?> elements = root.elements();
			parsingFields(message, elements);
			return message;
		}
	}

	/**
	 * 解析xml数据到对象
	 * 
	 * @param message
	 * @param xml
	 * @throws Exception
	 */
	public static BaseMessage fromMessage(String xml) throws Exception {
		Document document = DocumentHelper.parseText(xml);
		Element root = document.getRootElement();
		BaseMessage message = MessageEnum.getReqMessage(MessageEnum.getEnum(root.element(MESSAGE_TYPE).getTextTrim()));
		List<?> elements = root.elements();
		parsingFields(message, elements);
		return message;
	}

	/**
	 * 提取出xml数据包中的加密消息
	 * 
	 * @param xmltext 待提取的xml字符串
	 * @return 提取出的加密消息字符串
	 * @throws AesException
	 */
	public static Object[] extract(String xmltext) throws AesException {
		Object[] result = new Object[3];
		try {
			Document document = DocumentHelper.parseText(xmltext);
			Element root = document.getRootElement();

			Element node1 = root.element(MESSAGE_ENCRYPT);
			Element node2 = root.element(MESSAGE_TOUSERNAME);

			result[0] = 0;
			result[1] = node1.getTextTrim();
			result[2] = node2.getTextTrim();
			return result;
		} catch (Exception e) {
			throw new AesException(AesException.ParseXmlError);
		}
	}

	/**
	 * 解析对象字段与节点对应值
	 * 
	 * @param message
	 * @param elements
	 * @throws Exception
	 */
	public static void parsingFields(BaseMessage message, List<?> elements) throws Exception {
		if (message == null) {
			System.err.println("MessageXmlUtils.parsingFields(..)获取不到消息对象");
			return;
		}
		Field[] myFields = message.getClass().getDeclaredFields();
		if (!message.getClass().getName().equals(BaseMessage.class.getName())) {
			Field[] superFields = message.getClass().getSuperclass().getDeclaredFields();
			fillFields(message, superFields, elements);
		}
		fillFields(message, myFields, elements);
	}

	/**
	 * 字段填充数据
	 * 
	 * @param message
	 * @param fields
	 * @param elements
	 * @throws Exception
	 */
	public static void fillFields(BaseMessage message, Field[] fields, List<?> elements) throws Exception {
		for (Field field : fields) {
			if (field.getModifiers() == 2) {
				field.setAccessible(true);
				FieldLable lable = field.getAnnotation(FieldLable.class);
				for (Object object : elements) {
					Element element = (Element) object;
					if (lable != null && lable.value().equals(element.getName())) {
						field.set(message, element.getTextTrim());
					} else if (field.getName().equalsIgnoreCase(element.getName())) {
						field.set(message, element.getTextTrim());
					}
				}
			}
		}
	}

}
