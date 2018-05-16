package com.bp.bll;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bp.common.ConfigurationManager;
import com.bp.common.LogEnum;
import com.bp.common.LogWritter;
import com.bp.models.BPSubPointsModel;

/**
* @author zyk
* @version 创建时间：2017年8月7日 上午11:27:11
* 埋点服务系统缓存资源存储 类说明
*/
public class BPFlg {
	/**
	 * 消息队列服务是否可用
	 */
	private static boolean MSMQServerCanUse=true;
	/**
	 * 客户唯一标识键名称
	 */
	private static String mtbpAccountKeyName="mtbpAccountID";
	/**
	 * 埋点校验时哪些客户端上传的键集合不需要校验
	 */
	public static List<String> NoValidateKeys=new ArrayList<String>();
	/**
	 * 并发访问对象锁对象
	 */
	private static Object objClock=new Object();
	/**
	 * 快速埋点标识符
	 */
	public final static String BPSpeedFlg="defaultFunClickCount"; 
	/**
	 * 无埋点标识符
	 */
	public final static String NoBuryFlg="NoBuryPointsKey";
	/**
	 * 本地服务器IP
	 */
	public static String ServerLocalIp=null;
	
	/**
	 * 埋点键值集合缓存对象
	 */
	private static Map<String,Map<String,String>> bpKValues=new HashMap<String,Map<String,String>>();
	
	/**
	 * 快速埋点 参数集合 (第一个参数,其他参数集合)
	 */
	private static Map<String,List<String>> SpeedKeys=new HashMap<String,List<String>>();
	
	/**
	 * 无埋点键标识符
	 */
	private static List<String> BPNoBuryKeys=null;
	
	
	/**
	 * 静态代码块，初始化数据
	 */
	static{
		
		//添加埋点时不需要校验客户端提交的键集合信息
		ConfigurationManager cM=new ConfigurationManager();
		String[] NoValidClientKeys=cM.ReadConfiByNodeName("NoValidClientKeys").split(",");
		List<String> noKeys= Arrays.asList(NoValidClientKeys);
		NoValidateKeys.addAll(noKeys);
		
	}
	/**
	 * 获取无埋点标识符集合
	 * @return
	 */
	public static List<String> getBPNoBuryKeys() {
		
		if(BPNoBuryKeys==null)
		{
			UpCacheBPNoBury();
		}
		return BPNoBuryKeys;
	}
	
	/**
	 * 刷新无埋点
	 */
	public static void UpCacheBPNoBury(){
		synchronized(BPFlg.class){
			BPNoBuryKeys=new ArrayList<String>();
			BPDbBaseDAL dal=new BPDbBaseDAL();
			//获取无埋点键标识符到缓存
			BPNoBuryKeys.addAll(dal.GetBPNoBurykeys());
		}
	}
	/**
	 * 添加无埋点标识符到缓存
	 * @param NoBuryKey
	 */
	public static void AddBPNoBuryKeytoCache(String NoBuryKey){
		if(!getBPNoBuryKeys().contains(NoBuryKey)){
			BPNoBuryKeys.add(NoBuryKey);
		}
	}
	/**
	 * 从缓存中移除无埋点标识符
	 * @param NoBuryKey
	 */
	public static void RemoveBPNokeyFromCache(String NoBuryKey){
		if(getBPNoBuryKeys().contains(NoBuryKey)){
			BPNoBuryKeys.remove(NoBuryKey);
		}
	}
	
	/**
	 * 获取快速埋点其他参数信息
	 * @param pars1 快速埋点第一个参数值
	 * @return
	 */
	public static List<String> GetBPSpeedOthKeys(String pars1){
		List<String> lst=null;
		if(SpeedKeys.containsKey(pars1)){
			lst=SpeedKeys.get(pars1);
		}
		else{
			lst=AddSpeedOthKeysToCache(pars1);
		}
		return lst;
	}
	/**
	 * 刷新快速埋点
	 * @param pars1
	 */
	public static void UpCacheBPSpeed(List<Map<String, String>> parsValue){
		if(parsValue!=null)
		{
			for (Map<String, String> p : parsValue) {
				// 获取第一个参数
				String parms1 = p.get("extendParams01");
				if (!parms1.equals("")){
					AddSpeedOthKeysToCache(parms1);
				}
			}
		}
	}
	/**
	 * 获取快速埋点参数集合信息并添加到缓存
	 * @param pars1
	 * @return
	 */
	private static List<String> AddSpeedOthKeysToCache(String pars1){
		List<String> lst=null;
		synchronized(BPFlg.class){
			if(SpeedKeys.containsKey(pars1)){
				SpeedKeys.remove(pars1);
			}
			BPDbBaseDAL dal=new BPDbBaseDAL();
			lst=dal.GetSpeedOthKeys(pars1);
			SpeedKeys.put(pars1, lst);
		}
		return lst;
	}
	
	/**
	 * 从缓存中移除快速埋点信息
	 * @param pars1 快速埋点参数1
	 */
	public static void RemoveBPSpeedFromCache(String pars1){
		SpeedKeys.remove(pars1);
	}
	
	
	/**
	 * 获取埋点键值集合
	 * @param BPFlg
	 * @return
	 */
	public static Map<String,String> GetBpKValues(String BPFlg)
	{
		Map<String,String> kv=null;
		//不包含当前埋点键集合则添加
		if(!bpKValues.containsKey(BPFlg))
		{
			kv=AddBPKValuesToCache(BPFlg);
		}
		else
		{
			//包含则直接获取
			kv=bpKValues.get(BPFlg);
		}
		return kv;
	}
	
	/**
	 * 从缓存中移除埋点信息（未用到，不允许删除埋点）
	 * @param BPFlg
	 */
	public static void RemoveBpFromCache(String BPFlg){
		if(bpKValues.containsKey(BPFlg))
		{
			bpKValues.remove(BPFlg);
		}
	}
	/**
	 * 缓存中移除埋点键值集合
	 * @param BPFlg
	 * @param BPKName
	 */
	public static void RemoveBpKValue(String BPFlg,String BPKName)
	{
		Map<String,String> kv=GetBpKValues(BPFlg);
		if(kv.containsKey(BPKName))
		{
			kv.remove(BPKName);
		}
	}
	/**
	 * 添加埋点键集合键值信息到缓存
	 * @param BPFlg
	 * @param BPKName
	 * @param BPKValue
	 */
	public static void AddBpKValue(String BPFlg,String BPKName,String BPKValue)
	{
		Map<String,String> kv=GetBpKValues(BPFlg);
		if(!kv.containsKey(BPKName))
		{
			kv.put(BPKName, BPKValue);
		}
	}
	/**
	 * 更新埋点缓存
	 * @param BPFlg
	 */
	public static void UpCacheBPkv(String BPFlg){
		AddBPKValuesToCache(BPFlg);
	}
	/**
	 * 添加埋点键集合添加到缓存中
	 * @param BPFlg
	 */
	private static Map<String,String> AddBPKValuesToCache(String BPFlg)
	{
		BPDbBaseDAL dal=new BPDbBaseDAL();
		Map<String,String> kv=new HashMap<String,String>();
		synchronized(BPFlg.class){
			if(bpKValues.containsKey(BPFlg)){
				bpKValues.remove(BPFlg);
			}
			//1:从数据库获取键集合信息
			List<BPSubPointsModel> lst=dal.GetBPSubPointsKValuesByBPFlg(BPFlg);
			if(lst==null)
			{
				lst=new ArrayList<BPSubPointsModel>();
			}
			//2:键集合添加到缓存中
			for(BPSubPointsModel m:lst)
			{
				kv.put(m.BPKName, m.BPKValue);
			}
			bpKValues.put(BPFlg, kv);
		}
		return kv;
	}
	
	/**
	 * 获取消息队列服务是否可用
	 * @return
	 */
	public static boolean getMSMQServerCanUse()
	{
		return MSMQServerCanUse;
	}
	/**
	 * 设置消息队列是否可使用
	 * @param canUse
	 */
	public synchronized static void setMSMQServerCanUse(boolean canUse)
	{
		synchronized(BPFlg.class){
			MSMQServerCanUse=canUse;
		}
	}
}