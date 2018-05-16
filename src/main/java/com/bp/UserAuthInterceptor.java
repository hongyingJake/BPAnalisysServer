package com.bp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author zyk
 * @version ����ʱ�䣺2017��8��10�� ����4:24:36 �û�Ȩ����֤������ ��˵��
 */
public class UserAuthInterceptor implements HandlerInterceptor {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// �ж��û��Ƿ��¼
		HttpSession session = request.getSession();
		Object user = session.getAttribute("UserLogin");
		if(user==null)
		{
			//��ת����¼ҳ��
			response.sendRedirect(request.getContextPath()+"/BPRegist/Login");
			return false;
		}
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
