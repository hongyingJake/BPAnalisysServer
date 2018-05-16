package com.bp.msgrealization;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.dubbo.common.logger.Level;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.bp.common.ConfigurationManager;
import com.bp.msginterface.IBaseMQ;
import com.mt.zx.buryingpoint.interfaces.IProviderService;

/**
* @author zyk
* @version 
* kafka消息队列实现
*/
public class KafkaMQStorage implements IBaseMQ {
	private static IProviderService ps;
	private static Level dubboLevel;
	public Level getDubboLevel()
	{
		if(dubboLevel==null)
		{
			ConfigurationManager mgr=new ConfigurationManager();
			String level=mgr.ReadConfiByNodeName("DubboLogLevel")==""?"WARN":mgr.ReadConfiByNodeName("DubboLogLevel").toUpperCase();
			//Dubbo 日志级别OFF、FATAL、ERROR、WARN、INFO、DEBUG、ALL
			dubboLevel=Level.valueOf(level);
		}
		return dubboLevel;
	}
	public boolean SaveMQInfo(String tabName,Object val) {
		if(ps==null)
		{
			LoggerFactory.setLevel(getDubboLevel());
			String[] ss=new String[]{"customer.xml"};
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(ss);
			context.start();
			ps=(IProviderService)context.getBean("providerService");
		}
		ps.saveJsonDataToKafka(tabName, GetStrSX(tabName), val.toString());
		//System.out.println(String.format("%s--Send Success:%s--%s",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()), tabName,val));
		return true;
	}
	
	/**
	 * 返回字符串的缩写
	 * @param str
	 * @return
	 */
	private  String GetStrSX(String str)
	{
		//逻辑：1：最多取出6个字符,取第一个字符和后面的所有大写字符
		//2：如果没有取到则返回字符串“NULL”
		StringBuilder sb=new StringBuilder(); 
		int num=0;
		if(str==null||str.length()<=0){
			sb.append("NULL");
		}
		else
		{
			for(int i=0;i<str.length();i++){
				char c=str.charAt(i);
				if(i==0){
					sb.append(str.charAt(i));
					num++;
				}
				else
				{
					if(Character.isUpperCase(c)){
						sb.append(c);
						num++;
					}
				}
				if(num==6)
				{
					break;
				}
			}
		}
		return sb.toString();
	}

	public List<String> GetMQInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 判断服务器是否可用
	 */
	public boolean CanUseMQ() {
		try{
			String[] ss=new String[]{"customer.xml"};
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(ss);
			context.start();
			ps=(IProviderService)context.getBean("providerService");
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
	}


}
