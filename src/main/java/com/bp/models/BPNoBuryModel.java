package com.bp.models;

import java.util.Date;

/**
* @author zyk
* @version ����ʱ�䣺2017��9��15�� ����2:42:32
* �����ע��ģ��  ��˵��
*/
public class BPNoBuryModel {
	private int noBuryId;
	/**
	 * ������ֵ��ʶ��˵����������ϵͳ��
	 */
	private String noBuryKey;
	/**
	 * �����ע��˵��
	 */
	private String noBuryInfo;
	/**
	 * �������չ����˵��
	 */
	private String noBuryExtend;
	private String regUserId;
	private String regUserName;
	private Date registerDate;
	private boolean isDelete;
	public int getNoBuryId() {
		return noBuryId;
	}
	public void setNoBuryId(int noBuryId) {
		this.noBuryId = noBuryId;
	}
	public String getNoBuryKey() {
		return noBuryKey;
	}
	public void setNoBuryKey(String noBuryKey) {
		this.noBuryKey = noBuryKey;
	}
	public String getNoBuryInfo() {
		return noBuryInfo;
	}
	public void setNoBuryInfo(String noBuryInfo) {
		this.noBuryInfo = noBuryInfo;
	}
	public String getNoBuryExtend() {
		return noBuryExtend;
	}
	public void setNoBuryExtend(String noBuryExtend) {
		this.noBuryExtend = noBuryExtend;
	}
	public String getRegUserId() {
		return regUserId;
	}
	public void setRegUserId(String regUserId) {
		this.regUserId = regUserId;
	}
	public String getRegUserName() {
		return regUserName;
	}
	public void setRegUserName(String regUserName) {
		this.regUserName = regUserName;
	}
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	public boolean isDelete() {
		return isDelete;
	}
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
}
