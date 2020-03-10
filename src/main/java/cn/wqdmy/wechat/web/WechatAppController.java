package cn.wqdmy.wechat.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wqdmy.wechat.service.WeChatMessageService;

/**
 * 微信公众号服务入口
 * @author dmy
 *
 */
@Controller
public class WechatAppController {
	
	private Logger logger = LoggerFactory.getLogger(WechatAppController.class);
	
	@Autowired
	private WeChatMessageService weChatMessageService;
	
	//微信所有请求入口
	@ResponseBody
	@RequestMapping(value="/input/{appCode}",produces={"application/xml;charset=UTF-8"})
	public String input(@PathVariable("appCode")String appCode, HttpServletRequest request) throws Exception{
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		request.setAttribute("appCode", appCode);
		// 验证签名
		if(weChatMessageService.checkSignature(signature, timestamp, nonce, appCode)) { 
			// 连接测试直接返回
			if("get".equalsIgnoreCase(request.getMethod())){
				return request.getParameter("echostr");
			}
			return weChatMessageService.handler(request);
		}
		logger.warn("signature:{} timestamp:{} nonce:{} Signature rejected !" , signature, timestamp, nonce);
		return "Signature rejected !"; 
	} 
}
