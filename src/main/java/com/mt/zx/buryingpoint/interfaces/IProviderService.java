package com.mt.zx.buryingpoint.interfaces;

/**
 * 该接口是 将在线不同来源的服务器数据 的json格式数据存入到HBase中
 * @author zhangyanfeng
 *
 */
public interface IProviderService {
	
	/**
	 * 
	 * @param tableName 表名
	 * @param tableNameAbbreviate 表名拼音缩写
	 * @param json 数据
	 *  json数据格式需要遵循以下格式:
	 *     {"keyClickNumber":[{"BrowserType":"Firefox_54.0","elementID":"2017042001"}
	 *     					 ,{"BrowserType":"Firefox_54.0","elementID":"2017042001"}]}
	 *       
	 */
	public void saveJsonDataToKafka(String tableName, String tableNameAbbreviate, String json);

}
