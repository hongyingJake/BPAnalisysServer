package com.bp;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import com.bp.bll.BPFlg;
import com.bp.common.LogEnum;
import com.bp.common.LogWritter;

/**
 * Web server ������
 * 
 * @author aaa
 *
 */
public class ServerCommon {
	
	/**
	 * JSON�����н������Բ�¼
	 * @param mapMain
	 * @return
	 */
	public Map<String, List<Map<String, String>>> AttributeAddByJsonObj(Map<String, List<Map<String, String>>> mapMain)
	{
		// �ڷ�Controler���л�ȡ�����ķ���
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
						.getRequest();
		for(String key:mapMain.keySet())
		{
			List<Map<String, String>> lst= mapMain.get(key);
			for(Map<String, String> map:lst)
			{
				//1:���Բ�¼
				SimpleDateFormat ServerTimeSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//��ȡ��ǰʱ��
				//1.1:ƥ����ȷ�������ò������ԣ�URL��HappenDateTime
				if(!map.containsKey("URL"))
				{
					// ��ȡhttp header��Ϣ
					String clientUri = request.getHeader("referer");
					if(clientUri==null||clientUri.length()<=0){
						clientUri="";
					}
					map.put("URL", clientUri);
				}
				if(!map.containsKey("HappenDateTime"))
				{
					String HappenDateTime = ServerTimeSdf.format(new Date());
					map.put("HappenDateTime", HappenDateTime);
				}
				//2:���ӷ�������ϵͳ����
				//2.1:���ӷ�����ʱ��
				String ServerTime = ServerTimeSdf.format(new Date());
				map.put("ServerTime", ServerTime);
				//2.2:���ӷ�����IP
				String localIp=getLocalIP();//��ȡ����IP��ַ
				map.put("ServerIP", localIp);
			}
		}
		return mapMain;
	}
	/**
	 * ���鼯��JSON�������Բ���
	 * @param val
	 * @return
	 */
	public String AttributeSupp(String val)
	{
		//0:JSON���������л���ʱ����ʱ�����л��з����ѻ��з��滻���ַ���
        val = val.replaceAll("[\\t\\n\\r]", "");
        //1:�ж��Ƿ������飬��������ÿ��JSON���������������
        if (val.charAt(0) == '[')
        {
        	//Ϊÿ�����������������
        	String tempV=val.substring(1,val.length()-2);
        	String[] tempSum=tempV.split("},");
        	StringBuilder str=new StringBuilder();
        	for(int i=0;i<tempSum.length;i++)
        	{
        		str.append(SingleAttribute(tempSum[i]+(tempSum[i].charAt(tempSum[i].length()-1)=='}'?"":"}"))+",");
        	}
        	return String.format("[%s]",str.toString().substring(0, str.length()-1));
        }
        else
        {
        	return SingleAttribute(val);
        }
	}
	/**
	 * ����JSON�����Զ�������
	 * 
	 * @param val
	 *            JSON�ַ���
	 * @return
	 */
	private String SingleAttribute(String val) {
		// �ڷ�Controler���л�ȡ�����ķ���
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		// HttpServletResponse response =
		// ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
		// HttpServletResponse response =
		// ((ServletWebRequest)RequestContextHolder.getRequestAttributes()).getResponse();
		// ServletContext context =
		// ContextLoader.getCurrentWebApplicationContext().getServletContext();
		
		//1:���Բ���
		StringBuilder strServerInfo =new StringBuilder();
		SimpleDateFormat ServerTimeSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//��ȡ��ǰʱ��
		//1.1:ƥ����ȷ�������ò������ԣ�URL��HappenDateTime
		String urlMatch="[.]*?\"URL\":.*";
		String happenMath="[.]*?\"HappenDateTime\":.*";
		if(!Pattern.matches(urlMatch, val))
		{
			// ��ȡhttp header��Ϣ
			String clientUri = request.getHeader("referer");
			strServerInfo.append(String.format(",\"%s\":\"%s\"", "URL", clientUri));
		}
		if(!Pattern.matches(happenMath, val))
		{
			String HappenDateTime = ServerTimeSdf.format(new Date());
			strServerInfo.append(String.format(",\"%s\":\"%s\"", "HappenDateTime", HappenDateTime));
		}
		//2:���ӷ�������ϵͳ����
		//2.1:���ӷ�����ʱ��
		String ServerTime = ServerTimeSdf.format(new Date());
		strServerInfo.append(String.format(",\"%s\":\"%s\"", "ServerTime", ServerTime));
		//2.2:���ӷ�����IP
		String localIp=getLocalIP();//��ȡ����IP��ַ
		strServerInfo.append(String.format(",\"%s\":\"%s\"", "ServerIP", localIp));
		val = val.substring(0, val.length() - 1) + strServerInfo.toString() + "}";
        return val;
	}
	/**
	 * ��ȡ�������˵�ַ(���ز������쳣)
	 * @return
	 */
	private String getServerIp() {
		String SERVER_IP = "";
		try {
			Enumeration netInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
				ip = (InetAddress) ni.getInetAddresses().nextElement();
				SERVER_IP = ip.getHostAddress();
				if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {
					SERVER_IP = ip.getHostAddress();
					break;
				} else {
					ip = null;
				}
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SERVER_IP;
	}
	/**
	 * ��ȡ���ص�ַ
	 * @return
	 */
	private  String getLocalIP() {
		if(BPFlg.ServerLocalIp!=null&&BPFlg.ServerLocalIp.length()>0){
			return BPFlg.ServerLocalIp;
		}
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogWritter.LogWritterInfo(ServerCommon.class, LogEnum.Error, "��ȡ���ط�����IP��ַ�쳣,"+e.getMessage());
		}

		byte[] ipAddr = addr.getAddress();
		String ipAddrStr = "";
		for (int i = 0; i < ipAddr.length; i++) {
			if (i > 0) {
				ipAddrStr += ".";
			}
			ipAddrStr += ipAddr[i] & 0xFF;
		}
		// System.out.println(ipAddrStr);
		BPFlg.ServerLocalIp=ipAddrStr;
		return ipAddrStr;
	}
}