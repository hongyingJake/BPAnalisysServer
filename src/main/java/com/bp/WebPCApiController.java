package com.bp;


import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.bp.common.*;
import com.bp.interfaces.IPersistenceService;

import net.sf.json.JSONObject;

import com.bp.bll.*;

/**
 * 埋点服务API控制器
 * 
 * @author aaa
 *
 */
@RestController
public class WebPCApiController {
	/**
	 * Get请求 记录埋点信息
	 * 
	 * @param key
	 *            请求埋点键
	 * @param content
	 *            请求埋点的内容信息
	 * @return
	 */
	@RequestMapping(value = "/MtBPAPI", method = RequestMethod.GET)
	public @ResponseBody ModelAndView Get(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String key = "";
		String content = "";
		try {
			key = request.getParameter("key");
			content = request.getParameter("content");
		} catch (Exception e) {
			e.printStackTrace();
			LogWritter.LogWritterInfo(WebPCApiController.class, LogEnum.Error,"埋点服务GET请求异常，"+e.getMessage());
		}
		try{
			return AddMQ(key, content);
		}catch(Exception e){
			e.printStackTrace();
			//记录访问日志
			LogWritter.LogWritterInfo(WebPCApiController.class, LogEnum.Error,String.format("请求埋点信息到消息队列异常：%s--%s", key,content));
			return ResultInfoCommon.FailJson("服务器工作繁忙");
		}
	}

	/**
	 * 埋点系统POST请求
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/MtBPAPI", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody ModelAndView Post(HttpServletRequest request) {
		// 注意：ajax跨域POST请求，Content-Type要设置成application/x-www-form-urlencoded
		// 设置成：application/json在服务器端获取不到数据
		String key = "";
		String content = "";
		try {
			key = request.getParameter("key");
			content = request.getParameter("content");
			
			//未获取参数，从请求流中读取数据（application/json请求补充）
			if(key==null&&content==null){
				StringBuilder sb = new StringBuilder(); 
				request.setCharacterEncoding("UTF-8");
				BufferedReader reader = request.getReader();
				char[]buff = new char[1024*5];
	            int len;  
	            while((len = reader.read(buff)) != -1) {  
	                 sb.append(buff,0, len);  
	            }
	            JSONObject json = JSONObject.fromObject(sb.toString());
	            key=json.getString("key");
	            content=json.getString("content");
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogWritter.LogWritterInfo(WebPCApiController.class, LogEnum.Error,"埋点服务POST请求异常，"+e.getMessage());
			return ResultInfoCommon.FailJson("请求参数格式错误");
		}
		try{
			return AddMQ(key, content);
		}catch(Exception e){
			e.printStackTrace();
			//记录访问日志
			LogWritter.LogWritterInfo(WebPCApiController.class, LogEnum.Error,String.format("请求埋点信息到消息队列异常：%s--%s", key,content));
			return ResultInfoCommon.FailJson("服务器工作繁忙");
		}
	}

	/**
	 * POST跨域请求时，会有一个OPTIONS 嗅探请求，需要服务器端处理，否则POST请求不成功
	 * 
	 * @return
	 */
	@RequestMapping(value = "/MtBPAPI", method = RequestMethod.OPTIONS)
	public @ResponseBody ModelAndView Options() {
		return ResultInfoCommon.SuccessJson("OPTIONS嗅探请求成功!");
	}

	/**
	 * 消息添加到消息队列中去
	 * 
	 * @param key
	 * @param content
	 * @return
	 */
	public ModelAndView AddMQ(String key, String content) {
		ModelAndView resultInfo = null;
		IPersistenceService persistence=new ImpPersistenceService();
		//System.out.println(String.format("%s--Receive Message,%s--%s",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()),key,content));
		//持久化消息
		final String[] info = new String[1];
		boolean isSuccess=persistence.SaveContentToBP(key, content, info);
		if(isSuccess){
			resultInfo = ResultInfoCommon.SuccessJson(info[0]);
		}
		else{
			resultInfo = ResultInfoCommon.FailJson(info[0]);
		}
		return resultInfo;
	}
}
