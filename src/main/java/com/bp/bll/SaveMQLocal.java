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
 * @version 创建时间：2017年8月7日 上午11:44:41 保存消息到本地文件 类说明
 */
public class SaveMQLocal {
	private static String FileName = "MQTempFile.bp";// 文件名称
	public static String ShareDir;// 共享文件目录，存放服务持久化文件
	/**
	 * 最新获取的文件名称
	 */
	private static String LastFilePath;
	// 静态代码块，初始化变量信息
	static {
		ConfigurationManager mgr = new ConfigurationManager();
		ShareDir = mgr.ReadConfiByNodeName("ShareDir");
		LastFilePath=ShareDir+ File.separator + "f0_" + FileName;
	}

	/**
	 * 动态获取目录文件名称
	 * 
	 * @return
	 */
	public static String GetDirFileName() {
		//1：如果文件内容大于100MB,重新获取文件（保证每个文件大小都不大于100MB）
		String filePath = ShareDir;// 获取upload的绝对目录
		String fileName = "f0_" + FileName;
		long fileMaxLength=100*1024*1024;//文件最大为100MB  100*1024*1024
		//逻辑：首先查看上一次获取的文件大小是否超过指定大小，没有超过则使用；然后在已有的文件中查找；最后无符合文件则创建新文件
		File lastFile=new File(LastFilePath);
		if(lastFile.length()<fileMaxLength){
			return LastFilePath;
		}
		File file=new File(filePath);
		if(file.isDirectory()&&file.exists()){
			File[] files= file.listFiles();
			boolean havFile=false;//是否找到内容小于100MB的文件
			List<Integer> fileNum=new ArrayList<Integer>();//记录文件编号，如果文件都不符合则找最大编号的文件
			Pattern pr=Pattern.compile("^[a-zA-Z]{1}([\\d]+).*$");//取出文件数字编号
			for(File f:files)
			{
				fileName=f.getName();
				Matcher mc= pr.matcher(fileName);
				if(mc.find()){
					fileNum.add(Integer.parseInt(mc.group(1)));
				}
				if(f.length()<fileMaxLength)
				{
					//注意：读取文件通过文件锁操作，读取二进制转换字符串，而每条消息是一行，不能分配读取。控制文件大小，每次直接读取整个文件
					filePath=f.getAbsolutePath();
					havFile=true;
					break;
				}
			}
			//如果没有找到合适的文件就重新创建个文件
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
		//2：随机生成文件
//		Random rm = new Random();
//		// 得到一个唯一文件名称
//		fileName = "f" + rm.nextInt(6) + "_" + FileName;
//		// 创建文件的存储路径，使用目录分离算法
//		// 获取文件名称的HashCode编码，int类型，占4个字节，upload\\3\\12\\image1.jpeg
//		//int fileCode = fileName.hashCode();
//		int firstDire =rm.nextInt(6);// fileCode & 0xf;// 取得第一级目录
//		int secondDire =rm.nextInt(6);// (fileCode >> 4) & 0xf;// 取得第二级目录
//		// 拼接文件目录
//		if (!ShareDir.endsWith(File.separator)) {
//			ShareDir = ShareDir + File.separator;
//		}
//		filePath = ShareDir + Integer.toString(firstDire) + File.separator + Integer.toString(secondDire);
//		// 目录不存在则创建
//		IOTxtHelper.CreateDir(filePath);
//		// 文件不存在则创建
//		filePath = filePath + File.separator + fileName;
//		IOTxtHelper.CreateFile(filePath);
		
		//3:返回绝对地址文件路径
		LastFilePath=filePath;
		return filePath;
		// Path targetPath=Paths.get(filePath, String.format("%s",firstDire));
	}

	
	/**
	 * 保存消息到文本文件
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
					LogWritter.LogWritterInfo(SaveMQLocal.class, LogEnum.Error, "服务持久化消息到本地异常，异常：" + e.toString());
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
//					LogWritter.LogWritterInfo(SaveMQLocal.class, LogEnum.Error, "服务持久化消息到本地异常，异常：" + e.toString());
//					e.printStackTrace();
//				}
//			}
//		});
//		thread.start();
		return true;
	}

	/**
	 * 读取文件内容信息后清空信息
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
			LogWritter.LogWritterInfo(SaveMQLocal.class, LogEnum.Error, "服务从持久化文件中读取消息异常，异常：" + e.getMessage());
		}
		return content;
	}
	/**
	 * 保存缓存信息到本地
	 * 停止API服务时候调用
	 */
	public static void SaveCacheQueuetoLocal(){
		String path=GetDirFileName();
		IOTxtHelper.SaveCacheQueueToFile(path, -1);
	}
}
