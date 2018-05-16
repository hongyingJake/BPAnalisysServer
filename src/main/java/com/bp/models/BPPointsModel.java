package com.bp.models;

import java.io.Serializable;
import java.util.Date;

/**
* @author zyk
* @version 创建时间：2017年8月8日 下午4:12:38
* 埋点主表  类说明
*/
public class BPPointsModel {
	/*
	 * 埋点ID
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
	 * 埋点键值
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
	 * 埋点名称
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
	 * 备注
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
	 * 注册用户
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
	 * 最后一个更新用户
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
	 * 注册日期
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
	 * 最后一次更新日期
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
	 * 是否有效 1：有效 0：无效
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
