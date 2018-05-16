package com.bp.common;

import java.io.UnsupportedEncodingException;

/**
* @author zyk
* @version 创建时间：2017年8月23日 下午2:49:24
* URL编码和解码 类说明
*/
public class UrlUtil {
	 private final static String ENCODE = "GBK"; 
	    /**
	     * URL 解码
	     *
	     * @return String
	     * @author lifq
	     * @date 2015-3-17 下午04:09:51
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
	     * URL 转码
	     *
	     * @return String
	     * @author lifq
	     * @date 2015-3-17 下午04:10:28
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
