package com.bp.bll;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.bp.common.*;
import com.bp.msgfactory.*;
import com.bp.msginterface.*;

/**
* @author zyk
* @version ����ʱ�䣺2017��8��7�� ����4:10:46
* Web �����̼߳��ӹ��� ��������Ϣ���з������Ƿ�������������˵��
*/
public class ThreadMonitor implements Runnable {
	/*
	 * �߳��������
	 * @see java.lang.Runnable#run()
	 */
	public void run()
	{
		MonitorMQAPI();
	}
	/**
	 * �߳̿�����Ϣʱ�䣬��λ����
	 */
	private static int ThreadFreeTime=1;
	static{
		ConfigurationManager mgr=new ConfigurationManager();
		String freeTime=mgr.ReadConfiByNodeName("ThreadFreeTime")==""?"1":mgr.ReadConfiByNodeName("ThreadFreeTime");
		ThreadFreeTime=Integer.parseInt(freeTime);
	}
	/**
	 * API���������Ϣ����
	 */
	private void MonitorMQAPI()
	{
		IBaseMQ mQ=MQFactory.GetMQInfo();
		while(true)
		{
			//�ж���Ϣ�����Ƿ����
			if (!BPFlg.getMSMQServerCanUse() && mQ.CanUseMQ())
            {
                //1:������Ϣ���б�ʶ������
                if (!BPFlg.getMSMQServerCanUse())
                {
                    BPFlg.setMSMQServerCanUse(true);
                    LogWritter.LogWritterInfo(ThreadMonitor.class, LogEnum.Error,String.format("�߳�:%s API���������--�Ѳ鿴��MQ��Ϣ����������!", Thread.currentThread().getId()));
                }
            }
            else if (BPFlg.getMSMQServerCanUse())
            {
            	 //�鿴�Ƿ��г־û���Ϣ��Ҫд�뵽����
                //2:�ӳ־û��ı��ļ��ж�ȡ��Ϣд�뵽MQ�� 
                try
                {
                    //�ݹ���������ļ�Ŀ¼�����ļ����ȡ�ļ���Ϣ����MQ
                    DGDirFile(SaveMQLocal.ShareDir);
                    //ָ����ʱ�䵥λΪ��
                    ThreadSleep(ThreadFreeTime);
                }
                catch (Exception e)
                {
                	e.printStackTrace();
                    LogWritter.LogWritterInfo(ThreadMonitor.class, LogEnum.Error,"API�����߳��쳣,"+e.getMessage());
                }
            }
            else
            {
            	//ָ����ʱ�䵥λΪ��
            	ThreadSleep(ThreadFreeTime);
            }
		}
	}
	/**
	 * �߳���Ϣָ����ʱ��
	 * @param times ��λ����
	 */
	private void ThreadSleep(int times){
		//ָ����ʱ�䵥λΪ��
    	try  
        {  
    		Thread.sleep(times * 1000);
        }  
        catch (InterruptedException e)  
        {  
        	 LogWritter.LogWritterInfo(ThreadMonitor.class, LogEnum.Error,"API�����߳�Sleepָ����ʱ���쳣��"+e.toString());
        }
	}
	/**
	 * ��ȡȫ���ļ�������д�뵽��Ϣ����
	 * @param f
	 */
	private void ReadAllFileContent(File f)
	{
		String content= SaveMQLocal.GetInfoBackClear(f.getAbsolutePath());
		if(content!=null&&content.length()>0)
		{
			//��Ϣ���ӵ���Ϣ���з�������ÿһ����Ϣ����һ�����з��ָ�
			String[] MQSum=content.split("\r\n");
			StringBuilder sb=new StringBuilder();
			for(String msg:MQSum)
			{
				if(msg.trim().length()<=0)
				{
					continue;
				}
				if(!BPFlg.getMSMQServerCanUse()){
					sb.append(msg);
					sb.append("\r\n");
					//SaveMQLocal.SaveMQToTxt(msg);
					continue;
				}
				try
				{
					//����Ҫת�����󣬱���ʱ��˵һ��JSON�ַ���
					//Object  obj= JsonUtility.JSONStrToObj(msg);
					//��ȡ��key
					String strSum = msg.substring(1, msg.length() - 2);
					String[] sum=strSum.split(":\\[");
					if(sum!=null&&sum.length==2)
					{
						String key=sum[0].replaceAll("\"", "");
						Object  obj=msg;
						if(obj!=null)
						{
							MQFactory.GetMQInfo().SaveMQInfo(key,obj);
							//System.out.println(obj);
						}
					}
				}
				catch(Exception e)
				{
					BPFlg.setMSMQServerCanUse(false);
					//���浽�ı��ļ�
					SaveMQLocal.SaveMQToTxt(msg);
					//��¼��־
					LogWritter.LogWritterInfo(ThreadMonitor.class, LogEnum.Error,String.format("�߳�:%s ��ȡ���س־û��ļ���MQ���̣�MQ�����쳣����Ϣ�ٴγ־û�������!", Thread.currentThread().getId()));
					//ֹͣ��ǰ�߳�ָ��ʱ��
					ThreadSleep(ThreadFreeTime);
					e.printStackTrace();
				}
			}
			//δ���͵��������³־û�������
			if(sb.length()>0){
				SaveMQLocal.SaveMQToTxt(sb);
			}
		}
		else
		{
			ThreadSleep(ThreadFreeTime);
		}
	}
	
	/**
	 * �������ļ����ж�ȡ�ļ���ɾ���ļ�
	 * @param f
	 */
	private void ReadRowFileContent(File f){
		String Absolute=f.getAbsolutePath();//�ļ����Ե�ַ
		String resetName=Absolute+".current";//����������ļ�
		//�ļ����ݹ��������������ȡ�����������ļ�
		File newFile=new File(resetName);
		//����������ļ�ֻ�е�ǰ������̷��ʣ������ж�ȡ
		FileInputStream inputStream = null;
		Scanner sc = null;
		try {
			f.renameTo(newFile);
		    inputStream = new FileInputStream(newFile);
		    //ͨ��Scanner��һ��һ�ж�ȡ��������ReadBufferһ������ȡ���ڴ�Ȼ����һ�ж�ȡ�������ڴ����
		    sc = new Scanner(inputStream, "UTF-8");
		    while (sc.hasNextLine()) {
		        String line = sc.nextLine();
		      //��ȡ��key
				String strSum = line.substring(1, line.length() - 2);
				String[] sum=strSum.split(":\\[");
				if(sum!=null&&sum.length==2)
				{
					String key=sum[0].replaceAll("\"", "");
					try{
					MQFactory.GetMQInfo().SaveMQInfo(key,line);
					}
					catch(Exception e)
					{
						e.printStackTrace();
						BPFlg.setMSMQServerCanUse(false);
						//���浽�ı��ļ�
						SaveMQLocal.SaveMQToTxt(line);
						LogWritter.LogWritterInfo(ThreadMonitor.class, LogEnum.Error,"API�����߳����ж�ȡд����Ϣ�����쳣�������³־û����أ�"+e.getMessage());
					}
				}
		    }
		} 
		catch(Exception e)
		{
			LogWritter.LogWritterInfo(ThreadMonitor.class, LogEnum.Error,"API�����߳����ж�ȡ��Ϣ�쳣"+e.getMessage());
			e.printStackTrace();
		}
		finally {
		    if (inputStream != null) {
		    	try{
		        inputStream.close();
		    	}
		    	catch(Exception e){
		    		LogWritter.LogWritterInfo(ThreadMonitor.class, LogEnum.Error,"API�����̹߳ر���ʱ��ȡ�ļ����쳣"+e.getMessage());
		    		e.printStackTrace();
		    	}
		    }
		    if (sc != null) {
		        sc.close();
		    }
		}
		//��ȡ���ļ������ļ�ɾ��
		try{
		newFile.delete();
		}
		catch(Exception e){
    		LogWritter.LogWritterInfo(ThreadMonitor.class, LogEnum.Error,"API�����߳�ɾ����ʱ�ļ��쳣"+e.getMessage());
    		e.printStackTrace();
		}
	}
	/**
	 * �ӱ����ļ���ȡ��Ϣ���͵���Ϣ���з�����
	 * @param Dir
	 */
	private void DGDirFile(String Dir)
	{
		File file=new File(Dir);
		if(file.isDirectory()&&file.exists())
		{
			if(BPFlg.getMSMQServerCanUse())
			{
				File[] files= file.listFiles();
				for(File f:files)
				{
					if(!f.isDirectory()&&BPFlg.getMSMQServerCanUse())
					{
						//�ж��ļ���С������500MB�����ж�ȡ��Ϣ����Ϣ���У��ڴ�������������ȡ�����ļ����ݵ���Ϣ����
						String fileName=f.getName();
						String prefix=fileName.substring(fileName.lastIndexOf(".")+1);//�ļ���չ��
						if(f.length()/1024/1024>500&&!prefix.equals("current")){
							//���������ļ���Ȼ�����ж�ȡ����
							ReadRowFileContent(f);
						}
						else if(prefix.equals("current")){
							//��ǰ���ڴ������ĵ��������в���
						}
						else
						{
							ReadAllFileContent(f);
						}
					}
					else
					{
						//ΪĿ¼��ݹ����
						DGDirFile(f.getAbsolutePath());
					}
					//������һ��Ŀ¼���ļ���ͣһ���̣߳���ֹϵͳ������
					ThreadSleep(ThreadFreeTime);
				}
			}
			else
			{
				ThreadSleep(ThreadFreeTime);
			}
		}
		else
		{
			ThreadSleep(ThreadFreeTime);
		}
	}
}