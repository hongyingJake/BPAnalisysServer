package com.bp.common;

import java.io.UnsupportedEncodingException;

/**
* @author zyk
* @version ����ʱ�䣺2017��8��23�� ����2:49:24
* URL����ͽ��� ��˵��
*/
public class UrlUtil {
	 private final static String ENCODE = "GBK"; 
	    /**
	     * URL ����
	     *
	     * @return String
	     * @author lifq
	     * @date 2015-3-17 ����04:09:51
	     */
	    public static String getURLDecoderString(String str) {
	        String result = "";
	        if (null == str) {
	            return "";
	        }
	        try {
	            result = java.net.URLDecoder.decode(str, ENCODE);
	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        }
	        return result;
	    }
	    /**
	     * URL ת��
	     *
	     * @return String
	     * @author lifq
	     * @date 2015-3-17 ����04:10:28
	     */
	    public static String getURLEncoderString(String str) {
	        String result = "";
	        if (null == str) {
	            return "";
	        }
	        try {
	            result = java.net.URLEncoder.encode(str, ENCODE);
	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        }
	        return result;
	    }
}
