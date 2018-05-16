package com.bp.bll;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.bp.common.LogEnum;
import com.bp.common.LogWritter;

/**
 * @author zyk
 * @version ����ʱ�䣺2017��8��7�� ����5:07:55 ����������� ����������ʱ�����Զ����߳� ��˵��
 */
public class BPServiceListener implements ServletContextListener {
	/**
	 * ��������߳�
	 */
	private Thread monitor = null;

	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		try {
			// 1��ֹͣAPI�����߳�
			if (monitor != null && monitor.isInterrupted()) {
				monitor.interrupt();
			}
			// 2:�����е���Ϣ�־û�������
			SaveMQLocal.SaveCacheQueuetoLocal();
		} catch (Exception e) {
			e.printStackTrace();
			LogWritter.LogWritterInfo(this.getClass(), LogEnum.Warn, "����������������߳��쳣��" + e.getMessage());
		}
	}

	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		// ��ʼ�����֮�������̼߳���
		if (monitor == null) {
			ThreadMonitor thMonitor = new ThreadMonitor();
			monitor = new Thread(thMonitor);
			monitor.setDaemon(true);// ����Ϊ��̨�߳�
			monitor.start();
		}
	}

}
