package com.bp.models;

import java.util.Date;

/**
* @author zyk
* @version 创建时间：2017年8月8日 下午4:04:15
* 埋点服务系统注册用户   类说明
*/
public class BPSystemUserModel {
	/*
	 * 表主键ID
	 */
	public  int BPUserId;
	/*
	 * 用户ID
	 */
	public String UserId;
	/*
	 * 登录名称
	 */
	public String UserLoginName;
	/*
	 * 登录密码
	 */
	public String UserLoginPwd;
	/*
	 * 用户名称
	 */
	public String UserName;
	/*
	 * 公司名称
	 */
	public String UserCompanyName;
	/*
	 * 用户电话
	 */
	public String UserPhone;
	/*
	 * 备注信息
	 */
	public String Demo;
	/*
	 * 注册日期
	 */
	public Date RegisterDate;
	/*
	 * 是否有效 1：有效 0：无效
	 */
	public int IsValid;
	
	/*
	 * 1:管理员  0：非管理员  管理员可查看所有数据
	 */
	public int IsAdmin;
	
}
