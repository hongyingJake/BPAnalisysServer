package com.bp.common;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.bp.bll.BPFlg;
import com.bp.bll.BPRegistDAL;

/**
 * ����У����
 * 
 * @author aaa
 *
 */
public class DataValid {
	/**
	 * �Ƿ���У��
	 */
	private static String OpenValid = null;
	/**
	 * У��ģʽ 1:ֻ��֤����ֵ���� 2����֤����ֵ����һһ��Ӧ
	 */
	private static String ValidType = null;

	// ��̬����飬������ʱִ��
	static {
		ConfigurationManager manager = new ConfigurationManager();
		OpenValid = manager.ReadConfiByNodeName("OpenValid");
		ValidType = manager.ReadConfiByNodeName("ValidType").equals("") ? "1"
				: manager.ReadConfiByNodeName("ValidType");
	}

	/**
	 * JSONתmap��������У��
	 * 
	 * @param jsonMap
	 *            json��ʽת����Map����
	 * @param OutMsg
	 *            ��֤��������˵��
	 * @return
	 */
	public static boolean IsValidFormByJsonObj(Map<String, List<Map<String, String>>> jsonMap, final String[] OutMsg) {
		// 1���ж��Ƿ���У��
		if (!OpenValid.equals("1")) {
			return true;
		}
		// 2:ͬ���ע����Ϣ����У��
		// 2.1:У�����key�Ƿ�ע��
		boolean isValid = true;// �Ƿ���֤�ɹ�
		for (String k : jsonMap.keySet()) {
			StringBuilder strInfo = new StringBuilder();
			List<Map<String, String>> lst = jsonMap.get(k);
			// a1:���п������У��(У��������ǰ2������)
			if (k.equals(BPFlg.BPSpeedFlg)) {
				isValid = BPSpeedValid(lst, strInfo);
			} else if (k.equals(BPFlg.NoBuryFlg)) {
				// a2:���������У��(У�����ĵ�һ������)
				isValid = BPNoBuryValid(lst, strInfo);
			} else {
				// a3:����һ��ע������У��
				// �����л�ȡ��ǰ����ֵ��Ϣ
				Map<String, String> kvs = BPFlg.GetBpKValues(k);
				// 2.1У������Ƿ�δע��
				if (kvs.keySet().size() == 0) {
					strInfo.append("δע�ᵱǰ�����Ϣ������ֵ����ϢΪ�գ�");
					isValid = false;
					// ���ؿͻ�����Ϣ
					OutMsg[0] = strInfo.toString();
					break;
				}
				// 2.2�ж�����ֵ����Ϣ�Ƿ�һ��
				for (Map<String, String> map : lst) {
					// 2.2:������֤��ֵ�����Ƿ�һ�£��粻һ������֤�ĸ���������
					int mSize = map.keySet().size();
					int kSize = kvs.keySet().size();
					// �ų��ͻ����ύ����ҪУ��ļ�
					for (String ck : map.keySet()) {
						if (BPFlg.NoValidateKeys.contains(ck)) {
							mSize--;
						}
					}
					if (mSize == kSize) {
						if (ValidType.equals("1")) {
							isValid = true;
							continue;
						} else {
							// ��������ֵ��һһУ��
							isValid = ValidMapKey(kvs, map, strInfo);
						}
					} else {
						// ��һ����֤��Щ��ȱ��
						strInfo = ValidMap(kvs, map);
						isValid = false;
					}
					if (!isValid) {
						break;
					}
				}
				//һ��ע������У��ʧ�ܣ���ˢ��һ����㣬�ų��ֲ�ʽ���������ڵ���ӵ����
				//��ȱ�㣺ÿ��У��ʧ�ܶ�Ҫˢ��;��һ�����ͻ�ʧ�ܣ�
				if(!isValid){
					BPFlg.UpCacheBPkv(k);
				}
			}

			if (!isValid) {
				// ���ؿͻ�����Ϣ
				OutMsg[0] = strInfo.toString();
				break;
			}
		}
		return isValid;
	}

	/**
	 * �������У��
	 * 
	 * @param parsValue
	 *            �������ļ�ֵ��Ϣ
	 * @param strInfo
	 *            У��ʧ�ܷ�����Ϣ˵��
	 * @return
	 */
	public static boolean BPSpeedValid(List<Map<String, String>> parsValue, final StringBuilder strInfo) {
		if (parsValue == null) {
			strInfo.append("δ�ҵ��������ļ�������Ϣ��");
			return false;
		}
		boolean isValue = true;
		for (Map<String, String> p : parsValue) {
			// ��ȡ��һ������
			String parms1 = p.get("extendParams01");
			String parms2 = p.get("extendParams02");
			// String parms3=p.get("extendParams03");
			// �ӻ����л�ȡ������������Ϣ
			List<String> others = BPFlg.GetBPSpeedOthKeys(parms1);
			if (others != null) {
				if (others.size() > 0) {
					// �ж�����������Ϊ�����жϡ���Ϊ�յ���δע����У��ʧ��
					if (!parms2.equals("")) {
						if (!others.contains(parms2)) {
							strInfo.append(String.format("���������δע�����2Ϊ��%s �����", parms2));
							isValue = false;
							break;
						}
					}
					// if(!parms3.equals("")){
					// if(!others.contains(parms3)){
					// strInfo.append(String.format("���������δע�����3Ϊ��%s �����",
					// parms3));
					// isValue=false;
					// break;
					// }
					// }
				} else {
					strInfo.append(String.format("���������δע�����1Ϊ��%s �����", parms1));
					// �ӻ������Ƴ�
					BPFlg.RemoveBPSpeedFromCache(parms1);
					isValue = false;
					break;
				}
			} else {
				strInfo.append(String.format("���������δע�����1Ϊ��%s �����", parms1));
				isValue = false;
				break;
			}
		}
		if(!isValue){
			//У��ʧ����ˢ�»��棨�ֲ�ʽ����ʱ���ų������ڵ�ע�����ݡ�ȱ�㣺��һ�����ʧ�ܣ�
			BPFlg.UpCacheBPSpeed(parsValue);
		}
		return isValue;
	}

	/**
	 * �����У��
	 * 
	 * @param parsValue
	 *            �ϴ��ļ�������Ϣ
	 * @param strInfo
	 *            У��ʧ����ʾ��Ϣ
	 * @return
	 */
	public static boolean BPNoBuryValid(List<Map<String, String>> parsValue, final StringBuilder strInfo) {
		if (parsValue == null) {
			strInfo.append("δ�ҵ��������ļ�������Ϣ��");
			return false;
		}
		// У���߼���ֻ����У�������ļ���ʶ���Ƿ�ע��
		boolean isValue = true;
		for (Map<String, String> p : parsValue) {
			// ��ȡ��������ʶ��
			String NoBuryKey = p.get("NoBuryKey");
			// �鿴�������Ƿ�����Ѿ�ע�����������ʶ��
			if (!BPFlg.getBPNoBuryKeys().contains(NoBuryKey)) {
				isValue = false;
				strInfo.append(String.format("�������δע������ʶ��Ϊ:%s �����", NoBuryKey));
			}
		}
		if(!isValue){
			BPFlg.UpCacheBPNoBury();//ˢ������㣨�ֲ�ʽ�����ų������ڵ���ӣ�ȱ�㣺У��ʧ�ܾ�Ҫˢ�£���һ�����ͻ�ʧ�ܣ�
		}
		return isValue;
	}

	/**
	 * ��֤���������Ƿ�һ��
	 * 
	 * @param RMap
	 *            ע�����������
	 * @param CMap
	 *            �ͻ����ϴ�����������
	 * @param sb
	 *            ���������Ϣ
	 * @return
	 */
	public static boolean ValidMapKey(Map<String, String> RMap, Map<String, String> CMap, StringBuilder sb) {
		boolean validSuccess = true;
		sb.append("�����ע������ͻ���δ�ṩ��");
		for (String k : RMap.keySet()) {
			if (!CMap.containsKey(k)) {
				sb.append(k + ",");
				validSuccess = false;
			}
		}
		return validSuccess;
	}

	/**
	 * ����Map����У��
	 * 
	 * @param RMap
	 *            ���ע���Map
	 * @param CMap
	 *            �ͻ����ϴ���Map
	 * @return
	 */
	public static StringBuilder ValidMap(Map<String, String> RMap, Map<String, String> CMap) {
		StringBuilder sb = new StringBuilder();
		int rSize = RMap.keySet().size();
		int cSize = CMap.keySet().size();
		// �ų��ͻ��˲���ҪУ��ļ�
		for (String k : CMap.keySet()) {
			if (BPFlg.NoValidateKeys.contains(k)) {
				cSize--;
			}
		}
		if (rSize > cSize) {
			sb.append("��ǰ�����ע��������ǿͻ���δ�ṩ��");
			for (String k : RMap.keySet()) {
				if (!CMap.containsKey(k)) {
					sb.append(k + ",");
				}
			}
		} else {
			sb.append("�ͻ����ṩ�˵�ǰ�����δע��ļ���");
			for (String k : CMap.keySet()) {
				if (BPFlg.NoValidateKeys.contains(k)) {
					continue;
				}
				if (!RMap.containsKey(k)) {
					sb.append(k + ",");
				}
			}
		}
		return sb;
	}

	/**
	 * У�������Ϣ�Ƿ����
	 * 
	 * @param key
	 *            ��
	 * @param val
	 *            ����
	 * @param OutMsg
	 *            �����Ϣ ȡ�����һ��ֵ
	 * @return
	 */
	public static boolean IsValidFormBaseModel(String key, String val, String[] OutMsg) {
		if (OpenValid == null) {
			ConfigurationManager manager = new ConfigurationManager();
			OpenValid = manager.ReadConfiByNodeName("OpenValid");
		}
		OutMsg = new String[1];
		// �ж��Ƿ���У��
		if (!OpenValid.equals("1")) {
			return true;
		}
		// �ж��Ƿ����ת����JSON��ʽ
		if (JsonUtility.IsJSON(val)) {
			return true;
		} else {
			OutMsg[0] = "���������Ϣת��JSON��ʽʧ�ܣ�";
			return false;
		}
	}
}
