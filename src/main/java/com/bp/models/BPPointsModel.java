package com.bp.models;

import java.io.Serializable;
import java.util.Date;

/**
* @author zyk
* @version ����ʱ�䣺2017��8��8�� ����4:12:38
* �������  ��˵��
*/
public class BPPointsModel {
	/*
	 * ���ID
	 */
	private int bPId;
	public void setBPId(int bpid)
	{
		this.bPId=bpid;
	}
	public int getBPId()
	{
		return this.bPId;
	}
	/*
	 * ����ֵ
	 */
	private String bPFlg;
	public void setBPFlg(String BPFlg)
	{
		this.bPFlg=BPFlg;
	}
	public String getBPFlg()
	{
		return this.bPFlg;
	}
	/*
	 * �������
	 */
	private String bPName;
	public void setBPName(String name)
	{
		this.bPName=name;
	}
	public String getBPName()
	{
		return this.bPName;
	}
	/*
	 * ��ע
	 */
	private String bPDemo;
	public void setBPDemo(String BPDemo)
	{
		this.bPDemo=BPDemo;
	}
	public String getBPDemo()
	{
		return this.bPDemo;
	}
	/*
	 * ע���û�
	 */
	private String regUserId;
	public void setRegUserId(String RegUserId)
	{
		this.regUserId=RegUserId;
	}
	public String getRegUserId()
	{
		return this.regUserId;
	}
	/*
	 * ���һ�������û�
	 */
	private String lastUpUserId;
	public void setLastUpUserId(String LastUpUserId)
	{
		this.lastUpUserId=LastUpUserId;
	}
	public String getLastUpUserId()
	{
		return this.lastUpUserId;
	}
	/*
	 * ע������
	 */
	private Date registerDate;
	public void setRegisterDate(Date RegisterDate)
	{
		this.registerDate=RegisterDate;
	}
	public Date getRegisterDate()
	{
		return this.registerDate;
	}
	/*
	 * ���һ�θ�������
	 */
	private Date lastUpDate;
	public void setLastUpDate(Date LastUpDate)
	{
		this.lastUpDate=LastUpDate;
	}
	public Date getLastUpDate()
	{
		return this.lastUpDate;
	}
	/*
	 * �Ƿ���Ч 1����Ч 0����Ч
	 */
	private int isValid;
	public void setIsValid(int IsValid)
	{
		this.isValid=IsValid;
	}
	public int getIsValid()
	{
		return this.isValid;
	}
}
