package com.bp.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zyk
 * @version 创建时间：2017年8月9日 下午4:44:03 类说明
 */
public class CookieUtility {
	
	/**
	 * 添加cookie
	 * @param response
	 * @param name
	 * @param value
	 * @param MaxDate cookie最多保存时间 单位：秒
	 */
	public void addCookie(HttpServletResponse response,String name,String value,int MaxDate){  
        Cookie cookie = new Cookie(name.trim(), value.trim());  
        cookie.setMaxAge(MaxDate);// 设置为30min  30 * 60
        cookie.setPath("/");  
        response.addCookie(cookie);
    }
	/**
	 * 删除cookie
	 * @param request
	 * @param response
	 * @param name
	 */
	public void deleteCookie(HttpServletRequest request,HttpServletResponse response, String name)
	{
		Map<String, Cookie> cookieMap = ReadCookieMap(request);
		if (cookieMap.containsKey(name)) {
			Cookie cookie = (Cookie) cookieMap.get(name);
			if(cookie!=null)
			{
				cookie.setValue(null);  
                cookie.setMaxAge(0);// 立即销毁cookie  
                cookie.setPath("/");  
                response.addCookie(cookie);
			}
		} 
	}
	/**
	 * 通过cookie名称获取cookie值
	 * @param request
	 * @param name
	 * @return
	 */
	public String getCookieByName(HttpServletRequest request, String name) {
		Map<String, Cookie> cookieMap = ReadCookieMap(request);
		if (cookieMap.containsKey(name)) {
			Cookie cookie = (Cookie) cookieMap.get(name);
			if(cookie!=null)
			{
				return cookie.getValue();
			}
			else
			{
				return null;
			}
		} else {
			return null;
		}
	}
	/**
	 * cookie封装到Map中
	 * @param request
	 * @return
	 */
	private Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}
}
