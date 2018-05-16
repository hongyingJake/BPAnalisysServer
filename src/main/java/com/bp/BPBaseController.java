package com.bp;

import javax.servlet.http.HttpServletRequest;

import com.bp.bll.BPRegistDAL;
import com.bp.common.LogEnum;
import com.bp.common.LogWritter;
import com.bp.models.BPSystemUserModel;

/**
* @author zyk
* @version ����ʱ�䣺2017��9��15�� ����4:10:09
* ���ע�����  ��˵��
*/
public class BPBaseController {
	private BPRegistDAL BPRegist;

	public void setBPRegist(BPRegistDAL BPRegist) {
		this.BPRegist = BPRegist;
	}
	/**
	 * ���ע��DAL��
	 * @return
	 */
	public BPRegistDAL getBPRegist() {
		return BPRegist;
	}
	/**
	 * ��ȡ��ǰ��¼�û�
	 * 
	 * @param request
	 * @return
	 */
	protected BPSystemUserModel GetCurrentUser(HttpServletRequest request) {
		Object obj = request.getSession().getAttribute("UserLogin");
		if (obj != null) {
			BPSystemUserModel model = (BPSystemUserModel) obj;
			return model;
		} else {
			return new BPSystemUserModel();
		}
	}
	/**
	 * ��¼��־
	 * @param msg
	 */
	protected void WriterLog(String msg) {
		LogWritter.LogWritterInfo(this.getClass(), LogEnum.Warn, msg);
	}
}
