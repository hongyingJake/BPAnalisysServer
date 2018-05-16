package com.bp.msginterface;

import java.util.List;

/**
* @author zyk
* @version 创建时间：2017年8月7日 上午10:48:59
* 消息队列抽象定义 类说明
*/
public interface IBaseMQ {
	/**
	 * 保存信息到消息队列
	 * @param val
	 * @return
	 */
    boolean SaveMQInfo(String tabName,Object val);
	/**
	 * 从消息队列中获取信息
	 * @return 返回消息集合
	 */
    List<String> GetMQInfo();
	/**
	 * 消息队列是否可用
	 * @return
	 */
    boolean CanUseMQ();

}
