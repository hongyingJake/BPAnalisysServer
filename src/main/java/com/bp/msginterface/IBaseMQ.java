package com.bp.msginterface;

import java.util.List;

/**
* @author zyk
* @version ����ʱ�䣺2017��8��7�� ����10:48:59
* ��Ϣ���г����� ��˵��
*/
public interface IBaseMQ {
	/**
	 * ������Ϣ����Ϣ����
	 * @param val
	 * @return
	 */
    boolean SaveMQInfo(String tabName,Object val);
	/**
	 * ����Ϣ�����л�ȡ��Ϣ
	 * @return ������Ϣ����
	 */
    List<String> GetMQInfo();
	/**
	 * ��Ϣ�����Ƿ����
	 * @return
	 */
    boolean CanUseMQ();

}