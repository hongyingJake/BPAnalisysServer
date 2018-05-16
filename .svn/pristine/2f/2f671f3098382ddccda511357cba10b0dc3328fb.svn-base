package com.bp.msgfactory;

import com.bp.msginterface.*;
import com.bp.msgrealization.KafkaMQStorage;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import com.bp.common.*;

/**
 * @author zyk
 * @version 消息工厂，通过反射创建消息队列实现类对象
 */
public class MQFactory {
	/**
	 * 消息对象接口
	 */
	private static IBaseMQ baseMQ = null;
	private static String MQRealizationJarFile = null;// 实现消息发送类接口的实现类jar包名称
	private static String MQRealizationClassName = null;// 实现消息类名称

	/**
	 * 获取项目执行程序跟目录
	 * @return file:/D:/workSoftWare/apache-tomcat-8.0.41/webapps/
	 *         BPAnalisysServer/WEB-INF/classes/
	 */
	private static String GetLibJarFilePath() {
		String imagesTmpFolderPath = Thread.currentThread().getContextClassLoader().getResource("").toString();
		imagesTmpFolderPath = imagesTmpFolderPath.replace("file:/", "");
		imagesTmpFolderPath = imagesTmpFolderPath.replace("WEB-INF/classes/", "");
		imagesTmpFolderPath += "WEB-INF/lib/";
		return imagesTmpFolderPath;
	}

	/**
	 * 获取消息对象实例
	 * 
	 * @return
	 */
	public static IBaseMQ GetMQInfo() {
		if (baseMQ == null) {
			//通过反射获取对象需在config.xml文档中添加节点:
			//<!-- 消息队列实现类jar包名称(lib目录) -->
			//<!-- <MQRealizationJarFile>msgrealization.jar</MQRealizationJarFile> -->
			//<!-- 消息队列实现类 -->
			//<!-- <MQRealizationClassName>com.bp.msgrealization.KafkaMQStorage</MQRealizationClassName> -->
//			ConfigurationManager mg = new ConfigurationManager();
//			MQRealizationJarFile = GetLibJarFilePath() + mg.ReadConfiByNodeName("MQRealizationJarFile");
//			MQRealizationClassName = mg.ReadConfiByNodeName("MQRealizationClassName");
			// 通过反射实现（反射转换成实现类型失败，类引用过多，有时间可查找原因，暂时取消反射创建）
			try {
//				File file = new File(MQRealizationJarFile);
//				URL url = file.toURI().toURL();
//				ClassLoader loader = new URLClassLoader(new URL[] { url });
//				baseMQ = (IBaseMQ)Class.forName(MQRealizationClassName).newInstance();
				baseMQ=new KafkaMQStorage();
			} catch (Exception e) {
				LogWritter.LogWritterInfo( IBaseMQ.class, LogEnum.Error, "通过反射实现消息队列实现类发生异常："+e.getMessage());
			}
		}
		return baseMQ;
	}
}
