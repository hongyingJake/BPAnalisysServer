package com.bp;

import javax.servlet.http.HttpServletRequest;

import com.bp.bll.BPRegistDAL;
import com.bp.common.LogEnum;
import com.bp.common.LogWritter;
import com.bp.models.BPSystemUserModel;

/**
* @author zyk
* @version 创建时间：2017年9月15日 下午4:10:09
* 埋点注册基类  类说明
*/
public class BPBaseController {
	private BPRegistDAL BPRegist;

	public void setBPRegist(BPRegistDAL BPRegist) {
		this.BPRegist = BPRegist;
	}
	/**
	 * 埋点注册DAL类
	 * @return
	 */
	public BPRegistDAL getBPRegist() {
		return BPRegist;
	}
	/**
	 * 获取当前登录用户
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
	 * 记录日志
	 * @param msg
	 */
	protected void WriterLog(String msg) {
		LogWritter.LogWritterInfo(this.getClass(), LogEnum.Warn, msg);
	}
}
