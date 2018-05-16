package com.bp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Spring ������������
 * 
 * @author aaa
 *
 */
public class CORSInterceptor implements HandlerInterceptor {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// ����Header��Ϣ��֧�ֿ�������
		// ����������ԣ�HTML5���ԣ�IE8���²�֧�֣�
		// �ͻ��˲���POST��������API��AJAX�޷�ʵ��POST��������ͨ��HTML5����ʵ�֡�֧��HTML5�������
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");//������3600���ڣ�����Ҫ�ٷ���Ԥ�������󣬿��Ի���ý��
		response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
		//���������������������content-typeͷ
		response.setHeader("Allow","POST");
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}