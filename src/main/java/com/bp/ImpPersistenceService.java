package com.bp;

import java.util.List;
import java.util.Map;

import com.bp.bll.BPDbBaseDAL;
import com.bp.bll.BPFlg;
import com.bp.bll.DataMQBLL;
import com.bp.common.DataValid;
import com.bp.common.JsonUtility;
import com.bp.common.LogEnum;
import com.bp.common.LogWritter;
import com.bp.interfaces.IPersistenceService;

/**
 * @author zyk
 * @version ����ʱ�䣺2017��9��6�� ����2:44:54 �־û���Ϣ�����������ʵ����˵��
 */
public class ImpPersistenceService implements IPersistenceService {
	/**
	 * 
	 * @param key
	 *            ����ֵ��ʶ��
	 * @param content
	 *            ��Ϣ��(JSON�ַ���)
	 * @param info
	 *            ����Ϊ1������ ������Ϣ���˵��
	 * @return ����ֵ true����ɹ� false����ʧ��
	 */
	public boolean SaveContentToBP(String key, String content,final String[] info) {
		// 1�����ݽ�����֤
		if (key == null || key.length() <= 0 || content == null || content.length() <= 0) {
			info[0] = "���ݸ�ʽ����,key��contentΪ�գ�";
			return false;
		} else {
			// 2���ϴ���Ϣת��Map��ʽ����
			Map<String, List<Map<String, String>>> mapMain = null;
			try {
				mapMain = JsonUtility.JSON2Map(key, content);
			} catch (Exception e) {
				LogWritter.LogWritterInfo(WebPCApiController.class, LogEnum.Error,
						String.format("�ϴ�JSON��ʽ�쳣! key:%s content:%s", key, content));
				info[0]  = "��Ϣ����ת��JSON��ʽ�쳣!";
				return false;
			}
			// 3:�������ע���ʽУ��
			String[] outMsg = new String[1];// ���������Ϣ ȡ��һ��ֵ
			if (DataValid.IsValidFormByJsonObj(mapMain, outMsg)) {
				// 4������ϵͳ���Բ�¼
				ServerCommon comm = new ServerCommon();
				mapMain = comm.AttributeAddByJsonObj(mapMain);
				// ת�����ַ�������
				String val = JsonUtility.ObjToJSONStr(mapMain);
				// 5������Ϣ�����з�����Ϣ
				DataMQBLL bll = new DataMQBLL();
				bll.AddMQInfo(key, val);
				// System.out.println(val);
				// 5�����ؿͻ���
				info[0]  = "��׽��Ϣ�ɹ�!";
				return true;
			} else {
				// 3.2У���������Ϣ���й�ϵ�����ݱ��棬���Բ鿴
				BPDbBaseDAL dal = new BPDbBaseDAL();
				dal.AddValidInfoToDB(key, outMsg[0]);
				// д����־
				LogWritter.LogWritterInfo(WebPCApiController.class, LogEnum.Error,
						String.format("PCAPI�������ݸ�ʽ����msg:%s key:%s content:%s", outMsg[0], key, content));
				info[0]  = outMsg[0];
				return false;
			}
		}
	}

}