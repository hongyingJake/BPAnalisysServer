package com.bp.bll;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.bp.common.LogEnum;
import com.bp.common.LogWritter;

/**
 * @author zyk
 * @version 创建时间：2017年8月7日 下午5:07:55 埋点服务监视器 服务启动的时候开启自定义线程 类说明
 */
public class BPServiceListener implements ServletContextListener {
	/**
	 * 服务监视线程
	 */
	private Thread monitor = null;

	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		try {
			// 1：停止API监视线程
			if (monitor != null && monitor.isInterrupted()) {
				monitor.interrupt();
			}
			// 2:缓存中的消息持久化到本地
			SaveMQLocal.SaveCacheQueuetoLocal();
		} catch (Exception e) {
			e.printStackTrace();
			LogWritter.LogWritterInfo(this.getClass(), LogEnum.Warn, "埋点服务监视器监视线程异常：" + e.getMessage());
		}
	}

	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		// 初始化完成之后启动线程监视
		if (monitor == null) {
			ThreadMonitor thMonitor = new ThreadMonitor();
			monitor = new Thread(thMonitor);
			monitor.setDaemon(true);// 设置为后台线程
			monitor.start();
		}
	}

}
