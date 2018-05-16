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
 * ������API������
 * 
 * @author aaa
 *
 */
@RestController
public class WebPCApiController {
	/**
	 * Get���� ��¼�����Ϣ
	 * 
	 * @param key
	 *            ��������
	 * @param content
	 *            ��������������Ϣ
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
			LogWritter.LogWritterInfo(WebPCApiController.class, LogEnum.Error,"������GET�����쳣��"+e.getMessage());
		}
		try{
			return AddMQ(key, content);
		}catch(Exception e){
			e.printStackTrace();
			//��¼������־
			LogWritter.LogWritterInfo(WebPCApiController.class, LogEnum.Error,String.format("���������Ϣ����Ϣ�����쳣��%s--%s", key,content));
			return ResultInfoCommon.FailJson("������������æ");
		}
	}

	/**
	 * ���ϵͳPOST����
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/MtBPAPI", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody ModelAndView Post(HttpServletRequest request) {
		// ע�⣺ajax����POST����Content-TypeҪ���ó�application/x-www-form-urlencoded
		// ���óɣ�application/json�ڷ������˻�ȡ��������
		String key = "";
		String content = "";
		try {
			key = request.getParameter("key");
			content = request.getParameter("content");
			
			//δ��ȡ���������������ж�ȡ���ݣ�application/json���󲹳䣩
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
			LogWritter.LogWritterInfo(WebPCApiController.class, LogEnum.Error,"������POST�����쳣��"+e.getMessage());
			return ResultInfoCommon.FailJson("���������ʽ����");
		}
		try{
			return AddMQ(key, content);
		}catch(Exception e){
			e.printStackTrace();
			//��¼������־
			LogWritter.LogWritterInfo(WebPCApiController.class, LogEnum.Error,String.format("���������Ϣ����Ϣ�����쳣��%s--%s", key,content));
			return ResultInfoCommon.FailJson("������������æ");
		}
	}

	/**
	 * POST��������ʱ������һ��OPTIONS ��̽������Ҫ�������˴���������POST���󲻳ɹ�
	 * 
	 * @return
	 */
	@RequestMapping(value = "/MtBPAPI", method = RequestMethod.OPTIONS)
	public @ResponseBody ModelAndView Options() {
		return ResultInfoCommon.SuccessJson("OPTIONS��̽����ɹ�!");
	}

	/**
	 * ��Ϣ���ӵ���Ϣ������ȥ
	 * 
	 * @param key
	 * @param content
	 * @return
	 */
	public ModelAndView AddMQ(String key, String content) {
		ModelAndView resultInfo = null;
		IPersistenceService persistence=new ImpPersistenceService();
		//System.out.println(String.format("%s--Receive Message,%s--%s",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()),key,content));
		//�־û���Ϣ
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