package com.bp.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * JSON格式工具类代码
 * 
 * @author aaa
 *
 */
public class JsonUtility {
	/**
	 * 判断JSON字符串是否为JSON格式
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static boolean IsJSON(String jsonStr) {
		// 判断是否为JSON数组
		if (jsonStr.charAt(0) == '[') {
			String strSum = jsonStr.substring(1, jsonStr.length() - 2);
			String[] tempSum = strSum.split("},");
			boolean isJson = true;
			for (int i = 0; i < tempSum.length; i++) {
				if (!IsSingleJson(tempSum[i] + "}")) {
					isJson = false;
					break;
				}
			}
			return isJson;
		} else {
			return IsSingleJson(jsonStr);
		}

	}

	/**
	 * 单类型JSON字符串判断是否为JSON格式
	 * 
	 * @param json
	 *            JSON字符串
	 * @return
	 */
	private static boolean IsSingleJson(String json) {
		try {
			JSONObject jsonObject = JSONObject.fromObject(json);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * JSON字符串转换成集合对象
	 * 
	 * @param key
	 *            键名称
	 * @param jsonContent
	 *           JSON格式字符串
	 * @return
	 */
	public static Map<String, List<Map<String, String>>> JSON2Map(String key, String jsonContent) throws Exception {
		Map<String, List<Map<String, String>>> map = new HashMap<String, List<Map<String, String>>>();
		List<Map<String, String>> listM = new ArrayList<Map<String, String>>();
		try {
			//0:替换JSON格式字符串中特殊符号，影响JSON格式的判断
			jsonContent = jsonContent.replaceAll("[\\t\\n\\r]", "");
			// 判断JSON格式字符串是否为数组
			if (jsonContent.charAt(0) == '[') {
				String strSum = jsonContent.substring(1, jsonContent.length() - 2);
				String[] tempSum = strSum.split("},");
				for (int i = 0; i < tempSum.length; i++) {
					Map<String, String> subMap = new HashMap<String, String>();
					subMap = SingleJson2Map(tempSum[i] + "}");
					listM.add(subMap);
				}
			} else {
				Map<String, String> mapContent = new HashMap<String, String>();
				mapContent = SingleJson2Map(jsonContent);
				listM.add(mapContent);
			}
		} catch (Exception e) {
			throw e;
		}
		map.put(key, listM);
		return map;
	}

	/**
	 * 单JSON对象转换集合对象
	 * 
	 * @param jsonContent
	 *            JSON格式字符串
	 * @return
	 */
	private static Map<String, String> SingleJson2Map(String jsonContent) {
		Map<String, String> mapContent = new HashMap<String, String>();
		JSONObject jsonO = JSONObject.fromObject(jsonContent);
		Iterator<String> keys = jsonO.keys();
		while (keys.hasNext()) {
			String k = keys.next();
			String v = jsonO.getString(k);

			mapContent.put(k, v);
		}
		return mapContent;
	}
	/**
	 * 对象转换成JSON格式字符串
	 * @param obj
	 * @return
	 */
	public static String ObjToJSONStr(Object obj)
	{
		JSONObject jsonO=JSONObject.fromObject(obj);
		return jsonO.toString();
	}
	/**
	 * map格式JSON字符串转Map集合对象
	 * @param mapJson Map<String, List<Map<String, String>>> 集合JSON格式字符串
	 * @return
	 */
	public static Map<String, List<Map<String, String>>> JSONStrToObj(String mapJson) throws Exception
	{
		//{"keyClickNumber":[{"BrowserType":"Firefox_54.0","elementID":"2017042001"}]}
		String strSum = mapJson.substring(1, mapJson.length() - 2);
		String[] sum=strSum.split(":\\[");
		if(sum!=null&&sum.length==2)
		{
			try
			{
				return JSON2Map(sum[0].replaceAll("\"", ""),sum[1]);
			}
			catch(Exception e)
			{
				throw e;
			}
		}
		else
		{
			return null;
		}
	}
}
