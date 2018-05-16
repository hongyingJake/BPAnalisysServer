package com.bp.bll;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bp.common.*;

/**
 * @author zyk
 * @version ����ʱ�䣺2017��8��7�� ����11:44:41 ������Ϣ�������ļ� ��˵��
 */
public class SaveMQLocal {
	private static String FileName = "MQTempFile.bp";// �ļ�����
	public static String ShareDir;// �����ļ�Ŀ¼����ŷ���־û��ļ�
	/**
	 * ���»�ȡ���ļ�����
	 */
	private static String LastFilePath;
	// ��̬����飬��ʼ��������Ϣ
	static {
		ConfigurationManager mgr = new ConfigurationManager();
		ShareDir = mgr.ReadConfiByNodeName("ShareDir");
		LastFilePath=ShareDir+ File.separator + "f0_" + FileName;
	}

	/**
	 * ��̬��ȡĿ¼�ļ�����
	 * 
	 * @return
	 */
	public static String GetDirFileName() {
		//1������ļ����ݴ���100MB,���»�ȡ�ļ�����֤ÿ���ļ���С��������100MB��
		String filePath = ShareDir;// ��ȡupload�ľ���Ŀ¼
		String fileName = "f0_" + FileName;
		long fileMaxLength=100*1024*1024;//�ļ����Ϊ100MB  100*1024*1024
		//�߼������Ȳ鿴��һ�λ�ȡ���ļ���С�Ƿ񳬹�ָ����С��û�г�����ʹ�ã�Ȼ�������е��ļ��в��ң�����޷����ļ��򴴽����ļ�
		File lastFile=new File(LastFilePath);
		if(lastFile.length()<fileMaxLength){
			return LastFilePath;
		}
		File file=new File(filePath);
		if(file.isDirectory()&&file.exists()){
			File[] files= file.listFiles();
			boolean havFile=false;//�Ƿ��ҵ�����С��100MB���ļ�
			List<Integer> fileNum=new ArrayList<Integer>();//��¼�ļ���ţ�����ļ�����������������ŵ��ļ�
			Pattern pr=Pattern.compile("^[a-zA-Z]{1}([\\d]+).*$");//ȡ���ļ����ֱ��
			for(File f:files)
			{
				fileName=f.getName();
				Matcher mc= pr.matcher(fileName);
				if(mc.find()){
					fileNum.add(Integer.parseInt(mc.group(1)));
				}
				if(f.length()<fileMaxLength)
				{
					//ע�⣺��ȡ�ļ�ͨ���ļ�����������ȡ������ת���ַ�������ÿ����Ϣ��һ�У����ܷ����ȡ�������ļ���С��ÿ��ֱ�Ӷ�ȡ�����ļ�
					filePath=f.getAbsolutePath();
					havFile=true;
					break;
				}
			}
			//���û���ҵ����ʵ��ļ������´������ļ�
			if(!havFile){
				if(fileNum.size()>0){
					int num=Collections.max(fileNum);
					num=num+1;
					fileName=String.format("f%s_%s", num,FileName);
					filePath = ShareDir + File.separator + fileName;
				}else{
					filePath = ShareDir + File.separator + "f0_" + FileName;
				}
				IOTxtHelper.CreateFile(filePath);
			}
		}
		//2����������ļ�
//		Random rm = new Random();
//		// �õ�һ��Ψһ�ļ�����
//		fileName = "f" + rm.nextInt(6) + "_" + FileName;
//		// �����ļ��Ĵ洢·����ʹ��Ŀ¼�����㷨
//		// ��ȡ�ļ����Ƶ�HashCode���룬int���ͣ�ռ4���ֽڣ�upload\\3\\12\\image1.jpeg
//		//int fileCode = fileName.hashCode();
//		int firstDire =rm.nextInt(6);// fileCode & 0xf;// ȡ�õ�һ��Ŀ¼
//		int secondDire =rm.nextInt(6);// (fileCode >> 4) & 0xf;// ȡ�õڶ���Ŀ¼
//		// ƴ���ļ�Ŀ¼
//		if (!ShareDir.endsWith(File.separator)) {
//			ShareDir = ShareDir + File.separator;
//		}
//		filePath = ShareDir + Integer.toString(firstDire) + File.separator + Integer.toString(secondDire);
//		// Ŀ¼�������򴴽�
//		IOTxtHelper.CreateDir(filePath);
//		// �ļ��������򴴽�
//		filePath = filePath + File.separator + fileName;
//		IOTxtHelper.CreateFile(filePath);
		
		//3:���ؾ��Ե�ַ�ļ�·��
		LastFilePath=filePath;
		return filePath;
		// Path targetPath=Paths.get(filePath, String.format("%s",firstDire));
	}

	
	/**
	 * ������Ϣ���ı��ļ�
	 * 
	 * @param value
	 * @return
	 */
	public static boolean SaveMQToTxt(final Object value) {
		ExecutorService service=Executors.newFixedThreadPool(Integer.MAX_VALUE);
		service.execute(new Runnable() {
			public void run() {
				try {
					//String val=JsonUtility.ObjToJSONStr(value);
					// String path=GetDirFileName();
					// IOTxtHelper.WriteLine(path, value.toString());
					IOTxtHelper.WriteLine(value.toString());
				} catch (Exception e) {
					LogWritter.LogWritterInfo(SaveMQLocal.class, LogEnum.Error, "����־û���Ϣ�������쳣���쳣��" + e.toString());
					e.printStackTrace();
				}
			}
		});
		service.shutdown();
//		Thread thread = new Thread(new Runnable() {
//			public void run() {
//				try {
//					IOTxtHelper.WriteLine(value.toString());
//				} catch (Exception e) {
//					LogWritter.LogWritterInfo(SaveMQLocal.class, LogEnum.Error, "����־û���Ϣ�������쳣���쳣��" + e.toString());
//					e.printStackTrace();
//				}
//			}
//		});
//		thread.start();
		return true;
	}

	/**
	 * ��ȡ�ļ�������Ϣ�������Ϣ
	 * 
	 * @param filePathName
	 * @return
	 * @throws Exception
	 */
	public static String GetInfoBackClear(String filePathName) {
		String content = "";
		try {
			content = IOTxtHelper.Read(filePathName, true);
		} catch (Exception e) {
			LogWritter.LogWritterInfo(SaveMQLocal.class, LogEnum.Error, "����ӳ־û��ļ��ж�ȡ��Ϣ�쳣���쳣��" + e.getMessage());
		}
		return content;
	}
	/**
	 * ���滺����Ϣ������
	 * ֹͣAPI����ʱ�����
	 */
	public static void SaveCacheQueuetoLocal(){
		String path=GetDirFileName();
		IOTxtHelper.SaveCacheQueueToFile(path, -1);
	}
}